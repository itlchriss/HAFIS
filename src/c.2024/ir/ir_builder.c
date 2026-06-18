/*
    IR Builder for HAFIS compiler.
    
    This module builds the Intermediate Representation (IR) from the AST
    after semantic synthesis. The IR is backend-agnostic and serves as
    the input to code generation.
    
    Key design: Non-destructive AST traversal
    - The original parse AST is preserved intact (nodes are marked as
      AST_CONSUMED instead of being deleted)
    - The IR builder skips consumed nodes and applies simplification
      logic during IR construction
    - This preserves the original tree structure for debugging, allowing
      inspection of which children were assigned during synthesis
    
    Simplification rules applied during IR construction:
    - If a Connective has 0 active children -> skip it (return NULL)
    - If a Connective has 1 active child -> promote the child (propagate negation)
    - If an Exists Quantifier has 1 active child -> promote it
    - If a Synthesised node is the only active child -> promote it
    
    Pipeline: AST (after synthesis, with consumed marks) -> IR Builder -> IR -> Code Generator
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ir.h"
#include "ast.h"
#include "alias.h"
#include "error.h"

/* Counter for generating unique quantifier variable names */
static int ir_builder_var_counter = 0;

/* Reset the variable counter (call before building a new IR) */
static void ir_builder_reset(void) {
    ir_builder_var_counter = 0;
}

/* Generate a unique variable name (i, j, k, ..., i0, i1, ...) */
static char ir_builder_next_var(void) {
    char var;
    if (ir_builder_var_counter < 3) {
        /* Use i, j, k for first three variables */
        var = 'i' + ir_builder_var_counter;
    } else {
        /* After k, use i0, i1, etc. (just use single char for simplicity) */
        var = 'a' + (ir_builder_var_counter % 26);
    }
    ir_builder_var_counter++;
    return var;
}

/*
    Resolve the type name for a quantified variable from the CST.
    Returns a strdup'd string that must be freed by the caller.
*/
static char *ir_builder_resolve_type_name(struct cstsymbol *cst_ptr) {
    if (!cst_ptr || !cst_ptr->datatype) {
        return NULL;
    }
    
    /* Check if this is an argument to a predicate (use type directly) */
    if (cst_ptr->is_argument_to_predicate) {
        if (cst_ptr->datatype->types && cst_ptr->datatype->types->count > 0) {
            return strdup((char *)gqueue(cst_ptr->datatype->types, 0));
        }
    }
    
    /* Check for alias */
    struct cstsymbol *ac = searchalias(cst_ptr);
    if (ac && ac->datatype && ac->datatype->types && ac->datatype->types->count > 0) {
        return strdup((char *)gqueue(ac->datatype->types, 0));
    }
    
    /* Use the datatype directly */
    if (cst_ptr->datatype->types && cst_ptr->datatype->types->count > 0) {
        return strdup((char *)gqueue(cst_ptr->datatype->types, 0));
    }
    
    return NULL;
}

/*
    Resolve the reference datatype for a CST symbol.
    This determines how length/access is represented.
*/
static enum reference_datatype ir_builder_resolve_ref_type(struct cstsymbol *cst_ptr) {
    if (!cst_ptr || !cst_ptr->datatype) {
        return Object;  /* Default */
    }
    
    /* Check for relative datatype (wrapped type) */
    if (cst_ptr->datatype->relative_datatype) {
        return cst_ptr->datatype->relative_datatype->r;
    }
    
    return cst_ptr->datatype->r;
}

/*
    Check if a reference type should use cardinality notation (|x|).
    This is for Dafny sequences (List type).
*/
static int ir_builder_uses_cardinality(enum reference_datatype r) {
    return (r == List) ? 1 : 0;
}

/* Forward declaration for the main builder function */
struct ir_node *ir_builder_build_from_ast(struct astnode *ast_node);

/*
    Build IR for a quantifier node.
    Handles simplification: if the quantifier body results in a single
    child after skipping consumed nodes, the quantifier is preserved
    (quantifiers always wrap their body).
*/
static struct ir_node *ir_builder_build_quantifier(struct astnode *ast_node) {
    if (!ast_node || ast_node->type != Quantifier) {
        return NULL;
    }
    
    /* Determine quantifier type */
    enum ir_quantifier_type qtype;
    if (ast_node->qtype == Quantifier_ForAll) {
        qtype = IR_FORALL;
    } else {
        qtype = IR_EXISTS;
    }
    
    /* Get the quantified variable info from the CST */
    struct cstsymbol *cst_ptr = ast_node->cstptr;
    char var_name = ir_builder_next_var();
    char *type_name = ir_builder_resolve_type_name(cst_ptr);
    enum reference_datatype ref_type = ir_builder_resolve_ref_type(cst_ptr);
    
    /* Create quantified variable info */
    struct ir_quantified_var *qvar = ir_qvar_new(var_name, type_name, ref_type);
    qvar->use_cardinality = ir_builder_uses_cardinality(ref_type);
    
    /* Free type_name as ir_qvar_new makes a copy */
    if (type_name) free(type_name);
    
    /* Build the body from active children */
    struct ir_node *body = NULL;
    int active_count = countastchildren_active(ast_node);
    
    if (active_count == 0) {
        /* Quantifier with no active body - skip it entirely */
        return NULL;
    } else if (active_count == 1) {
        /* Single active child - build IR for it, apply negation propagation */
        struct astnode *child = getastchild_active(ast_node, 0);
        body = ir_builder_build_from_ast(child);
        
        /* For Exists with single child, promote the child with negation propagation */
        if (qtype == IR_EXISTS && body) {
            if (ast_node->isnegative) {
                body->is_negative ^= 1;
            }
            return body;
        }
    } else {
        /* Multiple active children - build IR for first child as body */
        /* (Typically quantifiers have one body child, which may be a connective) */
        struct astnode *child = getastchild_active(ast_node, 0);
        body = ir_builder_build_from_ast(child);
    }
    
