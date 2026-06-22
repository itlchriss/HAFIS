"""
Post-MR repair for arithmetic predicates.

Repairs MR-level faults where:
1. "+" appears with wrong POS (VBD/VBP/NNP instead of IN) in arithmetic context
2. "_divisible{JJ}" has incomplete arguments (only Subj, missing the divisor)
3. Dynamic compound expressions like "_i+1{NN}" need SI entries

This module processes MR strings (single-line lambda calculus format)
and returns repaired MR strings. It can also generate dynamic SI entries.

Usage:
    python mr_arithmetic_repair.py <mr_file> [--dynamic-si <output_yml>]
"""

import re
import sys
import os
import yaml


def _find_matching_paren(s, start):
    """Find the index of the closing paren matching the open paren at `start`."""
    depth = 0
    for i in range(start, len(s)):
        if s[i] == '(':
            depth += 1
        elif s[i] == ')':
            depth -= 1
            if depth == 0:
                return i
    return -1


def _extract_quantifier_body(s, quant_start):
    """Given a position of 'exists xNN.(' or 'all xNN.(', extract the body and bounds.
    Returns (variable, body_str, end_pos) or None.
    """
    m = re.match(r'(?:exists|all)\s+(\w+)\.\(', s[quant_start:])
    if not m:
        return None
    var = m.group(1)
    paren_start = quant_start + m.end() - 1  # position of '('
    paren_end = _find_matching_paren(s, paren_start)
    if paren_end == -1:
        return None
    body = s[paren_start + 1:paren_end]
    return var, body, paren_end


def repair_plus_vbd_vbp(mr):
    """Repair _+{VBD} and _+{VBP} in arithmetic context.
    
    Pattern: _+{VBD/VBP}(eN) & (Subj(eN) = xM) & _num{CD}(eN)
    Target:  _plus{IN}(eN, xK) & (Subj(eN) = xM) & _num{CD}(xK)
    where xK is a fresh individual variable for the number.
    
    The original pattern has the number sharing the event variable (eN).
    We restructure to give the number its own individual variable (xK)
    so that plus{IN} can reference it as its second argument.
    
    Also handles: _+{VBD/VBP}(eN) & (Subj(eN) = xM) & AccI(eN, ...)
    where the number is in an AccI complement.
    """
    # Pattern 1: Simple case - _+{VBD}(eN) & (Subj(eN) = xM) & _num{CD}(eN)
    # The number predicate shares the event variable
    pattern1 = re.compile(
        r'_\+\{(VBD|VBP)\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\)\s*&\s*_(\d+)\{CD\}\(\2\)'
    )
    
    def replace1(m):
        event = m.group(2)
        subj_var = m.group(3)
        num_val = m.group(4)
        # Generate a fresh individual variable for the number
        all_vars = set(re.findall(r'x(\d+)', mr))
        next_id = max(int(v) for v in all_vars) + 1 if all_vars else 99
        num_var = 'x%02d' % next_id
        # Convert to plus{IN} with the number as a separate individual variable
        return '_plus{IN}(%s, %s) & (Subj(%s) = %s) & _%s{CD}(%s)' % (
            event, num_var, event, subj_var, num_val, num_var)
    
    mr = pattern1.sub(replace1, mr)
    
    # Pattern 2: With AccI - _+{VBD}(eN) & (Subj(eN) = xM) & AccI(eN, exists xK.(...))
    # The number is inside an AccI complement
    pattern2 = re.compile(
        r'_\+\{(VBD|VBP)\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\)\s*&\s*AccI\(\2,'
    )
    
    def replace2(m):
        event = m.group(2)
        subj_var = m.group(3)
        # Find the variable inside AccI
        after_acci = mr[m.end():]
        num_match = re.match(r'\s*exists\s+(\w+)\.', after_acci)
        if num_match:
            num_var = num_match.group(1)
            return '_plus{IN}(%s, %s) & (Subj(%s) = %s) & AccI(%s,' % (
                event, num_var, event, subj_var, event)
        return m.group(0)  # No change if can't determine
    
    mr = pattern2.sub(replace2, mr)
    
    # Pattern 3: With Acc (variable operand) - _+{VBD/VBP}(eN) & (Subj(eN) = xM) & (Acc(eN) = xK)
    # The Acc variable is the second operand (e.g., "a + b" where both are variables)
    pattern3 = re.compile(
        r'_\+\{(VBD|VBP)\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\)\s*&\s*\(Acc\(\2\)\s*=\s*(\w+)\)'
    )
    
    def replace3(m):
        event = m.group(2)
        subj_var = m.group(3)
        acc_var = m.group(4)
        return '_plus{IN}(%s, %s) & (Subj(%s) = %s)' % (event, acc_var, event, subj_var)
    
    mr = pattern3.sub(replace3, mr)
    
    # Pattern 4: Standalone _+{VBD/VBP} with just Subj (fallback, no operand found)
    pattern4 = re.compile(
        r'_\+\{(VBD|VBP)\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\)'
    )
    
    def replace4(m):
        event = m.group(2)
        subj_var = m.group(3)
        return '_plus{IN}(%s) & (Subj(%s) = %s)' % (event, event, subj_var)
    
    mr = pattern4.sub(replace4, mr)
    
    return mr


