#!/bin/bash
# Test script for HAFIS compiler
SI_FILES="specs/si/common/typed_si.yml,specs/si/dafny/types.yml,specs/si/dafny/typed_si.yml,specs/si/dafny/arithmetic.yml,specs/si/dafny/logic.yml,specs/si/dafny/quantifiers.yml,specs/si/dafny/frames.yml,specs/si/dafny/sequences.yml,specs/si/dafny/sets.yml,specs/si/dafny/functions.yml"

ANN=$1
if [ -z "$ANN" ]; then
    echo "Usage: $0 <annotation_number>"
    exit 1
fi

MR_FILE="qwen3.7/s0001_two_sum/tmp/ann.${ANN}.requires.mr"
if [ ! -f "$MR_FILE" ]; then
    MR_FILE="qwen3.7/s0001_two_sum/tmp/ann.${ANN}.ensures.mr"
fi

echo "Testing annotation $ANN: $MR_FILE"
./bin/main -f "$MR_FILE" -s "$SI_FILES" -b dafny
