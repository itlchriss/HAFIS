"""
Expression extraction pipeline step.

Extracts quoted/bracketed expressions from text and stores them in the
context's expression_store, replacing them with placeholder tokens.
Handles backtick expressions, quoted strings/chars, and bracketed arrays.
"""

import os
import re
import yaml
from typing import Dict, List, Optional

from ..pipeline import ProcessingContext, ProcessingStep

# Default path to expression extraction rules
DEFAULT_RULES_PATH = os.path.join('.', 'rules', 'expression_extraction.yml')

# Articles/determiners that signal a noun phrase to ccg2lambda
_DETERMINERS = ('the ', 'a ', 'an ')


def _strip_brackets(s: str) -> str:
    """Remove surrounding square brackets."""
    return s.replace('[', '').replace(']', '')


def _strip_parens(s: str) -> str:
    """Remove surrounding parentheses."""
    return s.replace('(', '').replace(')', '')


def _strip_backticks(s: str) -> str:
    """Remove surrounding backticks and single quotes."""
    return s.replace('`', '').replace("'", '').strip()


VALUE_TRANSFORMS = {
    'strip_brackets': _strip_brackets,
    'strip_parens': _strip_parens,
    'strip_backticks': _strip_backticks,
}


def _replace_with_determiner(sent: str, match: str, replacement: str) -> str:
    """Replace first occurrence of *match* in *sent* with *replacement*,
    prepending 'the' if the replacement starts with a placeholder key
    and no determiner already precedes the match position.

    This avoids producing 'the the arr_a' when the source text already
    contains a determiner before the bracketed expression.
    """
    idx = sent.find(match)
    if idx == -1:
        return sent

    # Does a determiner already immediately precede the match?
    preceding = sent[:idx].lower()
    has_det = any(preceding.endswith(d) for d in _DETERMINERS)

    if has_det:
        # Strip a leading 'the ' from the replacement to avoid duplication
        if replacement.startswith(' the '):
            replacement = ' ' + replacement[5:]
        elif replacement.startswith('the '):
            replacement = replacement[4:]

    return sent[:idx] + replacement + sent[idx + len(match):]


