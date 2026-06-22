#!/bin/bash
SI_FILES='specs/si/common/typed_si.yml,specs/si/dafny/types.yml,specs/si/dafny/typed_si.yml,specs/si/dafny/arithmetic.yml,specs/si/dafny/logic.yml,specs/si/dafny/quantifiers.yml,specs/si/dafny/frames.yml,specs/si/dafny/sequences.yml,specs/si/dafny/sets.yml,specs/si/dafny/functions.yml'
./bin/main -b dafny -f qwen3.7/s0001_two_sum/tmp/ann.0.requires.mr -s \ 2>&1
