#!/usr/bin/env python3
"""Script to replace deleteastnodeandedge/deleteastchildren/deleteastchild with consume variants across all source files."""

import os

replacements = [
    # si.c
    {
        'file': 'src/c.2024/si.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
        ]
    },
    # si_analysis.c
    {
        'file': 'src/c.2024/si_analysis.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
        ]
    },
    # synthesis/event.c
    {
        'file': 'src/c.2024/synthesis/event.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
        ]
    },
    # synthesis/share.c
    {
        'file': 'src/c.2024/synthesis/share.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
            ('deleteastchildren(node);', 'consumeastchildren(node);'),
        ]
    },
    # synthesis/preposition.c
    {
        'file': 'src/c.2024/synthesis/preposition.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
            ('deleteastchildren(node);', 'consumeastchildren(node);'),
        ]
    },
    # synthesis/adjective.c
    {
        'file': 'src/c.2024/synthesis/adjective.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
        ]
    },
    # synthesis/to.c
    {
        'file': 'src/c.2024/synthesis/to.c',
        'replacements': [
            ('root = deleteastnodeandedge(node, root);', 'root = consumeastnodeandedge(node, root);'),
        ]
    },
    # parser.y
    {
        'file': 'src/c.2024/parser.y',
        'replacements': [
            ('deleteastnodeandedge(_e->_subtree_root, ast);', 'consumeastnodeandedge(_e->_subtree_root, ast);'),
            ('deleteastchild(ref->node->parent, ref->node);', 'consumeastnode(ref->node);'),
        ]
    },
]

base_dir = os.path.dirname(os.path.abspath(__file__))

for item in replacements:
    filepath = os.path.join(base_dir, item['file'])
    
    if not os.path.exists(filepath):
        print(f'WARNING: File not found: {filepath}')
        continue
    
    with open(filepath, 'r', encoding='utf-8', newline='') as f:
        content = f.read()
    
    original = content
    for old, new in item['replacements']:
        count = content.count(old)
        if count > 0:
            content = content.replace(old, new)
            print(f'  {item["file"]}: replaced {count} occurrence(s) of "{old}"')
        else:
            print(f'  {item["file"]}: pattern not found "{old}"')
    
    if content != original:
        with open(filepath, 'w', encoding='utf-8', newline='') as f:
            f.write(content)
        print(f'  {item["file"]}: SAVED')
    else:
        print(f'  {item["file"]}: No changes needed')

print('\nDone - all replacements applied')