class ExpressionExtractionStep(ProcessingStep):
    """Extracts expressions from text and stores them in context.
    
    Loads simple extraction rules from YAML (arrays, tuples) and also
    handles programmatic extraction of backtick expressions and quoted
    strings/characters.
    
    YAML format:
        - name: rule_name
          pattern: 'regex_with_capture_group'
          key_prefix: 'prefix_'
          replacement_template: ' the type_ {key}'
          value_transform: strip_brackets
    """
    
    def __init__(self, rules_path: Optional[str] = None):
        """Initialize the expression extraction step.
        
        Args:
            rules_path: Path to the YAML rules file. Uses default if not specified.
        """
        self.rules_path = rules_path or DEFAULT_RULES_PATH
        self.rules: List[dict] = []
        self._loaded = False
    
    def _load_rules(self):
        """Load rules from the YAML file."""
        if self._loaded:
            return
        
        if not os.path.exists(self.rules_path):
            self._loaded = True
            return
        
        with open(self.rules_path, 'r', encoding='utf-8') as fp:
            data = yaml.safe_load(fp)
        
        if data:
            self.rules = data
        
        self._loaded = True
    
    def process(self, sent: str, context: ProcessingContext) -> str:
        """Extract expressions from the sentence.
        
        Extracted expressions are stored in context.expression_store with
        generated keys. The sentence is modified to replace expressions
        with placeholder tokens.
        
        Args:
            sent: The sentence to process
            context: Processing context (expression_store is updated)
            
        Returns:
            The modified sentence with placeholders
        """
        self._load_rules()
        exprs = context.expression_store
        
        # 1. Apply YAML-defined extraction rules
        sent = self._apply_yaml_rules(sent, exprs)
        
        # 2. Extract backtick expressions (programmatic - has char/string classification)
        sent = self._extract_backtick_exprs(sent, exprs)
        
        # 3. Extract 'x' or 'y' characters pattern
        sent = self._extract_char_or_pattern(sent, exprs)
        
        # 4. Extract single-quoted chars
        sent = self._extract_single_quoted_chars(sent, exprs)
        
        # 5. Extract double-quoted strings (multiple patterns for specificity)
        sent = self._extract_double_quoted_strings(sent, exprs)
        
        # 6. Handle power sign: ^ -> _pow_
        sent = self._handle_power_sign(sent)
        
        # 7. Fix type distribution for "A or B noun" patterns
        sent = self._fix_type_distribution(sent)
        
        # 8. Fix 'or' article insertion (skip if 'the' already present)
        sent = re.sub(r'\bor (?!the )(str_|expr_)', r'or the \1', sent)
        
        return sent
    
    def _apply_yaml_rules(self, sent: str, exprs: Dict) -> str:
        """Apply YAML-defined extraction rules."""
        for rule in self.rules:
            pattern = rule['pattern']
            key_prefix = rule['key_prefix']
            template = rule['replacement_template']
            transform_name = rule.get('value_transform', '')
            transform = VALUE_TRANSFORMS.get(transform_name, lambda x: x)

            matches = re.findall(pattern, sent, re.ASCII)
            for i, match in enumerate(matches):
                key = key_prefix + chr(i + 97)  # a, b, c, ...
                exprs[key] = transform(match)
                replacement = template.format(key=key)
                sent = _replace_with_determiner(sent, match, replacement)

        return sent
    
    def _extract_backtick_exprs(self, sent: str, exprs: Dict) -> str:
        """Extract backtick-quoted expressions with character/string classification.

        Duplicate backtick content reuses the first assigned key.
        """
        matches = re.findall(r'(`[0-9 <>\-\+\*!,a-zA-Z\[\]=\.\^\(\)\%\|\/_\'\{\}]+`)', sent)

        seen: Dict[str, str] = {}
        new_index = 0

        for e in matches:
            _t = e.replace('`', '').replace("'", '').strip()
            original_t = _t  # save for dedup before any mutation

            # Reuse key for duplicate content
            if original_t in seen:
                sent = _replace_with_determiner(sent, e, ' ' + seen[original_t])
                continue

            index = chr(new_index + 97)
            new_index += 1

            if not _t:
                symbol, _t = 'chrs_', ' '
            elif _t == 'period':
                symbol, _t = 'chrs_', '.'
            elif len(_t) == 1:
                symbol = 'chrs_'
            else:
                symbol = 'strs_'

            key = symbol + index
            exprs[key] = ("'%s'" if len(_t) == 1 else '"%s"') % _t
            seen[original_t] = key

            sent = _replace_with_determiner(sent, e, ' ' + key)

        return sent
    
    def _extract_char_or_pattern(self, sent: str, exprs: Dict) -> str:
        """Extract 'x' or 'y' characters pattern."""
        if r := re.findall(r'((\'[^ ]+\')\s+or\s+(\'[^ ]+\')\s+characters)', sent):
            s = r[0][0]
            _s = s.replace('characters', '')
            for i, e in enumerate(r[0][1:]):
                index = chr(i + 97)
                exprs['chrx_' + index] = e
                _s = _s.replace(e, ' chrx_' + index, 1)
            sent = sent.replace(s, _s)
        return sent

    def _extract_single_quoted_chars(self, sent: str, exprs: Dict) -> str:
        """Extract single-quoted character literals."""
        for i, e in enumerate(re.findall(r'(\'[^, ]+\')', sent)):
            index = chr(i + 97)
            exprs['chry_' + index] = e
            sent = _replace_with_determiner(sent, e, 'the chry_' + index)
        return sent

    def _extract_double_quoted_strings(self, sent: str, exprs: Dict) -> str:
        """Extract double-quoted string literals (multiple specificity levels)."""
        levels = [
            (r'(\"[^, ]+\")',  'stry_'),
            (r'(\"[a-zA-Z, ]+\")', 'strz_'),
            (r'(\"[^,]+\")',   'strl_'),
            (r'(\".*\")',      'strkk_'),
        ]
        for pattern, prefix in levels:
            for i, e in enumerate(re.findall(pattern, sent)):
                key = prefix + chr(i + 97)
                v = e.replace(' ', '') if ',' in e else e
                exprs[key] = v
                sent = _replace_with_determiner(sent, e, ' ' + key)
        return sent
    
    def _handle_power_sign(self, sent: str) -> str:
        """Replace ^ with _pow_ in words."""
        for w in sent.split():
            if '^' in w:
                sent = sent.replace(w, w.replace('^', '_pow_'))
        return sent

    def _fix_type_distribution(self, sent: str) -> str:
        """Fix 'A or B noun' patterns where the noun distributes to both."""
        types = ['characters', 'strings']
        pattern = r'(str_[^ ]+)\s+(or)\s+(str_[^ ]+)\s+(\b(?:{})\b)'.format('|'.join(types))
        if r := re.findall(pattern, sent):
            from nltk.stem import WordNetLemmatizer
            lemmatizer = WordNetLemmatizer()
            a, conj, b, noun = r[0]
            _type = 'type_' + lemmatizer.lemmatize(noun) + '_'
            sent = re.sub(
                r'%s\s+%s\s+%s\s+%s' % (a, conj, b, noun),
                r'the %s %s %s the %s %s' % (_type, a, conj, _type, b),
                sent
            )
        return sent
