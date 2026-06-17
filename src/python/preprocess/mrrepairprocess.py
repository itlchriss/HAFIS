import os
from typing import List
from enum import Enum
import math
import pandas as pd
import re
import yaml
from word2number import w2n


sispecspath = './specs/si/typed_si.yml'
def _get_specs():
    si = {}
    with open(sispecspath, encoding='utf-8') as fp:
        si = yaml.full_load(fp)
    return si

# the enumeration and constant UNDEFINED values must be consistent with the declarations in cst.h
UNDEFINED = -1
ANY = '*'

class IntEnum(Enum):
    def __int__(self):
        return self.value
    
    def __str__(self):
        return str(self.value)

class Primitive_datatype(IntEnum):
    Boolean = 0
    Byte = 1
    Char = 2
    Short = 3
    Integer = 4
    Long = 5
    Float = 6
    Double = 7
    


class Reference_datatype(IntEnum):
    Array = 0
    String = 1
    Object = 2


# denote __type__ as datatype in datatypes
# denote __num__ as a numerical value

SI_data = None

def __check_is_numeric__(word: str) -> bool:
    return word.isnumeric() or (word.startswith('-') and word.count('-') == 1 and word.replace('-', '').isnumeric())

def __check_is_char__(word: str) -> bool:
    return len(word) == 3 and word[0] == '\'' and word[2] == '\''

def __check_is_boolean__(word: str) -> bool:
    return word == 'true' or word == 'false'

def __check_is_be__(word: str) -> bool:
    return word == 'is' or word == 'are'

def __check_is_param__(word: str) -> bool:
    return (word.startswith('`') and word[-1] == '`') or (word.startswith('param_') and word[-1] == '_')

def __check_is_param_or_result__(word: str) -> bool:
    return (word.startswith('`') and word[-1] == '`') or (word.startswith('param_') and word[-1] == '_') or word == 'result'

def __check_is_expr__(word: str) -> bool:
    pattern = r'_expr\d+_'
    return re.match(pattern, word)

def __check_is_quoted__(word: str) -> bool:
    return word.startswith('`') and word.endswith('`')

def __check_is_type__(word: str) -> bool:
    return word.startswith('type_')

def __check_is_comparative__(word: str) -> bool:
    return word.endswith('er')

def __perform_sum__(sent: list) -> str:
    a = int(sent[0])
    b = int(sent[2])
    return str(a + b)

def __perform_diff__(sent: list) -> str:
    return str(pd.eval(' '.join(sent)))

def __perform_product__(sent: list) -> str:
    sent = list(map(lambda x: '*' if x == 'times' else x, sent))
    return str(pd.eval(' '.join(sent)))

def __get_number__(sent: list) -> str:
    for i in sent:
        if __check_is_numeric__(i):
            return str(i)
    return None

def __convert_to_vocontain__(sent: list) -> str:
    p = sent[0].replace("'s", '')
    return "%s vocontains" % p

def __check_is_string__(word: str) -> bool:
    return word.startswith('"') and word.endswith('"')

def __check_is_si_term__(word: str) -> bool:
    global SI_data
    if not SI_data:
        SI_data = _get_specs()
    terms = [si["term"] for si in SI_data]
    return word in terms

def __check_is_num_word__(word: str) -> bool:
    try:
        return isinstance(w2n.word_to_num(word), int)
    except:
        return False 

def __convert_num_word__(word: str) -> int:
    return w2n.word_to_num(word)


words_4_chartype = [
    'digits', 'English letters', 'alphabets', 'numbers'
]

def __check_is_chartype__(word: str) -> str:
    return word in words_4_chartype


word_4_restrictive_adverb = [
    'only'
]

def __check_is_restrictive_adverb__(word: str) -> str:
    return word in word_4_restrictive_adverb


# returning the index that the pattern starts at, or -1 indicates the pattern is not found
def __words_contain_pattern__(words: List[str], pattern: List[str]) -> int:
    for i in range(len(words) - len(pattern) + 1):   
        for j in range(len(pattern)):     
            if (pattern[j] in func_map and not func_map[pattern[j]](words[i + j])) or (not pattern[j] in func_map and words[i + j] != pattern[j]):            
                break
        else:
            return i
    return -1

