#!/bin/bash
#
# batch_check_grammar.sh - Test every 'example' sentence in rnl_grammar.yml
# through the ccg2lambda pipeline and report which produce valid MRs.
#
# A valid MR is: non-empty AND does not contain 'lambda'.
#
# Usage (from WSL, HAFIS project root):
#   bash scripts/rnl/batch_check_grammar.sh
#
# Output: one line per rule:  PASS/FAIL/LAMBDA  RNL-ID  "sentence"

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
GRAMMAR="$PROJECT_ROOT/specs/rnl_grammar.yml"
NLP="$PROJECT_ROOT/../NLP/ccg2lambda"
VENV="$PROJECT_ROOT/../venv/mearc"
TMP_DIR="/tmp/rnl_batch_check_$$"
mkdir -p "$TMP_DIR"
trap "rm -rf '$TMP_DIR'" EXIT

if [[ -f "$VENV/bin/activate" ]]; then
    source "$VENV/bin/activate"
    PYCMD=python
else
    PYCMD=python3
fi

TEMPLATE="$NLP/en/semantic_templates_en_event_flat_mearc.yaml"

# Use Python to drive the full batch run to avoid bash quoting issues with backticks
python3 - "$TMP_DIR" "$NLP" "$TEMPLATE" "$PYCMD" <<'PYEOF'
import sys, re, subprocess, os

tmp_dir, nlp, template, pycmd = sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4]
grammar = "/mnt/e/Post_Phd_work/Software/HAFIS/specs/rnl_grammar.yml"

with open(grammar) as f:
    content = f.read()

blocks = re.split(r'\n(?=- id:)', content)
pairs = []
for block in blocks:
    id_m = re.search(r"id:\s*(RNL-\w+)", block)
    ex_m = re.search(r"example:\s*'([^']+)'", block)
    if id_m and ex_m:
        pairs.append((id_m.group(1), ex_m.group(1)))

total = len(pairs)
print(f"{'='*60}")
print(f"Batch Grammar Example MR Check  ({total} rules)")
print(f"Valid MR = non-empty AND does not contain 'lambda'")
print(f"{'='*60}")
print(f"{'ID':<12} {'STATUS':<8}  SENTENCE (truncated to 80 chars)")
print(f"{'-'*60}")

passed, failed, has_lambda = [], [], []

for rid, sentence in pairs:
    base = os.path.join(tmp_dir, rid)

    def run(cmd, stdin=None, input_text=None):
        try:
            r = subprocess.run(cmd, input=input_text, capture_output=True, text=True, timeout=30)
            return r.returncode == 0, r.stdout, r.stderr
        except Exception:
            return False, "", ""

    # Step 1: tokenize
    tok_file = base + ".tok"
    ok, tok_out, _ = run(["sed", "-f", f"{nlp}/en/tokenizer.sed"], input_text=sentence + "\n")
    with open(tok_file, "w") as f:
        f.write(tok_out)

    # Step 2: candc
    candc_xml = base + ".candc.xml"
    ok, candc_out, _ = run([f"{nlp}/candc-1.00/bin/candc",
        "--models", f"{nlp}/candc-1.00/models",
        "--candc-printer", "xml",
        "--candc-maxwords", "2048",
        "--input", tok_file])
    with open(candc_xml, "w") as f:
        f.write(candc_out)

    # Step 3: candc2transccg
    ccg_xml = base + ".ccg.xml"
    ok, ccg_out, _ = run([pycmd, f"{nlp}/en/candc2transccg_wsc.py", candc_xml])
    with open(ccg_xml, "w") as f:
        f.write(ccg_out)

    # Step 4: semparse
    mr_file = base + ".mr"
    ok, mr_out, _ = run([pycmd, f"{nlp}/scripts/semparse_wsc.py",
        ccg_xml, template, "--ncores", "1"])
    with open(mr_file, "w") as f:
        f.write(mr_out)

    # Evaluate
    mr_stripped = mr_out.strip()
    short = sentence[:80]
    if not mr_stripped:
        status = "FAIL"
        failed.append((rid, sentence))
    elif "lambda" in mr_stripped:
        status = "LAMBDA"
        has_lambda.append((rid, sentence))
    else:
        status = "PASS"
        passed.append(rid)

    print(f"{rid:<12} {status:<8}  {short}")

print(f"{'='*60}")
print(f"SUMMARY:  PASS={len(passed)}  LAMBDA={len(has_lambda)}  FAIL={len(failed)}  TOTAL={total}")
print(f"{'='*60}")
if has_lambda:
    print("\nLAMBDA rules (need rewriting):")
    for rid, s in has_lambda:
        print(f"  {rid}: {s[:100]}")
if failed:
    print("\nFAIL rules (empty MR):")
    for rid, s in failed:
        print(f"  {rid}: {s[:100]}")
PYEOF
