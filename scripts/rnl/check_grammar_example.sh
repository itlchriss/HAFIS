#!/bin/bash
#
# check_grammar_example.sh - Verify that an RNL example sentence can be
# derived into a Meaning Representation (MR) using the ccg2lambda pipeline.
#
# Usage (from WSL, run from HAFIS project root):
#   bash scripts/rnl/check_grammar_example.sh "Your RNL sentence here."
#
# Example:
#   bash scripts/rnl/check_grammar_example.sh \
#     "There exist a non-negative integer \`i\` and a non-negative integer \`j\` such that \`i\` is not equal to \`j\` and the sum of the value at index \`i\` of the integer array parameter \`numbers\` and the value at index \`j\` of the integer array parameter \`numbers\` is equal to the integer parameter \`target\`."
#
# The script runs:
#   1. Tokenization    (tokenizer.sed)
#   2. C&C parsing     (candc)
#   3. CCG conversion  (candc2transccg_wsc.py)
#   4. Semantic parsing (semparse_wsc.py) -> .mr output
#
# Exit codes:
#   0 - MR successfully derived (non-empty .mr produced)
#   1 - Pipeline failed at some stage
#

set -e

# ============================================================================
# Configuration
# ============================================================================
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
NLP="$PROJECT_ROOT/../NLP/ccg2lambda"
VENV="$PROJECT_ROOT/../venv/mearc"
TMP_DIR="/tmp/rnl_grammar_check_$$"

# ============================================================================
# Argument handling
# ============================================================================
if [[ $# -lt 1 ]]; then
    echo "Usage: bash $(basename "$0") \"<RNL sentence>\""
    echo ""
    echo "Example:"
    echo "  bash $(basename "$0") \"The integer parameter \`n\` is greater than or equal to 0.\""
    exit 1
fi

SENTENCE="$1"

# ============================================================================
# Activate virtual environment
# ============================================================================
if [[ -f "$VENV/bin/activate" ]]; then
    source "$VENV/bin/activate"
    PYCMD=python
else
    PYCMD=python3
fi

# ============================================================================
# Validate NLP tools
# ============================================================================
if [[ ! -f "$NLP/candc-1.00/bin/candc" ]]; then
    echo "[ERROR] C&C parser not found at: $NLP/candc-1.00/bin/candc"
    exit 1
fi

if [[ ! -f "$NLP/en/tokenizer.sed" ]]; then
    echo "[ERROR] tokenizer.sed not found at: $NLP/en/tokenizer.sed"
    exit 1
fi

TEMPLATE="$NLP/en/semantic_templates_en_event_flat_mearc.yaml"
if [[ ! -f "$TEMPLATE" ]]; then
    echo "[ERROR] Semantic template not found at: $TEMPLATE"
    exit 1
fi

# ============================================================================
# Setup temp directory
# ============================================================================
mkdir -p "$TMP_DIR"
trap "rm -rf '$TMP_DIR'" EXIT

echo "============================================================"
echo "RNL Grammar Example MR Derivation Check"
echo "============================================================"
echo "Sentence: $SENTENCE"
echo ""

# ============================================================================
# Step 1: Tokenize
# ============================================================================
echo "[Step 1] Tokenizing..."
printf '%s\n' "$SENTENCE" | sed -f "$NLP/en/tokenizer.sed" > "$TMP_DIR/test.tok"
echo "  Tokenized: $(cat "$TMP_DIR/test.tok")"

# ============================================================================
# Step 2: C&C parsing
# ============================================================================
echo "[Step 2] C&C parsing..."
if ! "$NLP/candc-1.00/bin/candc" \
        --models "$NLP/candc-1.00/models" \
        --candc-printer xml \
        --candc-maxwords 2048 \
        --input "$TMP_DIR/test.tok" \
        > "$TMP_DIR/test.candc.xml" 2>"$TMP_DIR/candc.log"; then
    echo "[FAIL] C&C parsing failed. Log:"
    cat "$TMP_DIR/candc.log"
    exit 1
fi
echo "  C&C parsing: OK"

# ============================================================================
# Step 3: CCG conversion
# ============================================================================
echo "[Step 3] Converting to CCG XML..."
if ! $PYCMD "$NLP/en/candc2transccg_wsc.py" \
        "$TMP_DIR/test.candc.xml" \
        > "$TMP_DIR/test.ccg.xml" 2>"$TMP_DIR/ccg_convert.log"; then
    echo "[FAIL] CCG conversion failed. Log:"
    cat "$TMP_DIR/ccg_convert.log"
    exit 1
fi
echo "  CCG conversion: OK"

# ============================================================================
# Step 4: Semantic parsing -> MR
# ============================================================================
echo "[Step 4] Semantic parsing (ccg2lambda -> MR)..."
if ! $PYCMD "$NLP/scripts/semparse_wsc.py" \
        "$TMP_DIR/test.ccg.xml" \
        "$TEMPLATE" \
        --ncores 1 \
        > "$TMP_DIR/test.mr" 2>"$TMP_DIR/semparse.log"; then
    echo "[FAIL] Semantic parsing failed. Log:"
    cat "$TMP_DIR/semparse.log"
    exit 1
fi

# ============================================================================
# Check MR output
# ============================================================================
echo ""
echo "============================================================"
if [[ -f "$TMP_DIR/test.mr" ]] && grep -qE '[^[:space:]]' "$TMP_DIR/test.mr" 2>/dev/null; then
    echo "[PASS] MR successfully derived:"
    echo ""
    cat "$TMP_DIR/test.mr"
    echo ""
    echo "============================================================"
    exit 0
else
    echo "[FAIL] MR file is empty - sentence could not be parsed into MR."
    echo ""
    if [[ -f "$TMP_DIR/semparse.log" ]] && grep -qE '[^[:space:]]' "$TMP_DIR/semparse.log"; then
        echo "semparse log:"
        cat "$TMP_DIR/semparse.log"
    fi
    echo "============================================================"
    exit 1
fi
