"""
Convert RNL bullets to NL-annotated Dafny files.

For each problem in qwen3.7/s*/:
1. Reads rnl.txt (RNL bullets)
2. Reads verify.dfy (original with formal contracts)
3. Classifies each bullet as requires/ensures
4. Creates verify_nl.dfy with NL annotations as comments
   (// requires(*NL*); // ensures(*NL*);)
5. Creates nl_contract_map.yml tracking NL-to-contract pairs

Usage:
    python scripts/rnl/rnl_to_dafny_nl.py [--verbose]
"""

import os
import re
import glob
import argparse
import yaml

# Project root (scripts/rnl/ -> ../../)
PROJECT_ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..')
os.chdir(PROJECT_ROOT)


def classify_bullet(bullet: str) -> str:
    """Classify an RNL bullet as 'requires' or 'ensures'.
    
    Heuristic: if the bullet mentions 'result', 'return', or 'output'
    in a postcondition context, it's an ensures. Otherwise requires.
    """
    lower = bullet.lower()
    
    # Test case patterns (If X equals value, result equals value) -> ensures
    if lower.startswith('if the ') and 'result' in lower:
        return 'ensures'
    
    # Direct result references -> ensures
    ensures_keywords = [
        'the integer result', 'the boolean result', 'the string result',
        'the type_integer_ result', 'the type_boolean_ result',
        'the type_string_ result', 'the integer array result',
        'the length of the integer array result',
        'the length of the string result',
        'the method may modify',
    ]
    for kw in ensures_keywords:
        if kw in lower:
            return 'ensures'
    
    # Functional correctness patterns -> ensures
    if 'result is' in lower and ('equal to' in lower or 'the maximum' in lower or 'the minimum' in lower):
        return 'ensures'
    if 'result is the' in lower:  # e.g., "result is the zigzag conversion"
        return 'ensures'
    if 'result contains' in lower or 'result does not contain' in lower:
        return 'ensures'
    if 'result is obtained' in lower or 'result is in the format' in lower:
        return 'ensures'
    if 'result is sorted' in lower or 'result is a valid' in lower:
        return 'ensures'
    
    # Ghost/function definitions -> ensures (they define spec infrastructure)
    if lower.startswith('define the') or lower.startswith('let the ghost'):
        return 'ensures'
    
    # Everything else -> requires (preconditions, parameter constraints)
    return 'requires'


def parse_verify_dfy(filepath: str) -> dict:
    """Parse a verify.dfy file to extract structure.
    
    Returns dict with:
        - header_lines: lines before the main method
        - method_name: name of the main method
        - method_sig_line: the method signature line
        - contracts: list of (type, text) for requires/ensures/modifies
        - body_lines: lines from { onwards (the method body)
        - helper_lines: helper function/lemma definitions before main method
    """
    if not os.path.exists(filepath):
        return None
    
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    result = {
        'header_lines': [],
        'method_name': None,
        'method_sig_line': None,
        'contracts': [],
        'body_lines': [],
        'helper_lines': [],
        'full_lines': lines,
    }
    
    # Find the main method (last 'method' declaration)
    main_method_idx = None
    for i, line in enumerate(lines):
        if re.match(r'^\s*method\s+\w+', line):
            main_method_idx = i
    
    if main_method_idx is None:
        # No method found - treat entire file as header
        result['header_lines'] = lines
        return result
    
    # Extract method name
    sig_match = re.match(r'\s*method\s+(\w+)', lines[main_method_idx])
    if sig_match:
        result['method_name'] = sig_match.group(1)
    
    # Header = everything before main method
    result['header_lines'] = lines[:main_method_idx]
    
    # Parse method signature and contracts
    i = main_method_idx
    result['method_sig_line'] = lines[i]
    i += 1
    
    # Collect contracts (requires/ensures/modifies/decreases)
    in_contract_block = False
    while i < len(lines):
        line = lines[i].strip()
        
        # Check for contract keywords
        contract_match = re.match(r'(requires|ensures|modifies|decreases)\s+(.*)', line)
        if contract_match:
            ctype = contract_match.group(1)
            ctext = contract_match.group(2).rstrip()
            result['contracts'].append((ctype, ctext))
            in_contract_block = True
            i += 1
            continue
        
        # Skip comments between contracts
        if line.startswith('//'):
            i += 1
            continue
        
        # Opening brace = end of contracts
        if line.startswith('{') or line == '{':
            result['body_lines'] = lines[i:]
            break
        
        # If we're in the contract block, continuation lines are part
        # of multi-line contracts (e.g., ensures ... && \n ... && \n ...)
        # Skip them to prevent leaking into the method body.
        if in_contract_block:
            i += 1
            continue
        
        # If we hit something else before any contract, it's a
        # continuation of the method signature
        result['method_sig_line'] += lines[i]
        i += 1
    
    return result


def extract_original_contracts(parsed: dict) -> list:
    """Extract original formal contracts from parsed verify.dfy."""
    contracts = []
    for ctype, ctext in parsed.get('contracts', []):
        if ctype in ('requires', 'ensures'):
            contracts.append({'type': ctype, 'contract': ctext})
    return contracts


