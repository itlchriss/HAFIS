#!/bin/bash
# HAFIS Compiler Build and Test Script
# This script builds the compiler with both JML and Dafny backends
# and tests with sample .mr files

set -e

echo "========================================"
echo "HAFIS Compiler Build and Test Script"
echo "========================================"

# Clean previous builds
echo ""
echo "Step 1: Cleaning previous builds..."
make clean 2>/dev/null || true

# Build with JML backend (default)
echo ""
echo "Step 2: Building with JML backend..."
make BACKEND=jml
if [ $? -eq 0 ]; then
    echo "JML backend build successful!"
    # Rename the binary
    cp bin/main bin/main_jml
else
    echo "JML backend build failed!"
    exit 1
fi

# Clean and build with Dafny backend
echo ""
echo "Step 3: Building with Dafny backend..."
make clean 2>/dev/null || true
make BACKEND=dafny
if [ $? -eq 0 ]; then
    echo "Dafny backend build successful!"
    # Rename the binary
    cp bin/main bin/main_dafny
else
    echo "Dafny backend build failed!"
    exit 1
fi

# Test with sample .mr files
echo ""
echo "========================================"
echo "Testing with sample .mr files"
echo "========================================"

# Find test .mr files
MR_FILES=$(find test/motivation -name "*.mr" -type f 2>/dev/null | head -5)

if [ -z "$MR_FILES" ]; then
    echo "No .mr files found in test/motivation/"
    echo "Creating a sample test..."
    
    # Create a simple test MR file
    mkdir -p test/sample
    cat > test/sample/test.mr << 'EOF'
-exists x01.(_param_arr_{NN}(x01) & _type_integer_array_{NN}(x01) & exists e02.(_null{JJ}(e02) & (Subj(e02) = x01)))
EOF
    MR_FILES="test/sample/test.mr"
fi

echo ""
echo "Step 4: Testing JML backend..."
for mr_file in $MR_FILES; do
    echo ""
    echo "Testing: $mr_file"
    echo "----------------------------------------"
    echo "Input MR:"
    cat "$mr_file"
    echo ""
    echo "JML Output:"
    ./bin/main_jml -f"$mr_file" -sspecs/si/typed_si.yml,specs/si/std_si_2023.yml -bjml 2>&1 || echo "Failed"
    echo "----------------------------------------"
done

echo ""
echo "Step 5: Testing Dafny backend..."
for mr_file in $MR_FILES; do
    echo ""
    echo "Testing: $mr_file"
    echo "----------------------------------------"
    echo "Input MR:"
    cat "$mr_file"
    echo ""
    echo "Dafny Output:"
    ./bin/main_dafny -f"$mr_file" -sspecs/si/typed_si.yml,specs/si/std_si_dafny.yml -bdafny 2>&1 || echo "Failed"
    echo "----------------------------------------"
done

echo ""
echo "========================================"
echo "Build and Test Complete!"
echo "========================================"
echo ""
echo "Binaries created:"
echo "  - bin/main_jml  (JML backend)"
echo "  - bin/main_dafny (Dafny backend)"
echo ""
echo "Usage:"
echo "  ./bin/main_jml -f<mr_file> -s<si_files> -bjml"
echo "  ./bin/main_dafny -f<mr_file> -s<si_files> -bdafny"
