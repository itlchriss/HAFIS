"""
generate_all_rnl.py - Fix pronouns in existing LLM-RNL files and re-instrument.

This script processes all 84 problems in qwen3.7/:
1. Reads each llm_rnl.txt and the method signature from verify.dfy
2. Applies eliminate_pronouns_from_output to fix any remaining pronouns
3. Writes the cleaned output back to llm_rnl.txt and rnl.txt
"""

import os
import re
import glob
import sys

PROJECT_ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..')
os.chdir(PROJECT_ROOT)

STRICT_PRPS = [
    'i', 'you', 'he', 'she', 'it', 'we', 'they',
    'me', 'him', 'her', 'us', 'them',
    'mine', 'yours', 'his', 'hers', 'its', 'ours', 'theirs',
    'my', 'your', 'our', 'their',
    'myself', 'yourself', 'himself', 'herself', 'itself', 'ourselves', 'themselves',
    'this', 'that', 'these', 'those',
    'who', 'whom', 'whose', 'which',
]


def _extract_method_params(signature):
    params = []
    result_type = 'void'
    paren_match = re.search(r'(\w+)\s*\(([^)]*)\)', signature)
    if not paren_match:
        return params, result_type
    method_name = paren_match.group(1)
    param_str = paren_match.group(2).strip()
    before_method = signature[:signature.rfind(method_name)].strip()
    words_before = before_method.split()
    if words_before:
        result_type = words_before[-1].replace('[]', '')
    if param_str:
        for p in param_str.split(','):
            p = p.strip()
            pm = re.match(r'(\w+(?:<[^>]+>)?(?:\[\])?)\s+(\w+)', p)
            if pm:
                ptype = pm.group(1)
                pname = pm.group(2)
                is_array = '[]' in ptype or ptype.lower() in ('list', 'array')
                params.append((ptype.replace('[]', ''), pname, is_array))
    return params, result_type


def _build_pronoun_replace_map(params, result_type):
    pmap = {}
    if not params:
        return [], pmap
    first_param = params[0]
    ptype, pname, is_array = first_param
    if is_array:
        first_full = 'all values in the %s array parameter `%s`' % (ptype, pname)
        first_singular = 'the %s array parameter `%s`' % (ptype, pname)
    else:
        first_full = 'the %s parameter `%s`' % (ptype, pname)
        first_singular = first_full
    result_full = 'the %s result' % result_type
    for pp in ('they', 'them', 'their', 'theirs'):
        pmap[pp] = first_full
    for sp in ('it', 'its', 'he', 'she', 'him', 'his', 'her'):
        pmap[sp] = first_singular
    for ptype_i, pname_i, is_array_i in params:
        if is_array_i:
            plural_ref = 'all values in the %s array parameter `%s`' % (ptype_i, pname_i)
            singular_ref = 'the %s array parameter `%s`' % (ptype_i, pname_i)
        else:
            plural_ref = 'the %s parameters `%s`' % (ptype_i, pname_i)
            singular_ref = 'the %s parameter `%s`' % (ptype_i, pname_i)
        for pp in ('they', 'them', 'their', 'theirs'):
            pmap.setdefault(pp, plural_ref)
        for sp in ('it', 'its', 'he', 'she', 'him', 'his', 'her'):
            pmap.setdefault(sp, singular_ref)
    for rp in ('it', 'its'):
        pmap[rp] = result_full
    for dp in ('this', 'that', 'these', 'those', 'who', 'whom', 'whose', 'which',
               'myself', 'yourself', 'himself', 'herself', 'itself',
               'i', 'you', 'we', 'us', 'me', 'my', 'your', 'our',
               'mine', 'yours', 'hers', 'ours'):
        pmap[dp] = first_singular
    ordered = sorted(pmap.keys(), key=len, reverse=True)
    return ordered, pmap


def eliminate_pronouns_from_output(text, signature):
    params, result_type = _extract_method_params(signature)
    if not params:
        return text
    ordered_pronouns, pmap = _build_pronoun_replace_map(params, result_type)
    escaped = [re.escape(p) for p in ordered_pronouns]
    pronoun_pattern = re.compile(
        r'\b(' + '|'.join(escaped) + r")('s)?\b(?!')",
        re.IGNORECASE
    )
    param_pattern = re.compile(
        r'(?:the\s+)?(?:\w+\s+)?(?:array\s+|list\s+)?parameter\s+`(\w+)`',
        re.IGNORECASE
    )
    result_pattern = re.compile(r'(?:the\s+)?(?:\w+\s+)?result', re.IGNORECASE)
    lines = text.split('\n')
    out_lines = []
    context = {}
    last_param_ref = [None]
    for line in lines:
        stripped = line.strip()
        if stripped.startswith('-'):
            for pm in param_pattern.finditer(stripped):
                pname = pm.group(1)
                full_match = pm.group(0)
                last_param_ref[0] = full_match
                for pt, pn, is_arr in params:
                    if pn == pname:
                        if is_arr:
                            plural_form = 'all values in ' + full_match
                            context['they'] = plural_form
                            context['them'] = plural_form
                            context['their'] = plural_form
                            context['theirs'] = plural_form
                            context['it'] = full_match
                            context['its'] = full_match
                        else:
                            for k in ('it', 'its', 'he', 'she', 'him', 'his', 'her',
                                      'they', 'them', 'their', 'theirs',
                                      'this', 'that', 'which', 'who'):
                                context[k] = full_match
                        break
            for rm in result_pattern.finditer(stripped):
                for k in ('it', 'its', 'this', 'that', 'which'):
                    context[k] = rm.group(0)
            if not context:
                if last_param_ref[0]:
                    fallback = last_param_ref[0]
                else:
                    for pt, pn, is_arr in params:
                        if is_arr:
                            fallback = 'the %s array parameter `%s`' % (pt, pn)
                        else:
                            fallback = 'the %s parameter `%s`' % (pt, pn)
                        break
                for k in ('it', 'its', 'he', 'she', 'him', 'his', 'her',
                          'they', 'them', 'their', 'theirs',
                          'this', 'that', 'which', 'who'):
                    context[k] = fallback
            def repl(m):
                pronoun = m.group(1).lower()
                has_s = m.group(2)
                replacement = context.get(pronoun, pmap.get(pronoun, m.group(0)))
                if has_s:
                    return replacement + "'s"
                orig = m.group(1)
                if orig[0].isupper() and replacement:
                    return replacement[0].upper() + replacement[1:]
                return replacement
            new_line = pronoun_pattern.sub(repl, stripped)
            out_lines.append(new_line)
        else:
            out_lines.append(line)
    return '\n'.join(out_lines)