def generate_nl_dafny(parsed: dict, bullets: list, classifications: list) -> str:
    """Generate the NL-annotated Dafny file content.
    
    Format:
    - Header comments
    - Helper functions/lemmas (from header)
    - NL annotations as // requires(*NL*); / // ensures(*NL*);
    - Method signature (without formal contracts)
    - Method body
    """
    output_lines = []
    
    # Write header lines (comments, helper functions)
    for line in parsed.get('header_lines', []):
        output_lines.append(line.rstrip())
    
    # Write NL annotations before the method
    for bullet, ctype in zip(bullets, classifications):
        output_lines.append(f'// {ctype}(*{bullet}*);')
    
    # Write method signature
    sig = parsed.get('method_sig_line', '')
    if sig:
        output_lines.append(sig.rstrip())
    
    # Write method body (starting from {)
    for line in parsed.get('body_lines', []):
        output_lines.append(line.rstrip())
    
    return '\n'.join(output_lines) + '\n'


def generate_contract_map(problem_name: str, method_name: str,
                          bullets: list, classifications: list,
                          original_contracts: list) -> dict:
    """Generate the NL-to-contract mapping for validation."""
    annotations = []
    for i, (bullet, ctype) in enumerate(zip(bullets, classifications)):
        entry = {
            'id': i,
            'type': ctype,
            'nl': bullet,
        }
        # Try to match with an original contract by index
        if i < len(original_contracts):
            entry['original_contract'] = original_contracts[i].get('contract', '')
        annotations.append(entry)
    
    return {
        'problem': problem_name,
        'method': method_name or 'unknown',
        'total_annotations': len(annotations),
        'requires_count': sum(1 for c in classifications if c == 'requires'),
        'ensures_count': sum(1 for c in classifications if c == 'ensures'),
        'original_contract_count': len(original_contracts),
        'annotations': annotations,
    }


def process_problem(problem_dir: str, verbose: bool = False) -> bool:
    """Process a single problem directory.
    
    Returns True if successful, False otherwise.
    """
    problem_name = os.path.basename(problem_dir)
    rnl_path = os.path.join(problem_dir, 'rnl.txt')
    verify_path = os.path.join(problem_dir, 'verify.dfy')
    nl_dafny_path = os.path.join(problem_dir, 'verify_nl.dfy')
    map_path = os.path.join(problem_dir, 'nl_contract_map.yml')
    
    if not os.path.exists(rnl_path):
        if verbose:
            print(f"  SKIP {problem_name}: no rnl.txt")
        return False
    
    if not os.path.exists(verify_path):
        if verbose:
            print(f"  SKIP {problem_name}: no verify.dfy")
        return False
    
    # Read RNL bullets
    with open(rnl_path, 'r', encoding='utf-8') as f:
        rnl_lines = f.readlines()
    
    bullets = []
    for line in rnl_lines:
        line = line.strip()
        if line.startswith('- '):
            bullets.append(line[2:])  # Remove '- ' prefix
    
    if not bullets:
        if verbose:
            print(f"  SKIP {problem_name}: no bullets in rnl.txt")
        return False
    
    # Classify each bullet
    classifications = [classify_bullet(b) for b in bullets]
    
    # Parse verify.dfy
    parsed = parse_verify_dfy(verify_path)
    if parsed is None:
        if verbose:
            print(f"  SKIP {problem_name}: could not parse verify.dfy")
        return False
    
    # Extract original contracts for comparison
    original_contracts = extract_original_contracts(parsed)
    
    # Generate NL-annotated Dafny file
    nl_content = generate_nl_dafny(parsed, bullets, classifications)
    with open(nl_dafny_path, 'w', encoding='utf-8') as f:
        f.write(nl_content)
    
    # Generate contract mapping
    contract_map = generate_contract_map(
        problem_name, parsed.get('method_name'),
        bullets, classifications, original_contracts
    )
    with open(map_path, 'w', encoding='utf-8') as f:
        yaml.dump(contract_map, f, default_flow_style=False, allow_unicode=True, sort_keys=False)
    
    if verbose:
        req_count = sum(1 for c in classifications if c == 'requires')
        ens_count = sum(1 for c in classifications if c == 'ensures')
        print(f"  OK {problem_name}: {len(bullets)} bullets "
              f"({req_count} requires, {ens_count} ensures) "
              f"-> verify_nl.dfy + nl_contract_map.yml")
    
    return True


def main():
    parser = argparse.ArgumentParser(description='Convert RNL bullets to NL-annotated Dafny files')
    parser.add_argument('--verbose', '-v', action='store_true', help='Show detailed output')
    parser.add_argument('--base-dir', '-d', default='qwen3.7',
                        help='Base directory with problem folders')
    
    args = parser.parse_args()
    base_dir = os.path.join(PROJECT_ROOT, args.base_dir)
    
    print("=" * 70)
    print("RNL to NL-Annotated Dafny Converter")
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
    print(f"RESULTS: {success} converted, {skipped} skipped, {failed} failed")
    print("=" * 70)


if __name__ == '__main__':
    main()
