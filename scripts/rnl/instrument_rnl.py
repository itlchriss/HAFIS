"""
Instrument RNL conditions into Dafny source files.

For each problem in qwen3.7/s*/:
1. Reads rnl.txt (RNL bullets with // method: headers)
2. Reads verify_nl.dfy (Dafny file with NL annotations)
3. For each method in rnl.txt, instruments the RNL conditions
   as NL annotations inside the corresponding method body
4. Removes any existing NL annotations before the method
5. Writes the instrumented file back to verify_nl.dfy

Usage:
    python scripts/rnl/instrument_rnl.py [--verbose]
"""

import os
import re
import glob
import argparse

# Project root (scripts/rnl/ -> ../../)
PROJECT_ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..')
os.chdir(PROJECT_ROOT)


def classify_bullet(bullet: str) -> str:
    """Classify an RNL bullet as 'requires' or 'ensures'.
    
    Classification rules based on repository conventions:
    - Preconditions (requires): parameter constraints, array bounds, non-null checks
    - Postconditions (ensures): result properties, functional correctness, test cases
    """
    lower = bullet.lower()
    
    # Test case patterns (If X equals value, result equals value) -> ensures
    if lower.startswith('if the ') and 'result' in lower:
        return 'ensures'
    
    # Direct result references -> ensures
    ensures_keywords = [
        'the integer result', 'the boolean result', 'the string result',
        'the character result', 'the integer array result',
        'the string array result', 'the character array result',
        'the list result', 'the type_integer_ result',
        'the type_boolean_ result', 'the type_string_ result',
        'the length of the integer array result',
        'the length of the string result',
        'the length of the list result',
        'the first value of the integer array result',
        'the second value of the integer array result',
        'the first value of the string result',
        'the method may modify',
    ]
    for kw in ensures_keywords:
        if kw in lower:
            return 'ensures'
    
    # Functional correctness patterns -> ensures
    if 'result is' in lower and ('equal to' in lower or 'the maximum' in lower or 'the minimum' in lower):
        return 'ensures'
    if 'result is the' in lower:
        return 'ensures'
    if 'result contains' in lower or 'result does not contain' in lower:
        return 'ensures'
    if 'result is obtained' in lower or 'result is in the format' in lower:
        return 'ensures'
    if 'result is sorted' in lower or 'result is a valid' in lower:
        return 'ensures'
    
    # Ghost/function definitions -> ensures
    if lower.startswith('define the') or lower.startswith('let the ghost'):
        return 'ensures'
    
    # Loop invariants and termination metrics -> ensures
    if lower.startswith('the loop invariant') or lower.startswith('the termination metric'):
        return 'ensures'
    
    # Existence patterns with result binding -> ensures
    if 'there exist' in lower and 'result' in lower:
        return 'ensures'
    
    # Everything else -> requires
    return 'requires'


def parse_rnl_with_methods(filepath: str) -> dict:
    """Parse rnl.txt and group bullets by method.
    
    Returns dict:
        - methods: list of method names in order
        - method_bullets: dict mapping method_name -> list of bullets
    """
    if not os.path.exists(filepath):
        return None
    
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    result = {
        'methods': [],
        'method_bullets': {},
    }
    
    current_method = None
    
    for line in lines:
        line = line.strip()
        
        # Check for method header
        method_match = re.match(r'^//\s*method:\s*(\w+)', line)
        if method_match:
            current_method = method_match.group(1)
            if current_method not in result['methods']:
                result['methods'].append(current_method)
                result['method_bullets'][current_method] = []
            continue
        
        # Check for bullet
        if line.startswith('- '):
            bullet = line[2:]
            if current_method is None:
                current_method = '__global__'
                if current_method not in result['methods']:
                    result['methods'].append(current_method)
                    result['method_bullets'][current_method] = []
            result['method_bullets'][current_method].append(bullet)
    
    return result


def find_method_body_start(lines: list, method_name: str) -> int:
    """Find the line number of the opening brace of a method body.
    
    Returns the line index (0-based) of the opening brace, or -1 if not found.
    """
    found_method = False
    for i, line in enumerate(lines):
        # Look for method declaration
        if re.match(r'^\s*method\s+' + method_name + r'\s*[\(\<]', line) or \
           re.match(r'^\s*method\s+' + method_name + r'\s*$', line) or \
           re.match(r'^\s*method\s+' + method_name + r'\s*\(', line):
            found_method = True
            continue
        
        if found_method:
            # Check if this line has the opening brace
            if re.match(r'^\s*\{', line):
                return i
    
    return -1


def find_method_end(lines: list, body_start: int) -> int:
    """Find the line number of the closing brace of a method body.
    
    Returns the line index (0-based) of the closing brace, or -1 if not found.
    """
    if body_start < 0 or body_start >= len(lines):
        return -1
    
    brace_count = 0
    for i in range(body_start, len(lines)):
        line = lines[i]
        brace_count += line.count('{') - line.count('}')
        if brace_count == 0:
            return i
    
    return -1


