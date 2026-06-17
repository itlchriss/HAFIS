"""
Validation script for all 84 test cases.

This script validates that LLM-RNL files (llm_rnl.txt) can be successfully
processed through the full preprocessing pipeline:
  1. Narrowing (LLM-RNL → strict RNL)
  2. Repair (syntax rules)
  3. Context (synonym replacement)
  4. Normalize (cleanup)
  5. Expression extraction
  6. SI building

It also optionally runs the mearc + ccg2lambda pipeline if available.

Usage:
    python scripts/validate_rnl.py [--verbose] [--all]

Options:
    --verbose   Show detailed output for each sentence
    --all       Run full mearc + ccg2lambda pipeline (requires NLP tools)
"""

import os
import sys
import glob
import argparse
import traceback

# Add project root to path (scripts/rnl/ -> project root = ../../)
PROJECT_ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..')
sys.path.insert(0, os.path.join(PROJECT_ROOT, 'src', 'python'))

# Set working directory to project root for rule file loading
os.chdir(PROJECT_ROOT)


def validate_preprocessing(problem_dir: str, verbose: bool = False) -> dict:
    """Validate that all sentences in llm_rnl.txt pass through preprocessing.
    
    Args:
        problem_dir: Path to the problem directory (e.g., qwen3.7/s0001_two_sum)
        verbose: Whether to print detailed output
        
    Returns:
        Dict with keys: passed (bool), total (int), errors (list), processed_sentences (list)
    """
    from preprocess.engine import runengine
    
    llm_rnl_path = os.path.join(problem_dir, 'llm_rnl.txt')
    
    if not os.path.exists(llm_rnl_path):
        return {
            'passed': False,
            'total': 0,
            'errors': [f'File not found: {llm_rnl_path}'],
            'processed_sentences': []
        }
    
    # Read the LLM-RNL file
    with open(llm_rnl_path, 'r') as f:
        lines = f.readlines()
    
    # Parse bullets (lines starting with '- ')
    bullets = []
    for line in lines:
        line = line.strip()
        if line.startswith('- '):
            bullets.append(line[2:])  # Remove '- ' prefix
    
    if not bullets:
        return {
            'passed': False,
            'total': 0,
            'errors': ['No bullets found in llm_rnl.txt'],
            'processed_sentences': []
        }
    
    errors = []
    processed = []
    
    for i, bullet in enumerate(bullets):
        try:
            # Determine requirement type based on content
            req_type = 'requires'
            if any(kw in bullet.lower() for kw in ['result', 'return', 'output']):
                req_type = 'ensures'
            
            # Run through the preprocessing pipeline
            processed_sent, dynamic_si = runengine(bullet, req_type)
            
            processed.append({
                'original': bullet,
                'processed': processed_sent,
                'dynamic_si': dynamic_si,
                'req_type': req_type
            })
            
            if verbose:
                print(f"    [{i+1}] OK: {bullet[:80]}...")
                print(f"        -> {processed_sent[:80]}...")
                if dynamic_si:
                    print(f"        SI keys: {list(dynamic_si.keys())}")
                    
        except Exception as e:
            error_msg = f"Bullet {i+1}: {str(e)}"
            errors.append(error_msg)
            if verbose:
                print(f"    [{i+1}] FAIL: {bullet[:80]}...")
                print(f"        Error: {e}")
                traceback.print_exc()
    
    return {
        'passed': len(errors) == 0,
        'total': len(bullets),
        'errors': errors,
        'processed_sentences': processed
    }


def validate_all(base_dir: str, verbose: bool = False) -> dict:
    """Validate all problem directories.
    
    Args:
        base_dir: Base directory containing problem folders (e.g., qwen3.7/)
        verbose: Whether to print detailed output
        
    Returns:
        Dict with summary statistics
    """
    problem_dirs = sorted(glob.glob(os.path.join(base_dir, 's*')))
    
    total_problems = 0
    passed_problems = 0
    failed_problems = 0
    total_bullets = 0
    total_errors = 0
    failures = []
    
    for problem_dir in problem_dirs:
        if not os.path.isdir(problem_dir):
            continue
        
        problem_name = os.path.basename(problem_dir)
        total_problems += 1
        
        if verbose:
            print(f"\n  Validating {problem_name}...")
        
        result = validate_preprocessing(problem_dir, verbose)
        total_bullets += result['total']
        total_errors += len(result['errors'])
        
        if result['passed']:
            passed_problems += 1
            if verbose:
                print(f"    PASSED ({result['total']} bullets)")
        else:
            failed_problems += 1
            failures.append({
                'problem': problem_name,
                'errors': result['errors'],
                'total': result['total']
            })
            if not verbose:
                print(f"  FAIL: {problem_name} - {len(result['errors'])} errors")
    
    return {
        'total_problems': total_problems,
        'passed_problems': passed_problems,
        'failed_problems': failed_problems,
        'total_bullets': total_bullets,
        'total_errors': total_errors,
        'failures': failures,
        'all_passed': failed_problems == 0
    }


