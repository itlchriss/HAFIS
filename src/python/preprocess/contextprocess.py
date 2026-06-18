import re
import os
import yaml
from typing import List, Dict, Tuple, Optional

# processing the text related to parameter, including the symbols, types that explicitly mentioned in the sentence
# processing the text related to method return, including the return type, text refer to the result, etc.


# these are java primitive and reference types
# we should extend this list to support more data types
primitive_datatypes = ['boolean', 'byte', 'character', 'short', 'integer', 'long', 'float', 'double', 'nat', 'non-negative integer']
reference_datatypes = ['array', 'string', 'object', 'list', 'set', 'sequence', 'multiset', 'map']


rulespath = os.path.join('.', 'rules')
ALT_RULE_FILENAME = 'alt.yml'

# Hardcoded rules for special patterns (always applied first due to length)
HARDCODED_RULES = {
    "arithmetic_operators": [
        "operators ('+', '-', '*', '/')",
        "operators ('+' , '-' , asterisk , '/')",
        "operators `('+' , '-' , '*' , '/')`",
        "operators `('+' , '-' , asterisk , '/')`",
    ]
}


class AltRuleEngine:
    """
    Simple rule engine with automatic longest-match-first ordering.
    
    All alternative strings are replaced by their canonical form.
    Rules are automatically sorted by pattern length (longest first)
    to handle conflicts where one pattern is a substring of another.
    """
    
    def __init__(self):
        # List of (alternative, canonical) tuples, sorted by alternative length (longest first)
        self.rules: List[Tuple[str, str]] = []
        self._loaded = False
    
    def load_rules(self, filepath: str):
        """
        Load rules from YAML file and auto-sort by alternative length (longest first).
        
        YAML format:
        - canonical_form:
            - alternative1
            - alternative2
        """
        with open(filepath, 'r', encoding='utf-8') as fp:
            data = yaml.safe_load(fp)
        
        if not data:
            return
        
        # Add hardcoded rules first
        for canonical, alternatives in HARDCODED_RULES.items():
            for alt in alternatives:
                self.rules.append((alt, canonical))
        
        # Parse YAML rules
        for entry in data:
            if not isinstance(entry, dict):
                continue
            for canonical, alternatives in entry.items():
                if canonical and alternatives:
                    for alt in alternatives:
                        if alt:
                            self.rules.append((alt.strip(), str(canonical).strip()))
        
        # Auto-sort by alternative length (longest first)
        # This ensures longer patterns are matched before shorter ones
        self.rules.sort(key=lambda x: -len(x[0].split()))
        self._loaded = True
    
    def apply(self, sentence: str) -> str:
        """
        Apply rules with token-aware matching.
        
        For each rule (in longest-first order):
        - Find matches at token boundaries
        - Replace matched tokens with canonical
        - Mark those positions as consumed (won't be re-matched)
        """
        if not self._loaded:
            return sentence
        
        # Remove trailing period for processing
        has_period = sentence.endswith('.')
        if has_period:
            sentence = sentence[:-1]
        
        # Tokenize the sentence
        tokens = sentence.split()
        
        # Track which token positions have been consumed
        consumed = set()
        
        # Apply each rule in order (longest alternatives first)
        for alternative, canonical in self.rules:
            alt_tokens = alternative.lower().split()
            alt_len = len(alt_tokens)
            
            if alt_len == 0:
                continue
            
            # Find all matches for this rule
            i = 0
            while i <= len(tokens) - alt_len:
                # Skip if any position in this range is already consumed
                if any(pos in consumed for pos in range(i, i + alt_len)):
                    i += 1
                    continue
                
                # Check for match (case-insensitive)
                match = True
                for j, alt_tok in enumerate(alt_tokens):
                    if tokens[i + j].lower() != alt_tok:
                        match = False
                        break
                
                if match:
                    # Mark positions as consumed
                    for pos in range(i, i + alt_len):
                        consumed.add(pos)
                    # Replace tokens: keep first position, replace with canonical, mark rest for removal
                    canonical_tokens = canonical.split()
                    tokens[i] = ' '.join(canonical_tokens)
                    # Mark extra positions for removal (we'll filter them out later)
                    for pos in range(i + 1, i + alt_len):
                        tokens[pos] = None
                    i += alt_len
                else:
                    i += 1
        
        # Filter out None tokens (positions that were merged)
        tokens = [t for t in tokens if t is not None]
        
        # Reconstruct sentence
        result = ' '.join(tokens)
        if has_period:
            result += '.'
        
        return result
    
    def validate(self) -> List[str]:
        """
        Validate rules and return list of potential conflicts/warnings.
        
        Checks for:
        - Duplicate alternatives mapping to different canonicals
        """
        warnings = []
        alt_to_canonicals: Dict[str, List[str]] = {}
        
        for alt, canonical in self.rules:
            alt_lower = alt.lower()
            if alt_lower not in alt_to_canonicals:
                alt_to_canonicals[alt_lower] = []
            if canonical not in alt_to_canonicals[alt_lower]:
                alt_to_canonicals[alt_lower].append(canonical)
        
        for alt, canonicals in alt_to_canonicals.items():
            if len(canonicals) > 1:
                warnings.append(f"Alternative '{alt}' maps to multiple canonicals: {canonicals}")
        
        return warnings


