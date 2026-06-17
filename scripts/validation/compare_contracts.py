"""
Compare generated Dafny contracts against expected contracts from nl_contract_map.yml.

After mearc processes verify_nl.dfy files, the NL annotations
(// requires(*NL*); / // ensures(*NL*);) are replaced with formal
Dafny contracts (requires / ensures). This script validates that
the generated contracts match expectations.

Usage:
    python scripts/validation/compare_contracts.py [--verbose] [--base-dir qwen3.7]
"""

import os
import re
import glob
import argparse
import yaml

# Project root (scripts/validation/ -> ../../)
PROJECT_ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..')
os.chdir(PROJECT_ROOT)


def parse_nl_annotations(filepath: str) -> list:
    """Parse NL annotations from a verify_nl.dfy file.
    
    Extracts // requires(*NL*); and // ensures(*NL*); comments.
    Returns list of (type, nl_text) tuples.
    """
    annotations = []
    if not os.path.exists(filepath):
        return annotations
    
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            match = re.match(r'//\s*(requires|ensures)\(\*(.*)\*\);', line)
            if match:
                annotations.append({
                    'type': match.group(1),
                    'nl': match.group(2),
                })
    return annotations


def parse_formal_contracts(filepath: str) -> list:
    """Parse formal Dafny contracts from a .dfy file.
    
    Extracts requires/ensures clauses that appear between the method
    signature and the opening brace of the method body.
    Returns list of (type, contract_text) tuples.
    """
    contracts = []
    if not os.path.exists(filepath):
        return contracts
    
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    # Find the main method (last 'method' declaration)
    main_method_idx = None
    for i, line in enumerate(lines):
        if re.match(r'^\s*method\s+\w+', line):
            main_method_idx = i
    
    if main_method_idx is None:
        return contracts
    
    # Parse contracts between method signature and opening brace
    in_contracts = False
    for i in range(main_method_idx + 1, len(lines)):
        line = lines[i].strip()
        
        # Check for contract keywords
        contract_match = re.match(r'(requires|ensures)\s+(.*)', line)
        if contract_match:
            contracts.append({
                'type': contract_match.group(1),
                'contract': contract_match.group(2).rstrip(),
            })
            in_contracts = True
            continue
        
        # Skip comments between contracts
        if line.startswith('//'):
            continue
        
        # Opening brace = end of contracts
        if line.startswith('{') or line == '{':
            break
        
        # Empty lines between contracts are OK
        if not line:
            continue
        
        # If we had contracts but hit something else, stop
        if in_contracts:
            break
    
    return contracts


def is_nl_still_annotated(filepath: str) -> bool:
    """Check if a verify_nl.dfy still has NL annotations (mearc not run yet)."""
    if not os.path.exists(filepath):
        return False
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            if re.search(r'//\s*(requires|ensures)\(\*', line):
                return True
    return False


def normalize_contract(text: str) -> str:
    """Normalize a Dafny contract for comparison.
    
    Strips whitespace, normalizes spaces, removes trailing comments.
    """
    text = text.strip()
    # Normalize multiple spaces to single
    text = re.sub(r'\s+', ' ', text)
    # Remove inline comments
    text = re.sub(r'//.*$', '', text).strip()
    return text


def compare_contracts_for_problem(problem_dir: str, verbose: bool = False) -> dict:
    """Compare contracts for a single problem.
    
    Returns a result dict with:
        - problem: problem name
        - status: 'ok' | 'mismatch' | 'no_map' | 'not_translated' | 'error'
        - total_expected: number of expected contracts from map
        - total_generated: number of generated contracts
        - matched: number of exact matches
        - mismatched: list of mismatch details
        - missing: number of expected contracts not generated
        - extra: number of generated contracts not expected
    """
    problem_name = os.path.basename(problem_dir)
    map_path = os.path.join(problem_dir, 'nl_contract_map.yml')
    nl_dafny_path = os.path.join(problem_dir, 'verify_nl.dfy')
    
    result = {
        'problem': problem_name,
        'status': 'ok',
        'total_expected': 0,
        'total_generated': 0,
        'matched': 0,
        'mismatched': [],
        'missing': 0,
        'extra': 0,
    }
    
    # Load the contract map
    if not os.path.exists(map_path):
        result['status'] = 'no_map'
        return result
    
    with open(map_path, 'r', encoding='utf-8') as f:
        contract_map = yaml.safe_load(f)
    
    annotations = contract_map.get('annotations', [])
    expected_contracts = [a for a in annotations if a.get('original_contract')]
    result['total_expected'] = len(expected_contracts)
    
    # Check if mearc has processed the file
    if is_nl_still_annotated(nl_dafny_path):
        result['status'] = 'not_translated'
        return result
    
    # Parse generated contracts from verify_nl.dfy
    generated = parse_formal_contracts(nl_dafny_path)
    result['total_generated'] = len(generated)
    
    # Compare by position (index-based matching)
    # Match generated contracts to expected contracts from the map
    matched = 0
    mismatched = []
    
    for i, expected in enumerate(expected_contracts):
        exp_type = expected.get('type', '')
        exp_contract = normalize_contract(expected.get('original_contract', ''))
        
        if i < len(generated):
            gen = generated[i]
            gen_type = gen.get('type', '')
            gen_contract = normalize_contract(gen.get('contract', ''))
            
            if exp_type == gen_type and exp_contract == gen_contract:
                matched += 1
                if verbose:
                    print(f"    MATCH [{i}] {exp_type}: {exp_contract}")
            else:
                mismatched.append({
                    'id': i,
                    'expected_type': exp_type,
                    'expected_contract': exp_contract,
                    'generated_type': gen_type,
                    'generated_contract': gen_contract,
                })
                if verbose:
                    print(f"    MISMATCH [{i}]:")
                    print(f"      expected:  {exp_type}: {exp_contract}")
                    print(f"      generated: {gen_type}: {gen_contract}")
        else:
            mismatched.append({
                'id': i,
                'expected_type': exp_type,
                'expected_contract': exp_contract,
                'generated_type': None,
                'generated_contract': None,
            })
            if verbose:
                print(f"    MISSING [{i}]: {exp_type}: {exp_contract}")
    
    # Check for extra generated contracts
    extra_count = max(0, len(generated) - len(expected_contracts))
    
    result['matched'] = matched
    result['mismatched'] = mismatched
    result['missing'] = sum(1 for m in mismatched if m.get('generated_type') is None)
    result['extra'] = extra_count
    
    if mismatched or extra_count > 0:
        result['status'] = 'mismatch'
    
    return result


