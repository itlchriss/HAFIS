#!/usr/bin/env python3
"""
Preprocess a single NL annotation before NLP pipeline.

Usage:
    python preprocess_annotation.py <text> <type> <output_dir> <annotation_id>

Arguments:
    text: The NL annotation text
    type: 'requires' or 'ensures'
    output_dir: Directory to write preprocessed output
    annotation_id: Unique ID for the annotation (used in filenames)

Output files:
    <output_dir>/preprocessed.<id>.txt     - Preprocessed text for NLP pipeline
    <output_dir>/dynamic_si.<id>.yml       - Dynamic SI entries (if any)
"""

import os
import sys
import yaml

# Add parent directory to path so we can import preprocess modules
sys.path.insert(0, os.path.join(os.path.dirname(__file__), 'src', 'python'))

from preprocess.engine import runengine


class NoAliasDumper(yaml.SafeDumper):
    def ignore_aliases(self, data):
        return True


def main():
    if len(sys.argv) != 5:
        print("Usage: python preprocess_annotation.py <text> <type> <output_dir> <annotation_id>")
        sys.exit(1)
    
    text = sys.argv[1]
    req_type = sys.argv[2]  # 'requires' or 'ensures'
    output_dir = sys.argv[3]
    annotation_id = sys.argv[4]
    
    # Ensure output directory exists
    os.makedirs(output_dir, exist_ok=True)
    
    # Run the preprocessing pipeline
    # This applies: narrowing, repair, context, normalization, expression extraction, SI building
    processed_text, dynamic_si = runengine(text, req_type)
    
    # Write preprocessed text
    preprocessed_path = os.path.join(output_dir, f'preprocessed.{annotation_id}.txt')
    with open(preprocessed_path, 'w', encoding='utf-8') as fp:
        fp.write(processed_text)
    
    # Write dynamic SI if there are any entries
    if dynamic_si:
        si_path = os.path.join(output_dir, f'dynamic_si.{annotation_id}.yml')
        with open(si_path, 'w', encoding='utf-8') as fp:
            yaml.dump(list(dynamic_si.values()), fp, sort_keys=False, allow_unicode=True, Dumper=NoAliasDumper)
    
    # Print preprocessed text to stdout for capture by calling script
    print(processed_text)


if __name__ == "__main__":
    main()
