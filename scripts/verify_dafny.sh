#!/bin/bash
cd /mnt/e/Post_Phd_work/Software/HAFIS

DAFNY=/mnt/e/Post_Phd_work/Software/dafny/dafny
RESULTS_FILE="dafny_verification_results.csv"

echo "test_case,verification_status,error_count" > "$RESULTS_FILE"

verified=0
failed=0
errors=0

for dfy in test/filtered_dafny/*/Solution.translated.dfy; do
    test_case=$(basename $(dirname "$dfy"))
    
    # Run dafny verify
    output=$($DAFNY verify "$dfy" 2>&1)
    exit_code=$?
    
    # Count verification errors
    error_count=$(echo "$output" | grep -c "Error:")
    
    if [ $exit_code -eq 0 ]; then
        status="verified"
        ((verified++))
    else
        status="failed"
        ((failed++))
    fi
    
    echo "$test_case,$status,$error_count" >> "$RESULTS_FILE"
    echo "$test_case: $status ($error_count errors)"
done

echo ""
echo "=========================================="
echo "DAFNY VERIFICATION SUMMARY"
echo "=========================================="
echo "Total files: $((verified + failed))"
echo "Verified: $verified"
echo "Failed: $failed"
echo "Results saved to: $RESULTS_FILE"
