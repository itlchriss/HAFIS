#!/usr/bin/env python3
"""Script to update main.c - remove astsimplification, update pipeline."""

filepath = 'src/c.2024/main.c'

with open(filepath, 'r', encoding='utf-8', newline='') as f:
    content = f.read()

# Replace the synthesis+simplification block
old_block = """    ast = root;
    #if ASTDEBUG
    showast(ast, 0);
    #endif
    ast = astsimplification(ast);
    ast = astsimplification(ast);
    #if ASTDEBUG
    showast(ast, 0);
    #endif 
    deallocatequeue(silist, deallocatesi);
    
    /* Build IR from AST after synthesis */
    struct ir_node *ir = ir_build_from_ast(ast);"""

new_block = """    ast = root;
    #if ASTDEBUG
    printf("AST after synthesis (with consumed markers):\\n");
    showast_with_status(ast, 0);
    #endif
    /*
        AST simplification is no longer performed destructively on the AST.
        Instead, the IR builder skips consumed nodes and applies simplification
        during IR construction. This preserves the original parse AST for debugging.
    */
    deallocatequeue(silist, deallocatesi);
    
    /* Build IR from AST after synthesis - IR builder handles consumed nodes and simplification */
    struct ir_node *ir = ir_build_from_ast(ast);"""

if old_block in content:
    content = content.replace(old_block, new_block)
    print('Replaced astsimplification block in main.c')
else:
    print('ERROR: Could not find old block in main.c')
    # Try to find partial matches
    if 'astsimplification' in content:
        print('  astsimplification still found in file')
    else:
        print('  astsimplification already removed')

with open(filepath, 'w', encoding='utf-8', newline='') as f:
    f.write(content)
print('Saved main.c')