def remove_existing_nl_annotations(lines: list) -> list:
    """Remove existing // requires(*...*) and // ensures(*...*) annotations."""
    result = []
    for line in lines:
        # Skip lines with NL annotations
        if re.match(r'^\s*//\s*(requires|ensures)\(\*', line):
            continue
        result.append(line)
    return result


def instrument_method(lines: list, method_name: str, bullets: list, classifications: list) -> list:
    """Instrument RNL conditions into a method body.
    
    Inserts NL annotations as comments inside the method body, after the opening brace.
    """
    if not bullets:
        return lines
    
    # Find method body start
    body_start = find_method_body_start(lines, method_name)
    if body_start < 0:
        return lines
    
    # Find method body end
    body_end = find_method_end(lines, body_start)
    if body_end < 0:
        return lines
    
    # Determine indentation from existing code in the method body
    indent = '    '  # default: 4 spaces
    for i in range(body_start + 1, min(body_start + 10, body_end)):
        match = re.match(r'^(\s+)\S', lines[i])
        if match:
            indent = match.group(1)
            break
    
    # Build NL annotation lines
    nl_lines = []
    for bullet, ctype in zip(bullets, classifications):
        nl_lines.append(f'{indent}// {ctype}(*{bullet}*);\n')
    
    # Insert NL annotations after the opening brace
    new_lines = lines[:body_start + 1] + nl_lines + lines[body_start + 1:]
    
    return new_lines


def process_problem(problem_dir: str, verbose: bool = False) -> bool:
    """Process a single problem directory.
    
    Returns True if successful, False otherwise.
    """
    problem_name = os.path.basename(problem_dir)
    rnl_path = os.path.join(problem_dir, 'rnl.txt')
    dafny_path = os.path.join(problem_dir, 'verify_nl.dfy')
    
    if not os.path.exists(rnl_path):
        if verbose:
            print(f"  SKIP {problem_name}: no rnl.txt")
        return False
    
    if not os.path.exists(dafny_path):
        if verbose:
            print(f"  SKIP {problem_name}: no verify_nl.dfy")
        return False
    
    # Parse RNL with method grouping
    rnl_parsed = parse_rnl_with_methods(rnl_path)
    if rnl_parsed is None or not rnl_parsed['methods']:
        if verbose:
            print(f"  SKIP {problem_name}: no methods/bullets in rnl.txt")
        return False
    
    # Read Dafny file
    with open(dafny_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    # Remove existing NL annotations
    lines = remove_existing_nl_annotations(lines)
    
    # Instrument each method
    for method_name in rnl_parsed['methods']:
        bullets = rnl_parsed['method_bullets'][method_name]
        if not bullets:
            continue
        
        classifications = [classify_bullet(b) for b in bullets]
        
        # For __global__ method, we need to find the main method
        if method_name == '__global__':
            # Find the main method (last 'method' declaration)
            main_method = None
            for i, line in enumerate(lines):
                match = re.match(r'^\s*method\s+(\w+)', line)
                if match:
                    main_method = match.group(1)
            if main_method:
                lines = instrument_method(lines, main_method, bullets, classifications)
        else:
            lines = instrument_method(lines, method_name, bullets, classifications)
    
    # Write back
    with open(dafny_path, 'w', encoding='utf-8') as f:
        f.writelines(lines)
    
    if verbose:
        total_bullets = sum(len(rnl_parsed['method_bullets'][m]) for m in rnl_parsed['methods'])
        methods_str = ', '.join(rnl_parsed['methods'])
        print(f"  OK {problem_name}: {total_bullets} bullets "
              f"methods=[{methods_str}] -> verify_nl.dfy")
    
    return True


def main():
    parser = argparse.ArgumentParser(description='Instrument RNL conditions into Dafny files')
    parser.add_argument('--verbose', '-v', action='store_true', help='Show detailed output')
    parser.add_argument('--base-dir', '-d', default='qwen3.7',
                        help='Base directory with problem folders')
    
    args = parser.parse_args()
    base_dir = os.path.join(PROJECT_ROOT, args.base_dir)
    
    print("=" * 70)
    print("RNL Instrumentation to Dafny Files")
    print("=" * 70)
    print(f"Base directory: {base_dir}")
    print()
    
    problem_dirs = sorted(glob.glob(os.path.join(base_dir, 's*')))
    
    success = 0
    skipped = 0
    failed = 0
    
    for problem_dir in problem_dirs:
        if not os.path.isdir(problem_dir):
            continue
        
        try:
            if process_problem(problem_dir, args.verbose):
                success += 1
            else:
                skipped += 1
        except Exception as e:
            failed += 1
            print(f"  FAIL {os.path.basename(problem_dir)}: {e}")
    
    print()
    print("=" * 70)
    print(f"RESULTS: {success} instrumented, {skipped} skipped, {failed} failed")
    print("=" * 70)


if __name__ == '__main__':
    main()