def _find_num_var_for_value(mr, pos, num_val):
    """Find the variable bound to _num{CD}(var) in a surrounding exists quantifier.
    Searches backwards from `pos` for 'exists xNN.(_num{CD}(xNN))'.
    """
    # Look for "exists xNN.(_num{CD}(xNN)" pattern near the match position
    search_start = max(0, pos - 200)
    context = mr[search_start:pos + 100]
    pattern = re.compile(r'exists\s+(\w+)\.\(_' + re.escape(num_val) + r'\{CD\}\(\1\)\)')
    m = pattern.search(context)
    if m:
        return m.group(1)
    # Try without the closing paren (might be part of a larger conjunction)
    pattern2 = re.compile(r'exists\s+(\w+)\.\(_' + re.escape(num_val) + r'\{CD\}\(\1\)')
    m = pattern2.search(context)
    if m:
        return m.group(1)
    return None


def _make_compound_si(compound, dynamic_si):
    """Create a dynamic SI entry for a word+num compound term."""
    if dynamic_si is not None and compound not in dynamic_si:
        dynamic_si[compound] = {
            'term': compound,
            'syntax': ['NN'],
            'arguments': [{'symbol': '*'}],
            'synthesised_datatype': {'primitive_type': 'integer', 'reference_type': 'undefined'},
            'interpretation': '(%s) + (%s)' % (compound.rsplit('_plus_', 1)[0], compound.rsplit('_plus_', 1)[1]),
        }


def repair_plus_nnp(mr, dynamic_si=None):
    """Repair _+{NNP} in arithmetic context.

    Handles three orderings of _+{NNP}, _num{CD}, and _word{POS} sharing
    the same variable.  All collapse to _word_plus_num{NN}(xN).
    """
    # Patterns: (a) _+{NNP}(xN) & _num{CD}(xN) & _word{POS}(xN)
    #           (b) _word{POS}(xN) & _+{NNP}(xN) & _num{CD}(xN)
    #           (c) _+{NNP}(xN) & _word{POS}(xN) & _num{CD}(xN)
    patterns = [
        re.compile(r'_\+\{NNP\}\((\w+)\)\s*&\s*_(\d+)\{CD\}\(\1\)\s*&\s*_(\w+)\{(\w+)\}\(\1\)'),
        re.compile(r'_(\w+)\{(\w+)\}\((\w+)\)\s*&\s*_\+\{NNP\}\(\3\)\s*&\s*_(\d+)\{CD\}\(\3\)'),
        re.compile(r'_\+\{NNP\}\((\w+)\)\s*&\s*_(\w+)\{(\w+)\}\(\1\)\s*&\s*_(\d+)\{CD\}\(\1\)'),
    ]

    def _extract(m, pat_idx):
        """Return (var, num_val, word) based on which pattern matched."""
        if pat_idx == 0:
            return m.group(1), m.group(2), m.group(3)
        elif pat_idx == 1:
            return m.group(3), m.group(4), m.group(1)
        else:
            return m.group(1), m.group(4), m.group(2)

    for idx, pat in enumerate(patterns):
        def replace(m, _idx=idx):
            var, num_val, word = _extract(m, _idx)
            compound = '%s_plus_%s' % (word, num_val)
            _make_compound_si(compound, dynamic_si)
            return '_%s{NN}(%s)' % (compound, var)
        mr = pat.sub(replace, mr)

    return mr