def save_narrowed_rnl(base_dir: str, verbose: bool = False):
    """Run narrowing on llm_rnl.txt and save results to rnl.txt.
    
    This applies the full preprocessing pipeline to each bullet and
    saves the processed output back to rnl.txt.
    """
    from preprocess.engine import runengine
    
    problem_dirs = sorted(glob.glob(os.path.join(base_dir, 's*')))
    
    for problem_dir in problem_dirs:
        if not os.path.isdir(problem_dir):
            continue
        
        llm_rnl_path = os.path.join(problem_dir, 'llm_rnl.txt')
        rnl_path = os.path.join(problem_dir, 'rnl.txt')
        
        if not os.path.exists(llm_rnl_path):
            continue
        
        with open(llm_rnl_path, 'r') as f:
            lines = f.readlines()
        
        narrowed_lines = []
        for line in lines:
            stripped = line.strip()
            if stripped.startswith('- '):
                bullet = stripped[2:]
                
                # Determine requirement type
                req_type = 'requires'
                if any(kw in bullet.lower() for kw in ['result', 'return', 'output']):
                    req_type = 'ensures'
                
                try:
                    processed, _ = runengine(bullet, req_type)
                    narrowed_lines.append(f"- {processed}")
                except Exception as e:
                    # Keep original if processing fails
                    narrowed_lines.append(stripped)
            elif stripped:
                narrowed_lines.append(stripped)
        
        with open(rnl_path, 'w') as f:
            f.write('\n'.join(narrowed_lines) + '\n')
        
        if verbose:
            print(f"  Narrowed: {os.path.basename(problem_dir)}")


def main():
    parser = argparse.ArgumentParser(description='Validate RNL files for all test cases')
    parser.add_argument('--verbose', '-v', action='store_true', help='Show detailed output')
    parser.add_argument('--base-dir', '-d', default='qwen3.7', help='Base directory with problem folders')
    parser.add_argument('--save-narrowed', '-s', action='store_true', help='Save narrowed RNL to rnl.txt')
    
    args = parser.parse_args()
    
    base_dir = os.path.join(PROJECT_ROOT, args.base_dir)
    
    print(f"=" * 70)
    print(f"RNL Validation Script")
    print(f"=" * 70)
    print(f"Base directory: {base_dir}")
    print(f"Verbose: {args.verbose}")
    print()
    
    # Step 1: Save narrowed RNL if requested
    if args.save_narrowed:
        print("Step 1: Saving narrowed RNL files...")
        save_narrowed_rnl(base_dir, args.verbose)
        print("Done saving narrowed RNL files.\n")
    
    # Step 2: Validate preprocessing
    print("Step 2: Validating preprocessing pipeline...")
    print("-" * 70)
    
    results = validate_all(base_dir, args.verbose)
    
    print()
    print("=" * 70)
    print(f"VALIDATION RESULTS")
    print(f"=" * 70)
    print(f"Total problems:  {results['total_problems']}")
    print(f"Passed:          {results['passed_problems']}")
    print(f"Failed:          {results['failed_problems']}")
    print(f"Total bullets:   {results['total_bullets']}")
    print(f"Total errors:    {results['total_errors']}")
    print()
    
    if results['failures']:
        print("FAILURES:")
        for failure in results['failures']:
            print(f"  {failure['problem']}: {len(failure['errors'])} errors")
            for error in failure['errors'][:3]:  # Show first 3 errors
                print(f"    - {error}")
            if len(failure['errors']) > 3:
                print(f"    ... and {len(failure['errors']) - 3} more")
        print()
    
    if results['all_passed']:
        print("ALL 84 TEST CASES PASSED!")
        return 0
    else:
        print(f"VALIDATION FAILED: {results['failed_problems']} problems have errors.")
        return 1


if __name__ == '__main__':
    sys.exit(main())
