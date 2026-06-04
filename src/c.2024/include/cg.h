#ifndef CG_H
#define CG_H

#include "ast.h"
#include "backend.h"

/*
    Unified Code Generator Interface.
    
    The tree walker (cg_walktree) and output function (cg_output) are
    backend-agnostic. They accept a backend_ops* for language-specific
    formatting.
    
    For backward compatibility:
    - output() is a wrapper that calls cg_output with jml_ops
    - dafny_output() is a wrapper that calls cg_output with dafny_ops
*/

/* JML output (backward compatible) */
void output(struct astnode *);

/* Dafny output (backward compatible) */
void dafny_output(struct astnode *);

/* Unified code generation with explicit backend */
void cg_output(struct astnode *root, const struct backend_ops *ops);

#endif
