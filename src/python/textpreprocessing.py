import os
import sys
import glob
import yaml
import re
from typing import Dict, List, Tuple
from preprocess.engine import runengine

modelspecspath = './specs/models'
# Default SI path - can be overridden via command-line argument
sispecspath = './specs/si/common/typed_si.yml'
CONDITION_PATTERNS_PATH = os.path.join('.', 'rules', 'condition_patterns.yml')


def load_condition_patterns(filepath=None):
    """Load condition splitting patterns from a YAML file.
    
    Returns a dict keyed by processor name, each containing a list of
    pattern dicts. Falls back to empty dict if file not found.
    """
    path = filepath or CONDITION_PATTERNS_PATH
    if not os.path.exists(path):
        return {}
    with open(path, 'r') as fp:
        data = yaml.safe_load(fp)
    return data or {}


_condition_patterns = load_condition_patterns()


def detect_backend(si_paths):
    """Detect backend from SI library paths.
    
    Examines the directory structure of SI library paths to determine
    which backend they belong to:
        - Paths containing 'jml'  -> 'jml'
        - Paths containing 'dafny' -> 'dafny'
    
    Args:
        si_paths: List of SI library file paths
    
    Returns:
        'jml' or 'dafny'
    """
    for p in si_paths:
        if 'dafny' in p.lower():
            return 'dafny'
    return 'jml'


def extract_conditions(filecontent, backend):
    """Extract raw NL conditions from source file content.
    
    Uses backend-specific patterns from condition_patterns.yml to extract
    natural language conditions from source code comments.
    
    Args:
        filecontent: Source file content as string
        backend: 'jml' or 'dafny'
    
    Returns:
        Dict with 'requires' and 'ensures' lists of raw NL strings
    """
    patterns = _condition_patterns.get('extraction_patterns', {})
    backend_patterns = patterns.get(backend, {})
    pattern = backend_patterns.get('pattern', '')
    
    conditions = {'requires': [], 'ensures': [], 'assert': []}
    
    if not pattern:
        return conditions
    
    for m in re.findall(pattern, filecontent):
        if len(m) >= 2:
            cond_type, nl_text = m[0], m[1]
            if cond_type in conditions:
                conditions[cond_type].append(nl_text)
    
    return conditions


def apply_condition_splitting(conditions):
    """Apply condition splitting transformations to separate complex conditions.
    
    These transformations split compound conditions into simpler atomic
    conditions suitable for NLP processing.
    
    Args:
        conditions: Dict with 'requires' and 'ensures' lists
    
    Returns:
        Transformed conditions dict
    """
    # Normalize None values to empty lists
    for key in list(conditions.keys()):
        if conditions[key] is None:
            conditions[key] = []
    
    conditions = __process_parameter_type_distrition(conditions)
    conditions = __process_either_or__(conditions)
    conditions = __process_pronoun__(conditions)
    conditions = __process_conditional_sentence_distribution(conditions)
    conditions = __process_false_otherwise(conditions)
    conditions = __process_and_false_clause(conditions)
    conditions = __process_compound_subject(conditions)
    conditions = __process_object_clause__(conditions)
    conditions = __process_specified_type_checking_sent__(conditions)
    conditions = __process_at_most_elements(conditions)
    conditions = __process_compound_subject_ultimate__(conditions)
    return conditions


def _get_specs():
    models = {}
    for f in glob.glob(modelspecspath + '/*'):
        name = f.split('/')[-1]
        with open(f) as fp:
            models[name] = fp.read().strip()
    si = {}
    for f in glob.glob(sispecspath + '/*'):
        with open(f) as fp:
            si = yaml.full_load(fp)
    return {'models': models, 'si': si}