def repair_divisible(mr):
    """Repair _divisible{JJ} with incomplete arguments.
    
    Pattern: _divisible{JJ}(eN) & (Subj(eN) = xM) & exists xK.(_num{CD}(xK) & (Subj(eN) = xK))
    Target: _divisible{JJ}(eN, xK) & (Subj(eN) = xM)
    
    The divisor number is connected via a separate exists clause sharing the Subj.
    We restructure to pass the number variable as the second argument.
    """
    # Pattern 1: exists xK.(_num{CD}(xK) & (Subj(eN) = xK))
    # The number variable is bound by exists and shares Subj with the divisible event
    pattern1 = re.compile(
        r'(_divisible\{JJ\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\))\s*&\s*'
        r'exists\s+(\w+)\.\(_(\d+)\{CD\}\(\4\)\s*&\s*\(Subj\(\2\)\s*=\s*\4\)\)'
    )
    
    def replace1(m):
        event = m.group(2)
        subj_var = m.group(3)
        num_var = m.group(4)
        return '_divisible{JJ}(%s, %s) & (Subj(%s) = %s)' % (event, num_var, event, subj_var)
    
    mr = pattern1.sub(replace1, mr)
    
    # Pattern 2: exists xK.(_num1{CD}(xK) & _num2{CD}(xK) & (Subj(eN) = xK))
    # Multiple numbers share the same variable (e.g., "divisible by both 3 and 5")
    # Also handles extra predicates like _result{NN}(xK) in the exists clause
    pattern2 = re.compile(
        r'(_divisible\{JJ\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\))\s*&\s*'
        r'exists\s+(\w+)\.\((.+?)\s*&\s*\(Subj\(\2\)\s*=\s*\4\)\)'
    )
    
    def replace2(m):
        event = m.group(2)
        subj_var = m.group(3)
        num_var = m.group(4)
        inner = m.group(5)
        # Check if inner contains number predicates
        nums = re.findall(r'_(\d+)\{CD\}', inner)
        if nums:
            # Has numbers: restructure to pass num_var as 2nd arg to divisible
            # Keep the full inner content (including non-number predicates)
            return '_divisible{JJ}(%s, %s) & (Subj(%s) = %s) & exists %s.(%s & (Subj(%s) = %s))' % (
                event, num_var, event, subj_var, num_var, inner, event, num_var)
        else:
            return m.group(0)  # No change if no numbers found
    
    mr = pattern2.sub(replace2, mr)
    
    # Pattern 3: Simpler - _divisible{JJ}(eN) & (Subj(eN) = xM) & _num{CD}(xK) & (Subj(eN) = xK)
    # Without exists quantifier on the number
    pattern3 = re.compile(
        r'(_divisible\{JJ\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\))\s*&\s*'
        r'_(\d+)\{CD\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*\5\)'
    )
    
    def replace3(m):
        event = m.group(2)
        subj_var = m.group(3)
        num_var = m.group(5)
        return '_divisible{JJ}(%s, %s) & (Subj(%s) = %s)' % (event, num_var, event, subj_var)
    
    mr = pattern3.sub(replace3, mr)
    
    # Pattern 4: Negated divisible - -exists eN.(_divisible{JJ}(eN) & ...)
    # Same patterns but preceded by negation
    # The patterns above should handle this since we're matching the inner structure
    
    return mr


