#ifndef CG_H
#define CG_H

#include "ast.h"
#include "ir.h"
#include "backend.h"

/*
    Unified Code Generator Interface.
    
    The code generator walks the IR (Intermediate Representation) and
    generates backend-specific output via backend_ops*.
    
    Pipeline: AST -> IR Builder -> IR -> Code Generator -> Backend Output
    
    New API (IR-based):
    - cg_output_ir(ir, ops) - Generate output from IR
    
    Legacy API (AST-based, deprecated):
    - cg_output(ast, ops) - Generate output from AST
    - output(ast) - JML output wrapper
    - dafny_output(ast) - Dafny output wrapper
*/

/* =========================================================================
   New IR-based Code Generation API
   ========================================================================= */

/* Generate output from IR tree with specified backend */
void cg_output_ir(struct ir_node *ir_root, const struct backend_ops *ops);

/* Walk IR tree (used internally, exposed for testing) */
void cg_walk_ir(struct ir_node *node, FILE *s, int *haserror,
                const struct backend_ops *ops);

/* =========================================================================
   Legacy AST-based Code Generation API (deprecated)
   ========================================================================= */

/* Generate output from AST with specified backend (deprecated - use cg_output_ir) */
void cg_output(struct astnode *root, const struct backend_ops *ops);

/* Walk AST tree (deprecated - use cg_walk_ir) */
void cg_walktree(struct astnode *node, FILE *s, int *haserror,
                 const struct backend_ops *ops);

/* JML output from AST (backward compatible wrapper) */
void output(struct astnode *root);

/* Dafny output from AST (backward compatible wrapper) */
void dafny_output(struct astnode *root);

#endif /* CG_H */