def __match_any_word__(word: str) -> bool:
    return word.isalpha()

def __check_is_SI_string__(word: str) -> bool:
    return re.match(r'^str[a-z]_[a-z]$', word)

def __check_is_posessive_preposition__(word: str) -> bool:
    return word == 'of' or word == 'in'

def __check_is_values_with_param_or_result__(word: str) -> bool:
    return word.endswith("'s") and (word.replace("'s", '') == 'result' or word.startswith("param_"))

def __check_is_contain__(word: str) -> bool:
    return word == 'contain' or word == 'contains'

def __check_is_array__(word: str) -> bool:
    return word.startswith('arr_')

func_map = {
    '__param_or_result__': __check_is_param_or_result__,
    '__num__': __check_is_numeric__,
    '__be__': __check_is_be__,
    '__quoted__': __check_is_quoted__,
    '__string__': __check_is_string__,
    '__char__': __check_is_char__,
    '__bool__': __check_is_boolean__,
    '__param__': __check_is_param__,
    '__type__': __check_is_type__,
    '__sum__': __perform_sum__,
    '__diff__': __perform_diff__,
    '__product__': __perform_product__,
    '__expr__': __check_is_expr__,
    '__comparative__': __check_is_comparative__,
    '__chartype__': __check_is_chartype__,
    '__restrictive_adverb__': __check_is_restrictive_adverb__,
    '__si_term__': __check_is_si_term__,
    '__num_word__': __check_is_num_word__,
    '__filter_num__': __get_number__,
    '__word__': __match_any_word__,
    '__expr_string__': __check_is_SI_string__,
    '__pos_prep__': __check_is_posessive_preposition__,
    '__param_or_result_values__': __check_is_values_with_param_or_result__,
    '__vocontain__': __convert_to_vocontain__,  
    '__contain__': __check_is_contain__,
    '__array__': __check_is_array__,
}

label_primitive_type = 'primitive_type'
label_reference_type = 'reference_type'
label_interpretation_type = 'interpretation_type'
label_symbol = 'symbol'

SYNTAX_RULES_PATH = os.path.join('.', 'rules', 'syntax_rules.yml')
REPAIR_PATTERNS_PATH = os.path.join('.', 'rules', 'repair_patterns.yml')


def load_repair_patterns(filepath=None):
    """Load clause-level repair patterns from a YAML file.
    
    Returns a dict keyed by clause type (partial_equal, complex_clause, etc.),
    each containing a list of pattern dicts.
    Falls back to empty dict if file not found.
    """
    path = filepath or REPAIR_PATTERNS_PATH
    if not os.path.exists(path):
        return {}
    with open(path, 'r', encoding='utf-8') as fp:
        data = yaml.safe_load(fp)
    return data or {}


def _ensure_default_fields(rule):
    """Ensure a syntax rule has all required fields with defaults."""
    defaults = {
        'pattern': [],
        'format': '',
        'symbol': '',
        'interpretation': '',
        'syntax': '',
        'arguments': [],
        'synthesised_datatype': {}
    }
    for key, value in defaults.items():
        if key not in rule:
            rule[key] = value
    return rule


def load_syntax_rules(filepath=None):
    """Load syntax transformation rules from a YAML file.
    
    Each rule specifies a word-level pattern to match and a format
    string to replace it with. Rules can optionally include SI metadata
    (symbol, interpretation, syntax, arguments, synthesised_datatype).
    
    Falls back to empty list if file not found.
    """
    path = filepath or SYNTAX_RULES_PATH
    if not os.path.exists(path):
        print('Warning: syntax rules file not found at %s' % path)
        return []
    with open(path, 'r', encoding='utf-8') as fp:
        data = yaml.safe_load(fp)
    if not data:
        return []
    return [_ensure_default_fields(rule) for rule in data]


general_syntax_rules = load_syntax_rules()

# there exists no non-repeating character in the type_string_ param_s_



reqtype_ignore_rules = {
    'requires': {
        },
    'ensures': {
        "`answer`": 'keyword_result'
    }
}