def repair_compound_plus(mr, dynamic_si=None):
    """Handle dynamic compound expressions like _i+1{NN}.
    
    Pattern: _var+N{NN/CD}(xN) where var+N is a compound like "i+1"
    These need dynamic SI entries for the arithmetic interpretation.
    
    If dynamic_si dict is provided, entries are added to it.
    """
    # Find all _var+num{POS}(xN) patterns
    compound_pattern = re.compile(r'_(\w+)\+(\d+)\{(NN|CD|NNP)\}\((\w+)\)')
    
    for m in compound_pattern.finditer(mr):
        var_name = m.group(1)
        num_val = m.group(2)
        pos = m.group(3)
        variable = m.group(4)
        
        # Create a dynamic SI entry
        symbol = '%s_plus_%s' % (var_name, num_val)
        if dynamic_si is not None:
            dynamic_si[symbol] = {
                'term': symbol,
                'syntax': [pos],
                'arguments': [{'symbol': '*'}],
                'synthesised_datatype': {'primitive_type': 'integer', 'reference_type': 'undefined'},
                'interpretation': '(%s) + (%s)' % (var_name, num_val),
            }
    
    return mr


def write_dynamic_si(dynamic_si, output_path):
    """Write dynamic SI entries to a YAML file."""
    if not dynamic_si:
        return
    
    entries = []
    for symbol, data in dynamic_si.items():
        entry = {
            'term': data['term'],
            'syntax': data['syntax'],
            'arguments': data['arguments'],
            'synthesised_datatype': data['synthesised_datatype'],
            'interpretation': data['interpretation'],
        }
        entries.append(entry)
    
    with open(output_path, 'w') as f:
        yaml.dump(entries, f, default_flow_style=False)


def repair_type_predicate_jj(mr):
    """Repair type predicates incorrectly tagged as JJ instead of NN.
    
    The ccg2lambda tagger sometimes assigns JJ to noun compound type predicates
    like type_integer_array_. This is a tagger error - these are noun compounds
    and should be NN.
    
    The JJ tag creates an event-based structure:
        _type_integer_array_{JJ}(e03) & (Subj(e03) = x02)
    
    The correct NN structure uses entity directly:
        _type_integer_array_{NN}(x02)
    
    This repair collapses the event structure into an entity predicate.
    """
    # Pattern: _type_XXX_{JJ}(eN) & (Subj(eN) = xM)
    # Replace with: _type_XXX_{NN}(xM)
    # Note: type predicate terms end with trailing underscore (e.g., type_integer_array_)
    pattern = re.compile(
        r'_(type_[a-zA-Z_]+_)\{JJ\}\((\w+)\)\s*&\s*\(Subj\(\2\)\s*=\s*(\w+)\)'
    )
    
    def replace(m):
        type_name = m.group(1)
        entity_var = m.group(3)
        return '_%s{NN}(%s)' % (type_name, entity_var)
    
    mr = pattern.sub(replace, mr)
    return mr


def repair_pos_tagger_errors(mr):
    """Repair known POS tagger errors in MR predicates.
    
    The ccg2lambda tagger sometimes assigns wrong POS tags to specific
    predicate terms. This repairs known cases:
    - first_element{JJ} → first_element{NN}
    - second_element{JJ} → second_element{NN}
    - value{NN} → value{NNS} (in array element access context)
    - index{NNS} → index{NN}
    """
    # Known POS corrections: (term, wrong_pos, correct_pos)
    corrections = [
        ('first_element', 'JJ', 'NN'),
        ('second_element', 'JJ', 'NN'),
        ('value', 'NN', 'NNS'),
        ('index', 'NNS', 'NN'),
    ]
    for term, wrong_pos, correct_pos in corrections:
        pattern = re.compile(r'_' + re.escape(term) + r'\{' + wrong_pos + r'\}')
        mr = pattern.sub('_%s{%s}' % (term, correct_pos), mr)
    return mr