def main():
    parser = argparse.ArgumentParser(
        description='Compare generated Dafny contracts against expected contracts')
    parser.add_argument('--verbose', '-v', action='store_true',
                        help='Show per-annotation comparison details')
    parser.add_argument('--base-dir', '-d', default='qwen3.7',
                        help='Base directory with problem folders')
    parser.add_argument('--summary-only', '-s', action='store_true',
                        help='Only show summary, not per-problem details')
    
    args = parser.parse_args()
    base_dir = os.path.join(PROJECT_ROOT, args.base_dir)
    
    print("=" * 70)
    print("Contract Comparison Validator")
    print("=" * 70)
    print(f"Base directory: {base_dir}")
    print()
    
    problem_dirs = sorted(glob.glob(os.path.join(base_dir, 's*')))
    
    # Counters
    ok_count = 0
    mismatch_count = 0
    no_map_count = 0
    not_translated_count = 0
    error_count = 0
    
    total_expected = 0
    total_generated = 0
    total_matched = 0
    
    mismatches = []
    
    for problem_dir in problem_dirs:
        if not os.path.isdir(problem_dir):
            continue
        
        try:
            res = compare_contracts_for_problem(problem_dir, args.verbose)
        except Exception as e:
            error_count += 1
            print(f"  ERROR {os.path.basename(problem_dir)}: {e}")
            continue
        
        status = res['status']
        
        if status == 'ok':
            ok_count += 1
            total_expected += res['total_expected']
            total_generated += res['total_generated']
            total_matched += res['matched']
            if not args.summary_only:
                print(f"  OK    {res['problem']}: "
                      f"{res['matched']}/{res['total_expected']} contracts matched")
        elif status == 'mismatch':
            mismatch_count += 1
            total_expected += res['total_expected']
            total_generated += res['total_generated']
            total_matched += res['matched']
            mismatches.append(res)
            if not args.summary_only:
                print(f"  FAIL  {res['problem']}: "
                      f"{res['matched']}/{res['total_expected']} matched, "
                      f"{len(res['mismatched'])} mismatched, "
                      f"{res['extra']} extra")
        elif status == 'not_translated':
            not_translated_count += 1
            if not args.summary_only:
                print(f"  WAIT  {res['problem']}: NL annotations still present "
                      f"(mearc not run yet)")
        elif status == 'no_map':
            no_map_count += 1
            if not args.summary_only:
                print(f"  SKIP  {res['problem']}: no nl_contract_map.yml")
    
    # Print summary
    print()
    print("=" * 70)
    print("SUMMARY")
    print("=" * 70)
    print(f"  Total problems:      {len(problem_dirs)}")
    print(f"  OK (all matched):    {ok_count}")
    print(f"  Mismatch:            {mismatch_count}")
    print(f"  Not translated:      {not_translated_count} (mearc not run yet)")
    print(f"  No contract map:     {no_map_count}")
    print(f"  Errors:              {error_count}")
    
    if ok_count + mismatch_count > 0:
        print()
        print(f"  Contract statistics (translated problems only):")
        print(f"    Total expected:    {total_expected}")
        print(f"    Total generated:   {total_generated}")
        print(f"    Total matched:     {total_matched}")
        if total_expected > 0:
            pct = (total_matched / total_expected) * 100
            print(f"    Match rate:        {pct:.1f}%")
    
    if mismatches and not args.verbose:
        print()
        print("  Mismatched problems:")
        for m in mismatches:
            print(f"    {m['problem']}: "
                  f"{len(m['mismatched'])} mismatched, {m['extra']} extra")
    
    print("=" * 70)
    
    # Return exit code
    if mismatch_count > 0 or error_count > 0:
        return 1
    return 0


if __name__ == '__main__':
    exit(main())
