/*
    IR Builder for HAFIS compiler.
    
    This module builds the Intermediate Representation (IR) from the AST
    after semantic synthesis. The IR is backend-agnostic and serves as
    the input to code generation.
    
    Pipeline: AST (after synthesis) -> IR Builder -> IR -> Code Generator
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
    
    /* Build the body (first child is typically the body) */
    struct ir_node *body = NULL;
    if (countastchildren(ast_node) > 0) {
        struct astnode *child = getastchild(ast_node, 0);
        body = ir_builder_build_from_ast(child);
    }
    
    /* Create the quantifier IR node */
    struct ir_node *ir_node = ir_quantifier_new(qtype, qvar, body);
    ir_node->is_negative = ast_node->isnegative;
    
    return ir_node;
}

/*
    Build IR for a connective node.
*/
static struct ir_node *ir_builder_build_connective(struct astnode *ast_node) {
    if (!ast_node || ast_node->type != Connective) {
        return NULL;
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
    
    /* Build left and right children */
    struct ir_node *left = NULL;
    struct ir_node *right = NULL;
    
    if (countastchildren(ast_node) >= 2) {
        left = ir_builder_build_from_ast(getastchild(ast_node, 0));
        right = ir_builder_build_from_ast(getastchild(ast_node, 1));
    } else if (countastchildren(ast_node) == 1) {
        /* Unary connective (shouldn't happen normally) */
        left = ir_builder_build_from_ast(getastchild(ast_node, 0));
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
*/
struct ir_node *ir_builder_build_from_ast(struct astnode *ast_node) {
    if (!ast_node) {
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
*/
struct ir_node *ir_build_from_ast(struct astnode *root) {
    ir_builder_reset();
    return ir_builder_build_from_ast(root);
}