def repair_param_si(mr, dynamic_si=None):
    """Generate dynamic SI entries for param_<name>_ predicates found in MR.
    
    Parameter references like _param_numbers_{NN}(x05) need SI entries.
    The interpretation is the bare parameter name (e.g., 'numbers', 'target').
    The type is inferred from co-occurring type predicates sharing the same
    variable OR connected via Rel() relations.
    
    Also handles _index{NN/NNS} predicates and _arr_<X>{NN} predicates.
    """
    if dynamic_si is None:
        return mr
    
    # Build a map of variable -> type from type predicates
    var_types = {}
    type_pattern = re.compile(r'_(type_\w+_)(?:\{(\w+)\})?\((\w+)\)')
    for m in type_pattern.finditer(mr):
        type_name = m.group(1)
        variable = m.group(3)
        _assign_type(var_types, variable, type_name)
    
    # Follow Rel() connections to propagate types
    # Rel(xA, xB) means xA depends on xB - if xB has a type, xA inherits it
    rel_pattern = re.compile(r'Rel\((\w+),(\w+)\)')
    for m in rel_pattern.finditer(mr):
        var_a, var_b = m.group(1), m.group(2)
        if var_b in var_types and var_a not in var_types:
            var_types[var_a] = dict(var_types[var_b])
        elif var_a in var_types and var_b not in var_types:
            var_types[var_b] = dict(var_types[var_a])
    
    # Find param_<name>_ predicates
    param_pattern = re.compile(r'_param_(\w+)_\{(\w+)\}\((\w+)\)')
    for m in param_pattern.finditer(mr):
        param_name = m.group(1)
        pos = m.group(2)
        variable = m.group(3)
        term = 'param_%s_' % param_name
        if term not in dynamic_si:
            type_info = var_types.get(variable, {'primitive_type': 'integer', 'reference_type': 'undefined'})
            dynamic_si[term] = {
                'term': term,
                'syntax': [pos],
                'arguments': [{'symbol': '*'}],
                'synthesised_datatype': [type_info],
                'interpretation': param_name,
            }
    
    # Find _index{NN/NNS} predicates
    index_pattern = re.compile(r'_index\{(\w+)\}\((\w+)\)')
    for m in index_pattern.finditer(mr):
        pos = m.group(1)
        if 'index' not in dynamic_si:
            dynamic_si['index'] = {
                'term': 'index',
                'syntax': [pos],
                'arguments': [{'symbol': '*'}],
                'synthesised_datatype': [{'primitive_type': 'integer', 'reference_type': 'undefined'}],
                'interpretation': '',
            }
    
    # Find _arr_<X>{NN} predicates (array literal placeholders)
    arr_pattern = re.compile(r'_(arr_\w+)\{(\w+)\}\((\w+)\)')
    for m in arr_pattern.finditer(mr):
        arr_key = m.group(1)
        pos = m.group(2)
        variable = m.group(3)
        if arr_key not in dynamic_si:
            # Array literals are integer arrays
            # The interpretation (CSV values) will be filled by the pipeline's
            # expression store when running the full mearc pipeline.
            # Here we provide the type information so the compiler can proceed.
            type_info = var_types.get(variable, {'primitive_type': 'integer', 'reference_type': 'array'})
            dynamic_si[arr_key] = {
                'term': arr_key,
                'syntax': [pos],
                'arguments': [{'symbol': '*'}],
                'synthesised_datatype': [type_info],
                'interpretation': '',  # Empty - pipeline fills from expression store
            }
    
    return mr


def _assign_type(var_types, variable, type_name):
    """Assign type information to a variable based on a type predicate name."""
    type_map = [
        ('integer_array',      'integer',  'array'),
        ('boolean_array',      'boolean',  'array'),
        ('character_array',    'character', 'array'),
        ('string_array',       'string',   'string_array'),
        ('non_negative_integer', 'integer', 'undefined'),
        ('integer',            'integer',  'undefined'),
        ('boolean',            'boolean',  'undefined'),
        ('string',             'undefined', 'string'),
    ]
    for key, prim, ref in type_map:
        if key in type_name:
            var_types[variable] = {'primitive_type': prim, 'reference_type': ref}
            return


def repair_equal_to(mr):
    """Repair _equal_to{VBG/JJ} predicates produced by alt rule conversion.

    The alt rule converts 'is equal to' -> 'is equal_to', which ccg2lambda
    parses as _equal_to{VBG} or _equal_to{JJ} with Acc arguments.
    The compiler expects _equal{JJ} with Dat arguments.

    For arr_ predicates, also adds direct equality (xA = xB) for alias resolution
    since the compiler creates aliases from IDENTIFIER EQUAL IDENTIFIER patterns.

    This repair:
    - Renames _equal_to{VBG/JJ} -> _equal{JJ}
    - Changes Acc -> Dat in the same predicate scope
    - For arr_ predicates, adds direct equality alongside Subj/Dat
    """
    # Rename the predicate
    mr = re.sub(r'_equal_to\{(VBG|JJ)\}', '_equal{JJ}', mr)
    # Change Acc to Dat (only within equal predicates - safe since Acc
    # is primarily used by equal_to in the MR structures we produce)
    mr = re.sub(r'\(Acc\(', '(Dat(', mr)

    return mr