def __process_parameter_type_distrition(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('parameter_type_distrition', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):
                    # if pattern['forbidden'] in sent:
                    #     results[t].append(sent)
                    # else:
                    target = r.group(0)
                    subject= r.group(1)
                    parameter_type = r.group(2)
                    sequence = r.group(3).replace('and', '').replace(' ', '')
                    predicate = r.groups()[-1]
                    head = '%s of %s ' % (subject, parameter_type)
                    for param in sequence.split(','):
                        results[t].append(head + param + ' ' + predicate)     
                    processed = True
            if not processed:
                results[t].append(sent)                   
                                
    # [print(r) for r in results['ensures']]
    return results


def __equal_respectively__(r, sent) -> List[str]:
    results = []
    target = r.group(0)
    parameter_type = r.group(1)
    subjectA = r.group(2)
    resultA = r.group(4)
    subjectB = r.group(3)
    resultB = r.group(5)
    predicate = r.group(6)
    # print('hit')
    template = 'If the %s parameter %s is equal to %s and the %s parameter %s is equal to %s %s'
    results.append(template % (parameter_type, subjectA, resultA, parameter_type, subjectB, resultB, predicate))
    # results.append(template % (parameter_type, subjectA, resultA, predicate))     
    # results.append(template % (parameter_type, subjectB, resultB, predicate))
    # print(results)     
    return results

def __equal_distributedly__(r, sent) -> List[str]:
    results = []
    target = r.group(0)
    parameter_type = r.group(1)
    subjectA = r.group(2)
    subjectB = r.group(3)
    result = r.group(4)
    predicate = r.group(5)
    # template = 'If the %s parameter %s is equal to %s %s'
    # print('hit')
    template = 'If the %s parameter %s is equal to %s and the %s parameter is equal to %s %s'
    results.append(template % (parameter_type, subjectA, result, parameter_type, subjectB, result, predicate))
    # results.append(template % (parameter_type, subjectA, result, predicate))     
    # results.append(template % (parameter_type, subjectB, result, predicate))
    return results

def __consist_of_distributed__(r, sent) -> List[str]:
    results = []
    template = '%s parameter %s %s %s'
    type_str = r.group(1)
    subjectA = r.group(2)
    subjectB = r.group(3)
    verb = r.group(4)
    predicate = r.group(5)
    if predicate[-1] == '.':
        predicate = predicate[:-1]
    verb = verb.replace('consist', 'consists')
    # print('hit')
    # results.append('The ' + template % (type_str, subjectA, verb, predicate))
    # results.append('The ' + template % (type_str, subjectB, verb, predicate))
    results.append('The ' + template % (type_str, subjectA, verb, predicate) + ' and the ' + template % (type_str, subjectB, verb, predicate))
    return results

def __object_distributed__(r, sent, template) -> List[str]:
    results = []
    type_str = r.group(1)
    subjectA = r.group(2)
    subjectB = r.group(3)
    results.append(template % (type_str, subjectA, type_str, subjectB))
    return results

def __object_distributed_general__(r, sent, template) -> List[str]:
    results = []
    type_str = r.group(1)
    subjectA = r.group(2)
    subjectB = r.group(3)
    predicate = r.group(4)
    results.append(template % (type_str, subjectA, predicate, type_str, subjectB, predicate))
    return results


_OBJECT_HANDLERS = {
    'object_distributed': __object_distributed__,
    'object_distributed_general': __object_distributed_general__,
}

def __process_object_clause__(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('object_clause', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):
                    handler = _OBJECT_HANDLERS.get(pattern['handler'], __object_distributed__)
                    results[t] += handler(r, sent, pattern['template'])                    
                    processed = True
                    break
            if not processed:
                results[t].append(sent)
                                
    # [print(r) for r in results['ensures']]
    return results

def __equal_correspondingly__(r, sent) -> List[str]:
    results = []
    target = r.group(0)
    parameter_type = r.group(1)
    subjectA = r.group(2)
    subjectB = r.group(3)
    resultA = r.group(4)
    resultB = r.group(5)
    predicate = r.group(6)
    # template = 'If the %s parameter %s is equal to %s %s'
    template = 'If the %s parameter %s is equal to %s and the %s parameter %s is equal to %s %s'
    results.append(template % (parameter_type, subjectA, resultA, parameter_type, subjectB, resultB, predicate))
    return results

_SUBJECT_HANDLERS = {
    'equal_respectively': __equal_respectively__,
    'equal_distributedly': __equal_distributedly__,
    'consist_of_distributed': __consist_of_distributed__,
    'equal_correspondingly': __equal_correspondingly__,
}

def __process_compound_subject(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('compound_subject', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):
                    handler = _SUBJECT_HANDLERS.get(pattern['handler'], __equal_respectively__)
                    results[t] += handler(r, sent)                    
                    processed = True
                    break
            if not processed:
                results[t].append(sent)                   
                                
    # [print(r) for r in results['ensures']]
    return results

def __process_either_or__(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('either_or', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):                    
                    # print(r.group(0))
                    parameter_type = r.group(1)
                    param = r.group(2)
                    format = 'the %s parameter %s is %s or the %s parameter %s is %s' % (parameter_type, param, r.group(3), parameter_type, param, r.group(4))
                    format = format.replace('is has', 'has')
                    results[t].append(sent.replace(r.group(0), format))
                    processed = True
            if not processed:
                results[t].append(sent)     
    return results  

# Experimental: inferring the noun of a posessive pronoun
def __process_pronoun__(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('pronoun', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):                    
                    # print(r.group(0))
                    parameter_type = r.group(1)
                    param = 'the %s parameter %s' % (parameter_type, r.group(2))
                    format = 'the %s is %s and the %s of the %s are %s' % (param, r.group(3), r.group(4), param, r.group(5))
                    format = format.replace('is has', 'has')
                    results[t].append(sent.replace(r.group(0), format))
                    processed = True
            if not processed:
                results[t].append(sent)     
    return results  


def __process_conditional_sentence_distribution(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('conditional_sentence_distribution', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):
                    # print(r.group(0))
                    _type = ""
                    if pattern['type'] == 'string':
                        # reserve for future use. other types can be applied
                        _type = '_type_string_'                    
                        format = 'If the %s parameter %s is equal to the %s' % (r.group(1), r.group(2), _type)
                    else:
                        format = 'If the %s parameter %s is equal to ' % (r.group(1), r.group(2))
                    for s in r.group(3).replace(' ', '').split(','):
                        if _type:
                            results[t].append(sent.replace(r.group(0), format + ' ' + s))
                        else:
                            results[t].append(sent.replace(r.group(0), format + ' ' + s))
                        # print(sent.replace(r.group(0), format + ' ' + s))
                    # results[t].append(sent.replace(r.group(0), format))
                    processed = True
            if not processed:
                results[t].append(sent)     
    return results


def __process_false_otherwise(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('false_otherwise', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):                    
                    # print(r.group(0))
                    _sent = sent.replace(r.group(0), '')
                    results[t].append(_sent)
                    # if 'is' in sent:
                    #     results[t].append(_sent.replace('is', 'is not'))
                    # elif 'are' in sent:
                    #     results[t].append(_sent.replace('are', 'are not'))
                    processed = True
            if not processed:
                results[t].append(sent)     
    return results  


def __process_and_false_clause(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('and_false_clause', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):   
                    target = 'The boolean result is false %s' % r.group(1)                
                    _sent = sent.replace(r.group(0), '.')
                    results[t].append(_sent)
                    results[t].append(target)
                    processed = True
            if not processed:
                results[t].append(sent)     
    return results

def __process_specified_type_checking_sent__(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('specified_type_checking', [])
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):   
                    # print(r.group(0))
                    template = pattern['template']
                    checking_symbol = None
                    if 'integer' in r.group(3) and 'integer array' == r.group(1):
                        checking_symbol = 'checking_integer'
                    results[t].append(template % (r.group(1), r.group(2), checking_symbol))
                    processed = True
            if not processed:
                results[t].append(sent)    
    return results

def __process_redundant_type_clause(conditions):
    patterns = _condition_patterns.get('redundant_type_clause', [])
    results = {'ensures': [], 'requires': []}
    for t in conditions:
        for sent in conditions[t]:                   
            processed = False
            for pattern in patterns:
                if r := re.findall(pattern['pattern'], sent):   
                    _sent = sent
                    for _r in r:
                        target = 'the type_character_ %s' % _r              
                        _sent = _sent.replace('the %s character' % _r, target, 1)
                    results[t].append(_sent)
                    processed = True
            if not processed:
                results[t].append(sent)     
    return results

def __process_at_most_elements(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('at_most_elements', [])
    for t in conditions:
        for sent in conditions[t]:          
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):   
                    type_str = r.group(1)
                    results[t].append(pattern['template'] % (type_str, r.group(2), type_str, str(r.group(3)))
                    )
                    processed = True
            if not processed:
                results[t].append(sent)    
    return results

def __process_compound_subject_ultimate__(conditions):
    results = {'ensures': [], 'requires': []}
    patterns = _condition_patterns.get('compound_subject_ultimate', [])
    for t in conditions:
        for sent in conditions[t]:          
            processed = False
            for pattern in patterns:
                if r := re.search(pattern['pattern'], sent):  
                    if 'constraint' in pattern and pattern['constraint'] in sent:
                        continue 
                    type_str = r.group(1)
                    clause = r.group(4).replace('are', 'is')
                    if clause[-1] == '.':
                        clause = clause[:-1]
                    results[t].append(pattern['template'] % (type_str, r.group(2), clause , type_str, r.group(3), clause)
                    )
                    processed = True
            if not processed:
                results[t].append(sent)    
    return results
    
def main(filecontent: str, si_path=None) -> Tuple[Dict[str, List[str]], List[Dict]]:
    """Process NL conditions: extract, split, and preprocess.
    
    This is the full pipeline entry point. It extracts raw conditions from
    source content, applies condition splitting transformations, and runs
    the preprocessing engine on each condition.
    
    Args:
        filecontent: Source file content
        si_path: Optional SI library path for the preprocessing engine
    
    Returns:
        Tuple of (results dict, dynamic SI entries dict)
    """
    models, si = _get_specs()
    # Auto-detect backend from SI path, default to jml
    backend = detect_backend([si_path] if si_path else [])
    conditions = extract_conditions(filecontent, backend)
    
    results = { 'ensures': [], 'requires': []}
    sis = {}
    # Apply condition splitting transformations
    conditions = apply_condition_splitting(conditions)
    #######
    for t in conditions:
        clist = conditions[t]
        for i, c in enumerate(clist):
            s, si = runengine(c, t, si_path=si_path)
            results[t].append(s)            
            if t == 'ensures':
                ditype = 'post'
            elif t == 'assert':
                ditype = 'assert'
            else:
                ditype = 'pre'
            sis['%s.%s' % (ditype, str(i))] = list(si.values())
    return results, sis

class NoAliasDumper(yaml.SafeDumper):
    def ignore_aliases(self, data):
        return True


def do_extract(filepath, backend, output_dir):
    """Extract raw NL conditions from a source file and write conditions.yml.
    
    Args:
        filepath: Path to the source file
        backend: 'jml' or 'dafny'
        output_dir: Directory to write conditions.yml
    """
    with open(filepath) as fp:
        filecontent = fp.read()
    
    if not filecontent or not filecontent.strip():
        sys.exit(1)
    
    conditions = extract_conditions(filecontent.strip(), backend)
    
    os.makedirs(output_dir, exist_ok=True)
    with open(os.path.join(output_dir, 'conditions.yml'), 'w') as fp:
        yaml.dump(conditions, fp, sort_keys=False, allow_unicode=True, width=float("inf"))


def do_preprocess(conditions_path, si_path, output_dir):
    """Preprocess extracted conditions and write output files.
    
    Reads conditions.yml, applies condition splitting and the preprocessing
    engine to each condition, and writes:
        - preprocessed.<key>.txt  for each condition
        - dynamic_si.<key>.yml    for each condition (if SI entries exist)
    
    Args:
        conditions_path: Path to conditions.yml
        si_path: SI library path for the preprocessing engine
        output_dir: Directory to write output files
    """
    global sispecspath
    
    with open(conditions_path) as fp:
        conditions = yaml.safe_load(fp)
    
    if not conditions:
        return
    
    # Normalize None values to empty lists (YAML parses empty sections as None)
    for key in list(conditions.keys()):
        if conditions[key] is None:
            conditions[key] = []
    
    # Update global SI path for backward compatibility with _get_specs()
    if si_path:
        sispecspath = si_path
    
    os.makedirs(output_dir, exist_ok=True)
    
    results = {'ensures': [], 'requires': []}
    sis = {}
    
    # Apply condition splitting transformations
    conditions = apply_condition_splitting(conditions)
    
    for t in conditions:
        clist = conditions[t]
        for i, c in enumerate(clist):
            s, si_entries = runengine(c, t, si_path=si_path)
            results[t].append(s)
            if t == 'ensures':
                ditype = 'post'
            elif t == 'assert':
                ditype = 'assert'
            else:
                ditype = 'pre'
            key = '%s.%s' % (ditype, str(i))
            sis[key] = list(si_entries.values())
            
            # Write preprocessed text
            preprocessed_path = os.path.join(output_dir, 'preprocessed.%s.txt' % key)
            with open(preprocessed_path, 'w') as fp:
                fp.write(s + "\n")
            
            # Write dynamic SI if non-empty
            if sis[key]:
                si_file_path = os.path.join(output_dir, 'dynamic_si.%s.yml' % key)
                with open(si_file_path, 'w') as fp:
                    yaml.dump(sis[key], fp, sort_keys=False, allow_unicode=True,
                              Dumper=NoAliasDumper)


if __name__ == "__main__":
    import argparse
    
    parser = argparse.ArgumentParser(
        description='Text preprocessing for NL specification conditions. '
                    'Serves all backends depending on input SI libraries.')
    parser.add_argument('input_file', help='Input source file')
    parser.add_argument('--mode', choices=['extract', 'preprocess', 'full'],
                        default='full',
                        help='Operation mode: extract (raw NL only), '
                             'preprocess (from conditions.yml), '
                             'full (extract + preprocess, default)')
    parser.add_argument('--si', default=None,
                        help='Comma-separated SI library paths. '
                             'Backend is auto-detected from these paths.')
    parser.add_argument('--output-dir', default=None,
                        help='Output directory (default: <input_folder>/tmp)')
    
    args = parser.parse_args()
    
    # Parse SI library paths
    si_paths = [p.strip() for p in args.si.split(',')] if args.si else []
    # Pass the full comma-separated SI path list so the engine can load
    # both common and backend-specific SI files
    si_path = ','.join(si_paths) if si_paths else None
    
    # Determine output directory
    folder = os.path.dirname(os.path.abspath(args.input_file))
    output_dir = args.output_dir or os.path.join(folder, 'tmp')
    
    # Detect backend from SI library paths
    backend = detect_backend(si_paths)
    
    if args.mode == 'extract':
        # Extract raw NL conditions only
        do_extract(args.input_file, backend, output_dir)
    
    elif args.mode == 'preprocess':
        # Preprocess from existing conditions.yml
        conditions_path = os.path.join(output_dir, 'conditions.yml')
        if not os.path.exists(conditions_path):
            print("Error: conditions.yml not found in %s" % output_dir, file=sys.stderr)
            sys.exit(1)
        do_preprocess(conditions_path, si_path, output_dir)
    
    else:
        # Full pipeline: extract + preprocess (backward compatible)
        with open(args.input_file) as fp:
            filecontent = fp.read()
        
        if not filecontent or not filecontent.strip():
            sys.exit(1)
        
        conditions, sis = main(filecontent.strip(), si_path=si_path)
        
        os.makedirs(output_dir, exist_ok=True)
        
        with open(os.path.join(output_dir, 'conditions.yml'), 'w') as fp:
            yaml.dump(conditions, fp, sort_keys=False, allow_unicode=True,
                      width=float("inf"))
        
        for k in sis.keys():
            si_file_path = os.path.join(output_dir, 'dynamic_si.%s.yml' % k)
            if not sis[k]:
                if os.path.exists(si_file_path):
                    os.remove(si_file_path)
            else:
                with open(si_file_path, 'w') as fp:
                    yaml.dump(sis[k], fp, sort_keys=False, allow_unicode=True,
                              Dumper=NoAliasDumper)