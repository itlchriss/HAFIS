#!/bin/bash
cd /mnt/e/Post_Phd_work/Software/HAFIS

# Test more complex cases
echo "=== Test 1: Simple null check (motivation/1) ==="
echo "--- JML ---"
./bin/main -ftest/motivation/1/tmp/pre.0.mr \
    -stest/motivation/1/tmp/method_si.yml,specs/si/typed_si.yml,specs/si/java_datatypes.yml \
    -bjml 2>&1 | grep -v pcount

echo "--- Dafny ---"
./bin/main -ftest/motivation/1/tmp/pre.0.mr \
    -stest/motivation/1/tmp/method_si.yml,specs/si/typed_si.yml,specs/si/java_datatypes.yml \
    -bdafny 2>&1 | grep -v pcount

echo ""
echo "=== Test 2: Range check (motivation/4) ==="
echo "--- JML ---"
./bin/main -ftest/motivation/4/tmp/pre.0.mr \
    -stest/motivation/4/tmp/method_si.yml,specs/si/typed_si.yml,specs/si/java_datatypes.yml \
    -bjml 2>&1 | grep -v pcount

echo "--- Dafny ---"
./bin/main -ftest/motivation/4/tmp/pre.0.mr \
    -stest/motivation/4/tmp/method_si.yml,specs/si/typed_si.yml,specs/si/java_datatypes.yml \
    -bdafny 2>&1 | grep -v pcount

echo ""
echo "=== Test 3: Sorted array (patterns/s0026) ==="
echo "--- JML ---"
./bin/main -ftest/patterns/s0026_remove_duplicates_from_sorted_array/hafis/o3/tmp/pre.0.mr \
    -stest/patterns/s0026_remove_duplicates_from_sorted_array/hafis/o3/tmp/method_si.yml,specs/si/typed_si.yml,specs/si/java_datatypes.yml \
    -bjml 2>&1 | grep -v pcount

echo "--- Dafny ---"
./bin/main -ftest/patterns/s0026_remove_duplicates_from_sorted_array/hafis/o3/tmp/pre.0.mr \
    -stest/patterns/s0026_remove_duplicates_from_sorted_array/hafis/o3/tmp/method_si.yml,specs/si/typed_si.yml,specs/si/java_datatypes.yml \
    -bdafny 2>&1 | grep -v pcount