    /* Create the quantifier IR node */
    struct ir_node *ir_node = ir_quantifier_new(qtype, qvar, body);
    ir_node->is_negative = ast_node->isnegative;
    
    return ir_node;
}

/*
    Build IR for a connective node.
    Handles simplification:
    - 0 active children -> skip (return NULL)
    - 1 active child -> promote it (propagate negation)
    - 2+ active children -> build normally (left/right for binary)
*/
static struct ir_node *ir_builder_build_connective(struct astnode *ast_node) {
    if (!ast_node || ast_node->type != Connective) {
        return NULL;
    }
    
    int active_count = countastchildren_active(ast_node);
    
    /* Simplification: 0 active children -> skip this connective */
    if (active_count == 0) {
        return NULL;
    }
    
    /* Simplification: 1 active child -> promote it with negation propagation */
    if (active_count == 1) {
        struct astnode *child = getastchild_active(ast_node, 0);
        struct ir_node *ir_child = ir_builder_build_from_ast(child);
        if (ir_child && ast_node->isnegative) {
            ir_child->is_negative ^= 1;
        }
        return ir_child;
    }
    
    /* Map AST connective type to IR connective type */
    enum ir_connective_type ctype;
    switch (ast_node->conntype) {
        case Op_And:
            ctype = IR_AND;
            break;
        case Op_Or:
            ctype = IR_OR;
            break;
        case Op_Equivalent:
            ctype = IR_EQUIVALENT;
            break;
        case Op_Imply:
            ctype = IR_IMPLY;
            break;
        default:
            ctype = IR_AND;  /* Default */
            break;
    }
    
    /* Build left and right from active children */
    struct ir_node *left = NULL;
    struct ir_node *right = NULL;
    
    if (active_count >= 2) {
        left = ir_builder_build_from_ast(getastchild_active(ast_node, 0));
        right = ir_builder_build_from_ast(getastchild_active(ast_node, 1));
    }
    
    /* Create the connective IR node */
    struct ir_node *ir_node = ir_connective_new(ctype, left, right);
    ir_node->is_negative = ast_node->isnegative;
    
    return ir_node;
}

/*
    Build IR for a synthesised (predicate) node.
*/
static struct ir_node *ir_builder_build_synthesised(struct astnode *ast_node) {
    if (!ast_node || ast_node->type != Synthesised) {
        return NULL;
    }
    
    /* Get the synthesized expression from the SI queue */
    char *expression = NULL;
    if (ast_node->si_q && ast_node->si_q->count > 0) {
        /* Get the first interpretation by default */
        char *si_expr = (char *)gqueue(ast_node->si_q, 0);
        if (si_expr && !ssearch(si_expr, "__REL__")) {
            expression = si_expr;
        }
    }
    
    /* Create the predicate IR node */
    struct ir_node *ir_node = ir_predicate_new(expression, ast_node->cstptr, ast_node->si_q);
    ir_node->is_negative = ast_node->isnegative;
    ir_node->max_interpretations = ast_node->si_q ? ast_node->si_q->count : 1;
    
    return ir_node;
}

/*
    Build IR from AST node.
    This is the main entry point for IR construction.
    
    Key behavior:
    - Skips consumed nodes (AST_CONSUMED) entirely
    - Applies simplification (promoting single children, removing empty connectives)
    - Propagates negation when promoting children
    - The original parse AST remains intact for debugging
*/
struct ir_node *ir_builder_build_from_ast(struct astnode *ast_node) {
    if (!ast_node) {
        return NULL;
    }
    
    /* Skip consumed nodes - they are logically removed from the tree */
    if (ast_node->status == AST_CONSUMED) {
        /*
            A consumed node should not appear in the IR.
            However, if this consumed node was the root, we need to find
            the first active descendant. This case is handled by the
            top-level ir_build_from_ast() entry point.
        */
        return NULL;
    }
    
    switch (ast_node->type) {
        case Quantifier:
            return ir_builder_build_quantifier(ast_node);
            
        case Connective:
            return ir_builder_build_connective(ast_node);
            
        case Synthesised:
            return ir_builder_build_synthesised(ast_node);
            
        case Predicate:
            /* Predicate nodes are typically placeholders */
            /* They should have been resolved during synthesis */
            return NULL;
            
        case Variable:
            /* Variable nodes reference quantified variables */
            if (ast_node->token && ast_node->token->symbol) {
                return ir_variable_new(ast_node->token->symbol, ast_node->cstptr);
            }
            return NULL;
            
        default:
            /* Other node types are not directly mapped to IR */
            #if IRDEBUG
            fprintf(stderr, "IR Builder: Unhandled AST node type %d\n", ast_node->type);
            #endif
            return NULL;
    }
}

/*
    Build IR from AST (main entry point).
    Resets internal state and builds the complete IR tree.
    
    If the root node is consumed, walks down to find the first active
    descendant that serves as the logical root.
    
    The original AST is NOT modified - consumed nodes are simply skipped
    during traversal, preserving the full tree for debugging.
*/
struct ir_node *ir_build_from_ast(struct astnode *root) {
    ir_builder_reset();
    
    if (!root) {
        return NULL;
    }
    
    /* Find the logical root (may be different if original root is consumed) */
    struct astnode *logical_root = find_logical_root(root);
    
    if (!logical_root || logical_root->status == AST_CONSUMED) {
        /* Entire tree is consumed - no IR to build */
        return NULL;
    }
    
    return ir_builder_build_from_ast(logical_root);
}
