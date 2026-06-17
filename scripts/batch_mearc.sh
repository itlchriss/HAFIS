#!/bin/bash
#
# Batch MEARC runner for all Dafny test cases
# Runs the full mearc pipeline and collects statistics
#

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
TEST_DIR="$PROJECT_DIR/test/filtered_dafny"
RESULTS_FILE="$PROJECT_DIR/batch_results.csv"

cd "$PROJECT_DIR"

# Initialize results file
echo "test_case,total_annotations,translated,failed,verification_status" > "$RESULTS_FILE"

# Counters
total_tests=0
tests_with_all_translated=0
tests_with_some_translated=0
tests_with_none_translated=0

# Process each test case
for test_dir in "$TEST_DIR"/*/; do
    if [[ ! -d "$test_dir" ]]; then
        continue
    fi
    
    test_name=$(basename "$test_dir")
    solution_file="$test_dir/Solution.dfy"
    
    if [[ ! -f "$solution_file" ]]; then
        echo "Skipping $test_name: No Solution.dfy found"
        continue
    fi
    
    echo "=========================================="
    echo "Processing: $test_name"
    echo "=========================================="
    
    total_tests=$((total_tests + 1))
    
    # Run mearc and capture output
    output=$(./mearc "$solution_file" 2>&1) || true
    
    # Parse the output to get statistics
    total_annotations=$(echo "$output" | grep -oP 'Found \K[0-9]+' || echo "0")
    
    # Count translated and failed from the annotation table
    translated=$(echo "$output" | grep -c "translated" || echo "0")
    failed=$(echo "$output" | grep -c "failed" || echo "0")
    
    # Check if translated file was created
    translated_file="$test_dir/Solution.translated.dfy"
    verification_status="no_output"
    
    if [[ -f "$translated_file" ]]; then
        verification_status="output_generated"
        
        # Try to verify with Dafny if available
        if command -v dafny &> /dev/null; then
            echo "Running Dafny verification..."
            if dafny verify "$translated_file" > /dev/null 2>&1; then
                verification_status="verified"
            else
                verification_status="verification_failed"
            fi
        fi
    fi
    
    # Update counters
    if [[ "$translated" -gt 0 && "$failed" -eq 0 ]]; then
        tests_with_all_translated=$((tests_with_all_translated + 1))
    elif [[ "$translated" -gt 0 ]]; then
        tests_with_some_translated=$((tests_with_some_translated + 1))
    else
        tests_with_none_translated=$((tests_with_none_translated + 1))
    fi
    
    # Write to results
    echo "$test_name,$total_annotations,$translated,$failed,$verification_status" >> "$RESULTS_FILE"
    
    echo "  Total: $total_annotations, Translated: $translated, Failed: $failed"
    echo "  Status: $verification_status"
    echo ""
done

echo ""
echo "=========================================="
echo "BATCH PROCESSING SUMMARY"
echo "=========================================="
echo "Total tests processed: $total_tests"
echo "Tests with all annotations translated: $tests_with_all_translated"
echo "Tests with some annotations translated: $tests_with_some_translated"
echo "Tests with no annotations translated: $tests_with_none_translated"
echo ""
echo "Results saved to: $RESULTS_FILE"
