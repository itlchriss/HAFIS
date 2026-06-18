#!/usr/bin/env python3
"""Remove unused ir_builder_collect_active_children function from ir_builder.c"""

filepath = 'src/c.2024/ir/ir_builder.c'

with open(filepath, 'r', encoding='utf-8', newline='') as f:
    content = f.read()

# Remove the unused function
old = """/*
    Collect all active (non-consumed) children of an AST node into an array.
    Returns the count of active children. The caller must free the array.
*/
static int ir_builder_collect_active_children(struct astnode *parent, 
                                               struct astnode ***out_children) {
    int active_count = countastchildren_active(parent);
    if (active_count == 0) {
        *out_children = NULL;
        return 0;
    }
    
    *out_children = (struct astnode **)malloc(sizeof(struct astnode *) * active_count);
    int idx = 0;
    struct astnodelist *children = parent->children;
    while ((children = children->next) != NULL) {
        if (children->node && children->node->status == AST_ACTIVE) {
            (*out_children)[idx++] = children->node;
        }
    }
    return active_count;
}

"""

if old in content:
    content = content.replace(old, '')
    with open(filepath, 'w', encoding='utf-8', newline='') as f:
        f.write(content)
    print('Removed unused function from ir_builder.c')
else:
    print('Pattern not found')
