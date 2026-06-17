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
        
        # 8. Fix 'or' article insertion
        sent = re.sub(r'or str_', 'or the str_', sent)
        sent = re.sub(r'or expr_', 'or the expr_', sent)
        
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
                index = chr(i + 97)  # a, b, c, ...
                key = key_prefix + index
                value = transform(match)
                exprs[key] = value
                replacement = template.format(key=key)
                sent = sent.replace(match, replacement, 1)
        
        return sent
    
    def _extract_backtick_exprs(self, sent: str, exprs: Dict) -> str:
        """Extract backtick-quoted expressions with character/string classification."""
        matches = re.findall(r'(`[0-9 <>\-\+\*!,a-zA-Z\[\]=\.\^\(\)\%\|\/_\'\{\}]+`)', sent)
        
        for i, e in enumerate(matches):
            index = chr(i + 97)
            _t = e.replace('`', '').replace("'", '').strip()
            
            if not _t:
                # Empty symbol means space character
                symbol = 'chrs_'
                _type = 'type_character_'
                _t = ' '
            elif _t == 'period':
                # Hard fix for period character
                symbol = 'chrs_'
                _type = 'type_character_'
                _t = '.'
            elif len(_t) == 1:
                # Single character
                symbol = 'chrs_'
                _type = 'type_character_'
            else:
                # String
                symbol = 'strs_'
                _type = 'type_string_'
            
            if len(_t) == 1:
                exprs[symbol + index] = "'%s'" % _t
            else:
                exprs[symbol + index] = "\"%s\"" % _t
            
            sent = sent.replace(e, ' the %s %s' % (_type, symbol) + index, 1)
        
        return sent
    
    def _extract_char_or_pattern(self, sent: str, exprs: Dict) -> str:
        """Extract 'x' or 'y' characters pattern."""
        if r := re.findall(r'((\'[^ ]+\')\s+or\s+(\'[^ ]+\')\s+characters)', sent):
            r = r[0]
            s = r[0]
            _s = s.replace('characters', '')
            t = r[1:]
            for i, e in enumerate(t):
                index = chr(i + 97)
                exprs['chrx_' + index] = e
                _s = _s.replace(e, ' chrx_' + index, 1)
            sent = sent.replace(s, _s)
        return sent
    
    def _extract_single_quoted_chars(self, sent: str, exprs: Dict) -> str:
        """Extract single-quoted character literals."""
        if r := re.findall(r'(\'[^, ]+\')', sent):
            for i, e in enumerate(r):
                index = chr(i + 97)
                exprs['chry_' + index] = e
                sent = sent.replace(e, 'the chry_' + index, 1)
        return sent
    
    def _extract_double_quoted_strings(self, sent: str, exprs: Dict) -> str:
        """Extract double-quoted string literals (multiple specificity levels)."""
        # Level 1: Simple strings without commas
        if r := re.findall(r'(\"[^, ]+\")', sent):
            for i, e in enumerate(r):
                index = chr(i + 97)
                exprs['stry_' + index] = e
                sent = sent.replace(e, ' stry_' + index, 1)
        
        # Level 2: Strings with letters and commas
        if r := re.findall(r'(\"[a-zA-Z, ]+\")', sent):
            for i, e in enumerate(r):
                index = chr(i + 97)
                exprs['strz_' + index] = e
                sent = sent.replace(e, ' strz_' + index, 1)
        
        # Level 3: Strings without commas
        if r := re.findall(r'(\"[^,]+\")', sent):
            for i, e in enumerate(r):
                index = chr(i + 97)
                exprs['strl_' + index] = e
                sent = sent.replace(e, ' strl_' + index, 1)
        
        # Level 4: Catch-all for remaining quoted strings
        if r := re.findall(r'(\".*\")', sent):
            for i, e in enumerate(r):
                index = chr(i + 97)
                v = e
                if ',' in v:
                    v = v.replace(' ', '')
                exprs['strkk_' + index] = v
                sent = sent.replace(e, ' strkk_' + index, 1)
        
        return sent
    
    def _handle_power_sign(self, sent: str) -> str:
        """Replace ^ with _pow_ in words."""
        words = sent.split(' ')
        targets = {}
        for w in words:
            if "^" in w:
                targets[w] = w.replace("^", "_pow_")
        for k, v in targets.items():
            sent = sent.replace(k, v)
        return sent
    
    def _fix_type_distribution(self, sent: str) -> str:
        """Fix 'A or B noun' patterns where the noun should distribute to both.
        
        For example: 'str_a or str_b strings' -> 
                     'the type_string_ str_a or the type_string_ str_b'
        """
        types = ['characters', 'strings']
        pattern = r'(str_[^ ]+)\s+(or)\s+(str_[^ ]+)\s+(\b(?:{})\b)'.format('|'.join(types))
        
        if r := re.findall(pattern, sent):
            from nltk.stem import WordNetLemmatizer
            lemmatizer = WordNetLemmatizer()
            
            t = r[0]
            a = t[0]
            conj = t[1]
            b = t[2]
            _type = 'type_' + lemmatizer.lemmatize(t[3]) + '_'
            sent = re.sub(
                r'%s\s+%s\s+%s\s+%s' % (a, conj, b, t[3]),
                r'the %s %s %s the %s %s' % (_type, a, conj, _type, b),
                sent
            )
        
        return sent