# Global rule engine instance
_rule_engine: Optional[AltRuleEngine] = None


def get_rule_engine() -> AltRuleEngine:
    """Get or create the global rule engine instance."""
    global _rule_engine
    if _rule_engine is None:
        _rule_engine = AltRuleEngine()
        _rule_engine.load_rules(os.path.join(rulespath, ALT_RULE_FILENAME))
    return _rule_engine



class ContextProcessor:

    def __init__(self) -> None:
        self.dynamic_si = {}
        self._rule_engine = get_rule_engine()

    def _type_processor(self):
        """Convert data type mentions to type tokens (_type_X_ format).
        
        This runs BEFORE _parameter_syntax_processor() so that types are
        recognised in ALL contexts (before variable names, standalone, etc.),
        not only when followed by 'parameter' or 'result'.
        
        The _type_X_ format (with leading and trailing underscores) matches
        the lexer pattern KEYWORD_TYPE = _type[_a-z]+_ in lex.l.
        
        Processing order:
        1. Combined types (e.g. 'integer array' -> _type_integer_array_)
        2. Standalone reference types (e.g. 'the array x' -> _type_array_ x)
        3. Standalone primitive types (e.g. 'the integer x' -> _type_integer_ x)
        4. Fix article agreement (e.g. 'an _type_integer_' -> 'a _type_integer_')
        """
        sent = self.sent
        if sent.endswith('.'):
            sent = sent[:-1]

        # Build set of multi-word primitives for exclusion lookaheads
        multi_word_prims = {p for p in primitive_datatypes if ' ' in p}

        # --- Pass 1: Combined types (primitive + reference) ---
        # Process longer primitive names first (e.g. 'non-negative integer' before 'integer')
        sorted_prims = sorted(primitive_datatypes, key=len, reverse=True)

        # Special case: 'non-negative integer' has a hyphen that breaks the
        # generic lookbehind logic. Handle it explicitly first.
        for r in reference_datatypes:
            sent = re.sub(
                r'(?<!_type_)\bnon\-negative\s+integer\s+' + r + r'\b',
                '_type_non_negative_integer_' + r + '_', sent)
        # Standalone 'non-negative integer' (before a word or at end of sentence)
        sent = re.sub(
            r"(?<!_type_)\b(?:the|a|an)\s+(non\-negative\s+integer)\s+(?=[\w'])",
            lambda m: m.group(0).replace(m.group(1), '_type_non_negative_integer_'), sent)
        sent = re.sub(
            r'(?<!_type_)\b(?:the|a|an)\s+(non\-negative\s+integer)\.?\s*$',
            lambda m: m.group(0).replace(m.group(1), '_type_non_negative_integer_'), sent)

        for p in sorted_prims:
            p_pat = p.replace('-', r'\-')
            safe_p = p.replace('-', '_')

            # Build negative lookbehinds to avoid matching sub-terms inside
            # longer compound types (e.g. 'integer' inside 'non-negative integer')
            lookbehinds = ''
            if ' ' in p:
                words = p.split()
                lookbehinds += '(?<!' + words[-1].replace('-', r'\-') + r'\s)'
            if p.startswith('non-'):
                lookbehinds += '(?<!non\\-)'
                lookbehinds += '(?<!non\\-\\s)'
            if 'negative' in p:
                lookbehinds += '(?<!negative\\s)'

            for r in reference_datatypes:
                # e.g. 'integer array' -> '_type_integer_array_'
                sent = re.sub(
                    r'(?<!_type_)' + lookbehinds + r'\b' + p_pat + r'\s+' + r + r'\b',
                    '_type_' + safe_p + '_' + r + '_', sent)

        # --- Pass 2: Standalone reference types ---
        _ref_with_string = ['string array'] + reference_datatypes
        for c in _ref_with_string:
            c_pat = c.replace('-', r'\-')
            c_safe = c.replace(' ', '_')
            # After article, before a word (variable name)
            sent = re.sub(
                r'(?<!_type_)\b(?:the|a|an)\s+(' + c_pat + r")\s+(?=[\w'])",
                lambda m: m.group(0).replace(m.group(1), '_type_' + c_safe + '_'), sent)
            # At end of sentence
            sent = re.sub(
                r'(?<!_type_)\b(?:the|a|an)\s+(' + c_pat + r')\s*$',
                lambda m: m.group(0).replace(m.group(1), '_type_' + c_safe + '_'), sent)

        # --- Pass 3: Standalone primitive types ---
        for p in sorted_prims:
            p_pat = p.replace('-', r'\-')
            safe_p = p.replace('-', '_')

            lookbehinds = ''
            if ' ' in p:
                words = p.split()
                lookbehinds += '(?<!' + words[-1].replace('-', r'\-') + r'\s)'
            if p.startswith('non-'):
                lookbehinds += '(?<!non\\-)'
                lookbehinds += '(?<!non\\-\\s)'
            if 'negative' in p:
                lookbehinds += '(?<!negative\\s)'

            # Build negative lookahead to prevent matching a prefix of a
            # longer multi-word primitive (e.g. 'non-negative' should not
            # match when followed by 'integer')
            lookahead = ''
            if p in multi_word_prims:
                other_words = [mp.split()[-1] for mp in multi_word_prims
                               if mp != p and mp.startswith(p.split()[0])]
                if other_words:
                    lookahead = '(?!\\s+(?:' + '|'.join(other_words) + '))'
            # Also prevent 'non-negative' from matching when 'integer' follows
            if p == 'non-negative':
                lookahead = '(?!\\s+integer)'

            # After article, before a word (variable name)
            sent = re.sub(
                r'(?<!_type_)' + lookbehinds + r'\b(?:the|a|an)\s+(' + p_pat + r')' + lookahead + r"\s+(?=[\w'])",
                lambda m: m.group(0).replace(m.group(1), '_type_' + safe_p + '_'), sent)
            # At end of sentence (including before period)
            sent = re.sub(
                r'(?<!_type_)' + lookbehinds + r'\b(?:the|a|an)\s+(' + p_pat + r')' + lookahead + r'\.?\s*$',
                lambda m: m.group(0).replace(m.group(1), '_type_' + safe_p + '_'), sent)

        # --- Pass 4: Fix article agreement ---
        # 'an _type_X_' -> 'a _type_X_' (_type_ starts with consonant sound)
        sent = re.sub(r'\ban\s+(_type_)', r'a \1', sent)

        self.sent = sent

    def _parameter_syntax_processor(self):
        sent = self.sent
        if sent[-1] == '.':
            sent = sent[:-1]
        combined_datatypes = ['%s %s' % (p, r) for p in primitive_datatypes for r in reference_datatypes]
        for c in combined_datatypes:
            sent = re.sub(r'\s+' + c + r'\s+parameters\s+', ' _type_' + c.replace(' ', '_') + '_ parameters ', sent)
            sent = re.sub(r'\s+' + c + r"\s+parameters's\s+", ' _type_' + c.replace(' ', '_') + "_ parameters's ", sent)
            
            sent = re.sub(r'\s+' + c + r'\s+parameter\s+', ' _type_' + c.replace(' ', '_') + '_ parameter ', sent)
            sent = re.sub(r'\s+' + c + r"\s+parameter's\s+", ' _type_' + c.replace(' ', '_') + "_ parameter's ", sent)
            sent = re.sub(r'\s+' + c + r'\s+result\s+', ' _type_' + c.replace(' ', '_') + '_ result ', sent)
            sent = re.sub(r'\s+' + c + r'\s+result', ' _type_' + c.replace(' ', '_') + '_ result ', sent)
            sent = re.sub(r'\s+' + c + r"\s+result's\s+", ' _type_' + c.replace(' ', '_') + "_ result's ", sent) 
        for c in primitive_datatypes:
            sent = re.sub(r'\s+' + c + r'\s+parameters\s+', ' _type_' + c.replace(' ', '_') + '_ parameters ', sent)
            sent = re.sub(r'\s+' + c + r'\s+parameter\s+', ' _type_' + c.replace(' ', '_') + '_ parameter ', sent)
            sent = re.sub(r'\s+' + c + r'\s+result\s+', ' _type_' + c.replace(' ', '_') + '_ result ', sent)

        _reference_datatypes =  ['string array'] + reference_datatypes
        for c in _reference_datatypes:
            sent = re.sub(r'\s+' + c + r"\s+parameters\s+", ' _type_' + c.replace(' ', '_') + "_ parameters ", sent)
            sent = re.sub(r'\s+' + c + r"\s+parameters's\s+", ' _type_' + c.replace(' ', '_') + "_ parameters's ", sent)
            sent = re.sub(r'\s+' + c + r"\s+parameter\s+", ' _type_' + c.replace(' ', '_') + "_ parameter ", sent)
            sent = re.sub(r'\s+' + c + r"\s+parameter's\s+", ' _type_' + c.replace(' ', '_') + "_ parameter's ", sent)
            sent = re.sub(r'\s+' + c + r'\s+result\s+', ' _type_' + c.replace(' ', '_') + '_ result ', sent)
            sent = re.sub(r'\s+' + c + r"\s+result's\s+", ' _type_' + c.replace(' ', '_') + "_ result's ", sent)        
        
        if r := re.findall(r'parameter (`[0-9a-zA-Z_]+`) and (`[0-9a-zA-Z_]+`)', sent, re.ASCII):
            # the case of composite subject/object with two parameters
            # we should convert both of them
            for param in r[0]:
                pattern = 'param_%s_' % param.replace('`', '')
                sent = sent.replace(param, pattern)
        elif r := re.findall(r'parameters (`[0-9a-zA-Z_]+`) and (`[0-9a-zA-Z_]+`)', sent, re.ASCII):
            # the case of composite subject/object with two parameters
            # we should convert both of them
            for param in r[0]:
                pattern = 'param_%s_' % param.replace('`', '')
                sent = sent.replace(param, pattern)
        elif r := re.findall(r'parameter (`[0-9a-zA-Z_]+`)', sent, re.ASCII):
            for param in r:
                pattern = 'param_%s_' % param.replace('`', '')
                sent = sent.replace(param, pattern)
        elif r := re.findall(r'(parameter [0-9a-zA-Z_]+)', sent, re.ASCII):
            for param in r:
                pattern = 'param_%s_' % param.replace('`', '').replace('parameter ', '')
                sent = sent.replace(param, pattern)
        self.sent = sent
        
        # NOTE: because LLM has already recognised the parameter. If 'param_' exists, it means that LLM has provided the parameter information and we have tackled it.
        #       therefore, in this case, the word 'parameter' can be skipped.
        if 'param_' in self.sent:
            self.sent = self.sent.replace('parameters', '')
            self.sent = self.sent.replace('parameter', '')            

    def _synonym_syntax_preprocessor(self):
        """Apply synonym rules using token-aware matching with longest-match-first ordering."""
        if self.sent[-1] == '.':
            self.sent = self.sent[:-1]
        self.sent = self.sent.replace('  ', ' ')
        
        # Use the rule engine for token-aware replacement
        self.sent = self._rule_engine.apply(self.sent)
        
    def _symbol_syntax_preprocessor(self):
        if self.sent[-1] == '.':
            self.sent = self.sent[:-1]  
        self.sent = re.sub(r"','", ' comma ', self.sent)
        self.sent = re.sub(r"'\?'", ' questionmark ', self.sent)
        self.sent = re.sub(r"'\*'", ' asterisk ', self.sent)
        self.sent = re.sub(r"'\.'", ' period ', self.sent)
        self.sent = re.sub(r"`*'\s*\(\s*'`*", 'leftp', self.sent)
        self.sent = re.sub(r"`*'\s*\)\s*'`*", 'rightp', self.sent)
        self.sent = re.sub(r"`*'\s*\[\s*'`*", 'leftbp', self.sent)
        self.sent = re.sub(r"`*'\s*\]\s*'`*", 'rightbp', self.sent)
        self.sent = re.sub(r"`*'\s*\{\s*'`*", 'leftb', self.sent)
        self.sent = re.sub(r"`*'\s*\}\s*'`*", 'rightb', self.sent)

    # Known SI terms that support possessive "X of Y" → "Y's X" transformation
    possessable_terms = {'length', 'sum', 'size', 'minimum_value', 'maximum_value',
                         'first_element', 'second_element', 'summation',
                         'string_representation', 'ones_complement',
                         'number_of_unique_elements', 'hexadecimal_representation'}

    # Ordinal words → (number, suffix) for normalization
    ORDINAL_WORDS = {
        'third': (3, 'rd'), 'fourth': (4, 'th'), 'fifth': (5, 'th'),
        'sixth': (6, 'th'), 'seventh': (7, 'th'), 'eighth': (8, 'th'),
        'ninth': (9, 'th'), 'tenth': (10, 'th'), 'eleventh': (11, 'th'),
        'twelfth': (12, 'th'), 'thirteenth': (13, 'th'), 'fourteenth': (14, 'th'),
        'fifteenth': (15, 'th'), 'sixteenth': (16, 'th'), 'seventeenth': (17, 'th'),
        'eighteenth': (18, 'th'), 'nineteenth': (19, 'th'), 'twentieth': (20, 'th'),
    }

    # Words that can follow an ordinal to form an element-access expression
    ELEMENT_NOUNS = ('element', 'value', 'character', 'item', 'entry')

    @staticmethod
    def _ordinal_suffix(n: int) -> str:
        """Return the ordinal suffix (st/nd/rd/th) for a positive integer."""
        if n % 100 in (11, 12, 13):
            return 'th'
        return {1: 'st', 2: 'nd', 3: 'rd'}.get(n % 10, 'th')

    def _is_possessable(self, term: str) -> bool:
        """Check if a term supports 'X of Y' → 'Y's X' possessive transformation.
        
        Recognises:
        - Known SI terms in possessable_terms
        - Any Nth_element pattern (1st_element, 2nd_element, 3rd_element, etc.)
        """
        if term in self.possessable_terms:
            return True
        if re.match(r'^\d+(st|nd|rd|th)_element$', term):
            return True
        return False

    def _normalize_ordinals(self):
        """Normalize ordinal expressions to Nth_element form.
        
        Handles:
        - Ordinal word + element noun: "third element" → "3rd_element"
        - Numeric ordinal + element noun: "3rd element" → "3rd_element"
        - "element/value/character at index N" → "Nth_element"
        
        Runs after alt.yml synonym processing, which already converts
        "first element/value/character" → first_element and
        "second element" → second_element and
        "value at index 0" → first_element, "value at index 1" → second_element.
        """
        sent = self.sent
        
        # "element/value/character at index N" → "Nth_element"
        # Skip index 0 and 1 - those are handled by alt.yml (first_element, second_element)
        def _at_index_repl(m):
            n = int(m.group(1))
            if n <= 1:
                return m.group(0)  # leave for alt.yml to handle
            return f'{n}{self._ordinal_suffix(n)}_element'
        sent = re.sub(
            r'(?:element|value|character|item|entry)\s+at\s+index\s+(\d+)',
            _at_index_repl, sent, flags=re.IGNORECASE)
        
        # Numeric ordinal + element noun: "3rd element" → "3rd_element"
        def _numeric_ordinal_repl(m):
            n = int(m.group(1))
            return f'{n}{self._ordinal_suffix(n)}_element'
        sent = re.sub(
            r'(\d+)(?:st|nd|rd|th)\s+(?:' + '|'.join(self.ELEMENT_NOUNS) + r')s?',
            _numeric_ordinal_repl, sent, flags=re.IGNORECASE)
        
        # Ordinal word + element noun: "third element" → "3rd_element"
        for word, (n, suffix) in self.ORDINAL_WORDS.items():
            pattern = r'\b' + word + r'\s+(?:' + '|'.join(self.ELEMENT_NOUNS) + r')s?\b'
            sent = re.sub(pattern, f'{n}{suffix}_element', sent, flags=re.IGNORECASE)
        
        self.sent = sent

    # converting 'length of x' to 'x's length'
    def _of_2_possesive(self):
        arr = self.sent.split(' ')
        while 'of' in arr:
            index = arr.index('of')
            if index < 2 or arr[index - 2].lower() != 'the' or not self._is_possessable(arr[index - 1]):
                break
            # Skip article "the"/"a"/"an" after "of" so the noun becomes the possessor
            after_of = index + 1
            if after_of < len(arr) and arr[after_of].lower() in ('the', 'a', 'an'):
                after_of += 1
            if after_of >= len(arr):
                break
            # Collect the type token(s) after "of" (e.g. type_integer_array_)
            type_token = arr[after_of]
            # Look for a param_ token after the type, skipping empty strings
            param_token = None
            param_idx = None
            for k in range(after_of + 1, len(arr)):
                if arr[k] == '':
                    continue
                if arr[k].startswith('param_'):
                    param_token = arr[k]
                    param_idx = k
                break
            if param_token is not None:
                # "The length of the type_integer_array_ param_numbers_ ..."
                # -> "param_numbers_'s type_integer_array_ length ..."
                t = param_token + "'s " + type_token + ' ' + arr[index - 1]
                arr = arr[:index - 1] + [t] + arr[param_idx + 1:]
            else:
                t = type_token + "'s " + arr[index - 1]
                arr = arr[:index - 1] + [t] + arr[after_of + 1:]
        self.sent = ' '.join(arr)
            

    def run(self, sent: str) -> str:
        self.sent = sent        
        self._symbol_syntax_preprocessor()
        self._synonym_syntax_preprocessor()
        self._normalize_ordinals()
        self._type_processor()
        self._parameter_syntax_processor()
        self._of_2_possesive()
        return self.sent