def __process_to_later_clause__(sent, r) -> str:
    type_str = r.group(1)
    param_str = r.group(2)
    other_str = r.group(3)
    ans = None
    if type_str == 'type_integer_array_':
        ans = '0'
    elif type_str == 'type_string_':
        ans = "'0'"
    return "The %s %s %s and the %s %s's first_element is equal to %s." % (type_str, param_str, other_str, type_str, param_str, ans)


class RepairProcessor:    
    
    def __init__(self):
        self._t = None
        self.dynamic_si = {}
        self._repair_patterns = load_repair_patterns()
    
    def __process_negative__(self, sent):
        words = sent.split(' ')
        targets = {}
        arr_flag = False
        for w in words:
            if '[' in w:
                arr_flag = True
            if '^' in w and arr_flag:
                arr_flag = False
            if w.startswith('-') and w.count('-') == 1 and w.replace('-', '').isnumeric() and not arr_flag:
                targets[w] = 'negative ' + w.replace('-', '')
        for k in targets:
            sent = sent.replace(k, targets[k])
        return sent

    def __process_power_sign__(self, sent):
        words = sent.split(' ')
        targets = {}        
        for w in words:
            if "^" in w:
                x = w.replace("^", "**")
                try:
                    targets[w] = str(pd.eval(x))
                except:
                    pass
            if "<sup>" in w and "</sup>" in w:
                arr = w.replace("<sup>", ' ').replace("</sup>", '').strip().split(' ')
                if len(arr) == 2 and arr[0].isnumeric() and arr[1].isnumeric():
                    targets[w] = str(math.pow(int(arr[0]), int(arr[1]))).split('.')[0]
        for k in targets:            
            sent = sent.replace(k, targets[k])
        return sent

    def __process_range_sign__(self, sent):
        range_p = r'range\s+of\s+\[(.*)\]'
        if r := re.search(range_p, sent):
            s = [i.strip() for i in r.group(1).split(',')]
            s = [pd.eval(i) for i in s]
            new = 'range of %s to %s' % (str(s[0]), str(s[1]))
            sent = re.sub(range_p, new, sent)
        return sent
    
    def __process_partial_equal(self, sent) -> str:
        patterns = self._repair_patterns.get('partial_equal', [])
        result = ''
        target = ''
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                target = r.group(0)
                length = r.group(1)
                data = re.findall(r'\d+', r.group(0))         
                if not length.isdigit():                   
                    length = str(__convert_num_word__(length))
                else:                    
                    data = data[1:]
                type_str = r.group(2)
                param_str = r.group(3)                        
                symbol = 'from_%s_%s_integer_sequence_' % (str(0), str(int(length) - 1))
                result = 'the %s %s is partially_equal to the type_integer_array_ %s' % (type_str, param_str, symbol)
                sent = sent.replace(target, result)                
                interpretation = '_'.join([str(0), str(int(length) - 1)]) + '_' + ','.join([str(i) for i in data])
                self.dynamic_si[symbol] = interpretation
        return sent
    
    def __process_semantic_correction(self, sent) -> str:
        patterns = self._repair_patterns.get('semantic_correction', [])
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                if pattern['constraint'] in r.group(pattern['index']):
                    type_str = r.group(1)
                    param_str = r.group(2)
                    return pattern['template'] % (type_str, param_str)
        return sent
    
    def __process_limited_equal(self, sent) -> str:
        patterns = self._repair_patterns.get('limited_equal', [])
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                target = r.group(0)
                data = re.findall(r'\d+', r.group(0))         
                symbol = '_fixed_integer_sequence_'                
                sent = sent.replace(target, 'are equal to the type_integer_array_ '+ symbol)                
                self.dynamic_si[symbol] = ','.join([str(i) for i in data])
        return sent
    
    
    # TODO: to be combined the two functions
    def __process_complex_clause(self, sent) -> str:
        patterns = self._repair_patterns.get('complex_clause', [])
        tmp = sent.replace(', - ,', ', minus ,')
        for pattern in patterns:
            if r := re.search(pattern['pattern'], tmp):
                if not r.group(0).endswith('and the') and not r.group(0).endswith('and does'):
                    symbol = 'checking_character_sequence_'
                    if 'template' not in pattern.keys():
                        verb = r.group(1)
                        if 'target' in pattern.keys():
                            index = pattern['target']
                        else:
                            index = 2
                        connective = ''
                        if 'connective' in pattern.keys():
                            connective = pattern['connective']
                        else:
                            if 'or' in r.group(index):
                                connective = 'or'
                            else:
                                connective = 'and'
                        target = r.group(index).replace('the', '').replace(' ', '').replace('or', ',').replace('and', ',').replace(',,', ',')
                        if 'characters' in target:
                            target = target.replace('characters', '')
                        else:
                            target = target.replace('character', '')
                        target = target.replace("the", '')
                        if ',' in target:
                            self.dynamic_si[symbol] = '%s,%s' % (connective, target)
                            sent = tmp.replace(r.group(0), verb + ' the ' + symbol + ' ')
                    else:
                        values = r.group(1)
                        connective = ''
                        if 'or' in values:
                            connective = 'or'
                        else:
                            connective = 'and'
                        target = values.replace(connective, ',').replace("\'", '').replace(' ', '')
                        self.dynamic_si[symbol] = '%s,%s' % (connective, target)
                        sent = tmp.replace(r.group(0), pattern['template'] + ' ' + symbol)
        return sent
    
    def __process_complex_clause2(self, sent) -> str:
        patterns = self._repair_patterns.get('complex_clause2', [])
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                if not r.group(0).endswith('and the') and not r.group(0).endswith('and'):
                    target = r.group(0)
                    type_str = r.group(1)
                    result = r.group(2)
                    result = re.sub(r'the\s+', '', result)
                    result = result.replace(' ', '').replace(pattern['connective'], '')
                    for s in result.split(','):
                        if sr := re.match(r'^str\w+_[a-z]$', s):
                            if s in self.current_dynamic_si.keys():
                                result = result.replace(s, self.current_dynamic_si[s])  
                        elif sr := re.match(r'^chr\w+_[a-z]$', s):
                            if s in self.current_dynamic_si.keys():
                                result = result.replace(s, self.current_dynamic_si[s])  
                    symbol = pattern['symbol']           
                    self.dynamic_si[symbol] = '%s,%s' % (pattern['connective'], result)
                    sent = sent.replace(target, pattern['template'] % symbol)
                    break
        return sent
    
    
    def __process_except_clause__(self, sent) -> str:
        patterns = self._repair_patterns.get('except_clause', [])
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                if 'handler' in pattern:
                    sent = __process_to_later_clause__(sent, r)
                    break
                else:
                    target = r.group(0)
                    type_str = r.group(1)
                    param_str = r.group(2)
                    zero = None
                    if type_str == 'type_integer_array_':
                        zero = str(0)
                    elif type_str == 'type_string_':
                        zero = "'0'"
                    if pattern['repeat'] == 1:
                        sent = sent.replace(target, pattern['template'] % (type_str, param_str, zero, type_str, param_str))
                    else:
                        sent = sent.replace(target, pattern['template'] % (type_str, param_str, zero))
        return sent
    
    def __process_power_clause__(self, sent) -> str:
        patterns = self._repair_patterns.get('power_clause', [])
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                target = r.group(0)
                base = r.group(1)
                type_str = r.group(2)
                power = r.group(3).replace('_', '')
                symbol = pattern['symbol']
                self.dynamic_si[symbol] = 'Math.pow(%s,%s)' % (base, power)
                sent = sent.replace(target, pattern['template'] % type_str)
        return sent

    def __process_comma_separated_clause__(self, sent) -> str:
        patterns = self._repair_patterns.get('comma_separated_clause', [])
        for pattern in patterns:
            if r := re.search(pattern['pattern'], sent):
                # this is the SI symbol that the construct type SI will find
                symbol = 'csvdata'
                target = 'or'
                if r.group(1) == 'integers':
                    _range_str = r.group(2)
                    if _range_str in self.current_dynamic_si.keys():
                        _range_str = self.current_dynamic_si[_range_str]
                    if ',' in _range_str:
                        target += ',range'
                        for i in _range_str.split(','):
                            target += ',' + i.strip()

                target += ',equal'
                if r.group(3).startswith('chr'):
                    target += ',' + self.current_dynamic_si[r.group(3)]
                
                self.dynamic_si[symbol] = target
                sent = sent.replace(r.group(0), 'checking_csv')
        return sent
    
    def __nth_repl(s, sub, repl, n):
        find = s.find(sub)
        # If find is not -1 we have found at least one match for the substring
        i = find != -1
        # loop util we find the nth or we find no match
        while find != -1 and i != n:
            # find + 1 means we start searching from after the last match
            find = s.find(sub, find + 1)
            i += 1
        # If i is equal to n we found nth match so replace
        if i == n:
            return s[:find] + repl + s[find+len(sub):]
        return s
    
    def __process_general_syntax_rule__(self, words, rule, index):
        f = rule['format']
        pattern = rule['pattern']
        symbol = rule['symbol']
        interpretation = rule['interpretation']
        
        pairs = []
        if len(f.split(' ')) > 1:
            for i, x in enumerate(pattern):
                if x in func_map.keys():
                    if x == '__be__' and 'either' in self._org_sent:
                        f = f.replace(x, words[index + i])
                    elif x == '__quoted__':
                        # replacing the info with unquoted version
                        f = f.replace(x, words[index + i].replace('`', ''), 1)
                    else:
                        f = f.replace(x, words[index + i], 1)
                    
                    if x == '__be__' and '__be__' in f:
                        f = f.replace(x, words[index + i])
                    pairs.append((x, words[index + i]))
        elif f in func_map.keys():
            subsent = words[index: index + len(pattern)]
            f = func_map[f](subsent)
                
                
        if pairs:
            for k,v in pairs:
                if not symbol:
                    continue
                symbol = symbol.replace(k, v)     
                interpretation = interpretation.replace(k, v)
        if symbol:           
            self.dynamic_si[symbol] = { 
                                    'term': symbol.replace('-', '_dash_'),
                                    'syntax': [rule['syntax']],
                                    'arguments': rule['arguments'],  
                                    'synthesised_datatype': rule['synthesised_datatype'],                                   
                                    'interpretation': interpretation,
                                    }     
        words = words[:index] + [f] + words[index + len(pattern):]
        return ' '.join(words)

        
    def run(self, sent: str, t: str, current_dynamic_si = None) -> str:
        self._t = t
        self._org_sent = sent
        self.current_dynamic_si = current_dynamic_si
        if sent[-1] == '.':
            sent = sent[:-1]   
        if ',' in sent:
            sent = sent.replace(',', ' , ')   
        sent = re.sub(r'\s+', ' ', sent)     
        sent = self.__process_power_sign__(sent)
        sent = self.__process_range_sign__(sent)
        sent = self.__process_negative__(sent)  
        sent = self.__process_partial_equal(sent)
        sent = self.__process_limited_equal(sent)
        sent = self.__process_complex_clause(sent)
        sent = self.__process_complex_clause2(sent)
        sent = self.__process_except_clause__(sent)
        sent = self.__process_power_clause__(sent)
        sent = self.__process_comma_separated_clause__(sent)
        sent = self.__process_semantic_correction(sent)
        words = sent.split(' ')        
        for r in general_syntax_rules:
            pattern = r['pattern']
            while (i := __words_contain_pattern__(words, pattern)) >= 0:
                sent = self.__process_general_syntax_rule__(words, r, i)
                words = sent.split(' ')        
        for k in reqtype_ignore_rules[t].keys():
            sent = sent.replace(k, reqtype_ignore_rules[t][k])        
        sent = re.sub(r'\s+\'s', '\'s', sent)
        

        # TODO: experimental statement to replace all the commas to 'and' or 'or'
        if r := re.search(r"only\s+contains((\s*\w+\s*,)+)+,*\s*(and|or)\s*\w+\s*\.?", sent):
            g = list(r.groups())
            conj = g[-1]
            sent = sent.replace(g[0], g[0].replace(',', conj))
        sent = re.sub(r'\s+\'\s,\s\'\s+', '\',\'', sent)

        # TODO: experimental replacing all text number to integer
        index = []
        words = sent.split(' ')
        for i,w in enumerate(words):
            if __check_is_num_word__(w):
                index.append(i)
        if index:
            for i in index:
                words[i] = str(__convert_num_word__(words[i]))
        sent = ' '.join(words)
        return sent