def _normalize_arr_arguments(dynamic_si):
    """Normalize arr_ entries' arguments to match param_ structure.

    The si_building step adds extra primitive_type/reference_type fields
    to arguments, which causes type-code mismatches in the compiler when
    comparing arr_ predicates with param_ predicates via equal/Rel.
    """
    for key, entry in dynamic_si.items():
        if key.startswith('arr_') and 'arguments' in entry:
            args = entry['arguments']
            if args and isinstance(args, list) and isinstance(args[0], dict):
                # Keep only the symbol field
                entry['arguments'] = [{'symbol': args[0].get('symbol', '*')}]


def repair_mr(mr, dynamic_si=None):
    """Apply all arithmetic MR repairs.
    
    Args:
        mr: The MR string to repair
        dynamic_si: Optional dict to collect dynamic SI entries
        
    Returns:
        The repaired MR string
    """
    if not mr or not mr.strip():
        return mr
    
    original = mr
    
    # Task 2: Repair "+" with wrong POS in arithmetic context
    mr = repair_plus_vbd_vbp(mr)
    mr = repair_plus_nnp(mr, dynamic_si)
    
    # Task 5: Repair _divisible{JJ} with incomplete arguments
    mr = repair_divisible(mr)
    
    # Task 6: Handle dynamic compound expressions like _i+1{NN}
    mr = repair_compound_plus(mr, dynamic_si)
    
    # Repair known POS tagger errors (first_element{JJ}→{NN}, value{NN}→{NNS}, etc.)
    mr = repair_pos_tagger_errors(mr)
    
    # Repair _equal_to{VBG/JJ} → _equal{JJ} with Acc→Dat
    mr = repair_equal_to(mr)
    
    # Repair type predicates with wrong POS (JJ → NN)
    mr = repair_type_predicate_jj(mr)
    
    # Generate dynamic SI for param_, index, and arr_ predicates
    mr = repair_param_si(mr, dynamic_si)
    
    return mr


def main():
    """Command-line interface for MR repair."""
    if len(sys.argv) < 2:
        print("Usage: python mr_arithmetic_repair.py <mr_file> [--dynamic-si <output_yml>]",
              file=sys.stderr)
        sys.exit(1)
    
    mr_file = sys.argv[1]
    dynamic_si_path = None
    
    if '--dynamic-si' in sys.argv:
        idx = sys.argv.index('--dynamic-si')
        if idx + 1 < len(sys.argv):
            dynamic_si_path = sys.argv[idx + 1]
    
    with open(mr_file, 'r') as f:
        mr = f.read().strip()
    
    # Load existing dynamic SI entries if the file already exists
    # (e.g., from the preprocessing step's expression extraction)
    dynamic_si = {}
    if dynamic_si_path:
        if os.path.exists(dynamic_si_path):
            with open(dynamic_si_path, 'r') as f:
                existing = yaml.safe_load(f)
            if existing and isinstance(existing, list):
                for entry in existing:
                    term = entry.get('term', '')
                    if term:
                        dynamic_si[term] = entry

    # Normalize arr_ argument structures to prevent type-code mismatches
    if dynamic_si:
        _normalize_arr_arguments(dynamic_si)

    repaired = repair_mr(mr, dynamic_si if dynamic_si_path else None)
    
    with open(mr_file, 'w') as f:
        f.write(repaired)
    
    if dynamic_si and dynamic_si_path:
        write_dynamic_si(dynamic_si, dynamic_si_path)
    
    if repaired != mr:
        print("MR repaired: %s" % mr_file, file=sys.stderr)


if __name__ == '__main__':
    main()
