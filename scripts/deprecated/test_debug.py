#!/usr/bin/env python3
"""Quick debug test for java_to_dafny conversion."""
import sys
sys.path.insert(0, 'scripts')
from java_to_dafny import convert_java_to_dafny

# Read and convert the s0007 file
with open('test/patterns/s0007_reverse_integer/Solution.java', 'r', encoding='utf-8') as f:
    java = f.read()

dafny = convert_java_to_dafny(java)
print(dafny)
print('---END---')