def extract_signature_from_verify_dfy(verify_path):
    """Extract a Java-like method signature from a verify.dfy file."""
    if not os.path.exists(verify_path):
        return None
    with open(verify_path, 'r') as f:
        content = f.read()
    for line in content.split('\n'):
        if re.match(r'\s*method\s+\w+', line):
            dm = re.match(r'\s*method\s+(\w+)\(([^)]*)\)(?:\s+returns\s+\(([^)]*)\))?', line)
            if dm:
                name = dm.group(1)
                params_str = dm.group(2)
                returns_str = dm.group(3)
                def convert_type(t):
                    t = t.strip()
                    if t.startswith('array<'):
                        inner = t[6:-1]
                        return convert_type(inner) + '[]'
                    return t
                java_params = []
                for p in params_str.split(','):
                    p = p.strip()
                    if ':' in p:
                        pn, pt = p.split(':', 1)
                        java_params.append('%s %s' % (convert_type(pt), pn.strip()))
                java_returns = 'void'
                if returns_str:
                    for r in returns_str.split(','):
                        r = r.strip()
                        if ':' in r:
                            rn, rt = r.split(':', 1)
                            if rn.strip() == 'result':
                                java_returns = convert_type(rt)
                return '%s %s(%s)' % (java_returns, name, ', '.join(java_params))
    return None


def find_pronouns_in_text(text):
    found = set()
    for line in text.split('\n'):
        stripped = line.strip()
        if not stripped.startswith('-'):
            continue
        words = re.findall(r"\b[a-zA-Z']+\b", stripped)
        for w in words:
            if w.lower() in set(STRICT_PRPS):
                found.add(w.lower())
    return found


def main():
    base_dir = os.path.join(PROJECT_ROOT, 'qwen3.7')
    problems = sorted(glob.glob(os.path.join(base_dir, 's*')))

    print("=" * 70)
    print("Pronoun Elimination for Existing LLM-RNL Files")
    print("=" * 70)
    print(f"Processing {len(problems)} problems in {base_dir}")
    print()

    fixed = 0
    already_clean = 0
    skipped = 0

    for problem_dir in problems:
        if not os.path.isdir(problem_dir):
            continue
        name = os.path.basename(problem_dir)
        llm_rnl_path = os.path.join(problem_dir, 'llm_rnl.txt')
        rnl_path = os.path.join(problem_dir, 'rnl.txt')
        verify_path = os.path.join(problem_dir, 'verify.dfy')

        if not os.path.exists(llm_rnl_path):
            print(f"  SKIP {name}: no llm_rnl.txt")
            skipped += 1
            continue

        # Read existing content
        with open(llm_rnl_path, 'r') as f:
            original = f.read()

        # Check for pronouns
        pronouns_before = find_pronouns_in_text(original)

        # Extract signature from verify.dfy
        sig = extract_signature_from_verify_dfy(verify_path)
        if not sig:
            print(f"  SKIP {name}: could not extract signature from verify.dfy")
            skipped += 1
            continue

        # Apply pronoun elimination
        cleaned = eliminate_pronouns_from_output(original, sig)

        # Check pronouns after
        pronouns_after = find_pronouns_in_text(cleaned)

        if pronouns_before and not pronouns_after:
            # Fixed!
            with open(llm_rnl_path, 'w') as f:
                f.write(cleaned)
            with open(rnl_path, 'w') as f:
                f.write(cleaned)
            fixed += 1
            print(f"  FIXED {name}: removed {sorted(pronouns_before)}")
        elif not pronouns_before:
            already_clean += 1
        else:
            # Still has pronouns after elimination
            remaining = pronouns_after - pronouns_before  # new pronouns (unlikely)
            if pronouns_after:
                with open(llm_rnl_path, 'w') as f:
                    f.write(cleaned)
                with open(rnl_path, 'w') as f:
                    f.write(cleaned)
                fixed += 1
                print(f"  PARTIAL {name}: had {sorted(pronouns_before)}, now {sorted(pronouns_after)}")
            else:
                already_clean += 1

    print()
    print("=" * 70)
    print(f"RESULTS: {fixed} fixed, {already_clean} already clean, {skipped} skipped")
    print("=" * 70)


if __name__ == '__main__':
    main()
