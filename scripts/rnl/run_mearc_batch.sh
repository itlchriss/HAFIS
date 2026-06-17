#!/bin/bash
#
# Run mearc on all verify_nl.dfy files and validate generated contracts.
#
# Usage (from WSL):
#   cd /mnt/e/Post_Phd_work/Software/HAFIS
#   bash scripts/rnl/run_mearc_batch.sh [--verbose]
#
# Prerequisites:
#   1. HAFIS compiler built: ./bin/main (build with: make BACKEND=dafny)
#   2. C&C parser models: ../ccg2lambda/en/candc-1.00/models/config must exist
#   3. Semantic template: ../ccg2lambda/en/semantic_templates_en_event_flat_mearc.yaml
#
# For each problem in qwen3.7/s*/:
#   - Runs mearc on verify_nl.dfy -> produces verify_nl.translated.dfy
#   - The translated file replaces NL annotations with formal Dafny contracts
#
# After running, use compare_contracts.py to validate:
#   python3 scripts/validation/compare_contracts.py --verbose

set -e

# Configuration
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
BASE_DIR="${PROJECT_ROOT}/qwen3.7"
MEARC="${PROJECT_ROOT}/mearc"
VERBOSE=""

# Parse arguments
for arg in "$@"; do
    case "$arg" in
        --verbose|-v) VERBOSE="--verbose" ;;
        *) echo "Unknown option: $arg"; exit 1 ;;
    esac
done

cd "$PROJECT_ROOT"

echo "======================================================================"
echo "Batch MEARC Processing for NL-Annotated Dafny Files"
echo "======================================================================"
echo "Project root: $PROJECT_ROOT"
echo "Base directory: $BASE_DIR"
echo ""

# Check prerequisites
echo "Checking prerequisites..."

if [ ! -f "./bin/main" ]; then
    echo "ERROR: HAFIS compiler not found at ./bin/main"
    echo "  Build with: make BACKEND=dafny"
    exit 1
fi
echo "  [OK] HAFIS compiler: ./bin/main"

if [ ! -f "../ccg2lambda/en/candc-1.00/models/config" ]; then
    echo "ERROR: C&C parser models not found"
    echo "  Expected: ../ccg2lambda/en/candc-1.00/models/config"
    echo "  Download models-1.02.tgz (Google Drive ID: 1LR6h3rX7a4Dq7fV_bc2mEmeYxteSyenH)"
    echo "  Extract to: ../ccg2lambda/en/candc-1.00/"
    exit 1
fi
echo "  [OK] C&C parser models found"

if [ ! -f "../ccg2lambda/en/semantic_templates_en_event_flat_mearc.yaml" ]; then
    echo "ERROR: Semantic template not found"
    echo "  Expected: ../ccg2lambda/en/semantic_templates_en_event_flat_mearc.yaml"
    exit 1
fi
echo "  [OK] Semantic template found"

echo ""

# Count problems
PROBLEM_DIRS=$(find "$BASE_DIR" -maxdepth 1 -type d -name 's*' | sort)
TOTAL=$(echo "$PROBLEM_DIRS" | wc -l)
echo "Processing $TOTAL problem folders..."
echo ""

# Counters
SUCCESS=0
FAILED=0
SKIPPED=0
FAILED_LIST=""

for problem_dir in $PROBLEM_DIRS; do
    problem_name=$(basename "$problem_dir")
    nl_dafny="${problem_dir}/verify_nl.dfy"
    translated="${problem_dir}/verify_nl.translated.dfy"

    # Check if verify_nl.dfy exists
    if [ ! -f "$nl_dafny" ]; then
        echo "  SKIP $problem_name: no verify_nl.dfy"
        SKIPPED=$((SKIPPED + 1))
        continue
    fi

    # Check if it has NL annotations
    if ! grep -q '// requires(\*\|// ensures(\*' "$nl_dafny" 2>/dev/null; then
        echo "  SKIP $problem_name: no NL annotations (already translated?)"
        SKIPPED=$((SKIPPED + 1))
        continue
    fi

    # Clean tmp directory for this problem
    rm -rf "${problem_dir}/tmp" 2>/dev/null || true

    # Run mearc
    if [ -n "$VERBOSE" ]; then
        echo "  --- $problem_name ---"
    fi

    if bash "$MEARC" "$nl_dafny" > "${problem_dir}/tmp/mearc.log" 2>&1; then
        if [ -f "$translated" ]; then
            SUCCESS=$((SUCCESS + 1))
            if [ -n "$VERBOSE" ]; then
                echo "  OK $problem_name -> verify_nl.translated.dfy"
            fi
        else
            FAILED=$((FAILED + 1))
            FAILED_LIST="${FAILED_LIST} ${problem_name}(no_output)"
            if [ -n "$VERBOSE" ]; then
                echo "  FAIL $problem_name: mearc succeeded but no output file"
            fi
        fi
    else
        FAILED=$((FAILED + 1))
        FAILED_LIST="${FAILED_LIST} ${problem_name}"
        if [ -n "$VERBOSE" ]; then
            echo "  FAIL $problem_name: mearc returned error"
            if [ -f "${problem_dir}/tmp/mearc.log" ]; then
                tail -5 "${problem_dir}/tmp/mearc.log" | sed 's/^/    /'
            fi
        fi
    fi

    # Print progress every 10 problems
    processed=$((SUCCESS + FAILED + SKIPPED))
    if [ $((processed % 10)) -eq 0 ] && [ -z "$VERBOSE" ]; then
        echo "  Progress: $processed/$TOTAL (ok=$SUCCESS, fail=$FAILED, skip=$SKIPPED)"
    fi
done

echo ""
echo "======================================================================"
echo "MEARC BATCH RESULTS"
echo "======================================================================"
echo "  Total:     $TOTAL"
echo "  Success:   $SUCCESS"
echo "  Failed:    $FAILED"
echo "  Skipped:   $SKIPPED"

if [ -n "$FAILED_LIST" ]; then
    echo ""
    echo "  Failed problems:$FAILED_LIST"
fi

echo ""

# Run contract comparison if any succeeded
if [ $SUCCESS -gt 0 ]; then
    echo "Running contract comparison..."
    echo ""
    python3 "${PROJECT_ROOT}/scripts/validation/compare_contracts.py" $VERBOSE
else
    echo "No successful translations - skipping contract comparison."
fi

echo "======================================================================"
