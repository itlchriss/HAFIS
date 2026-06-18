/*
    Unified Code Generator for HAFIS compiler.
    
    This module provides a single IR tree walker that generates specifications
    for any backend via dependency injection (backend_ops*).
    
    Design:
    - Single cg_walk_ir() function handles IR traversal
    - Backend-specific formatting delegated to ops->emit_* functions
    - No hard-coded language syntax in this file
    - IR is backend-agnostic; backends only handle formatting
    
    Pipeline: AST -> IR Builder -> IR -> Code Generator -> Backend Output
    
    Usage:
    - Build IR from AST: ir = ir_build_from_ast(ast)
    - JML:   cg_output_ir(ir, &jml_ops)
    - Dafny: cg_output_ir(ir, &dafny_ops)
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ir.h"
#include "util.h"
#include "cg.h"
#include "alias.h"
#include "error.h"
#include "backend.h"

/* Connective operators are the same for all backends */
static const char *connective_code[] = { "&&", "||", "<==>", "==>" };

/* Current interpretation index (for multiple interpretations per predicate) */
static int cg_pindex = 0;

/* =========================================================================
   IR-based Code Generation
   ========================================================================= */

/*
    Emit quantifier header from IR quantified variable info.
*/
static void cg_ir_emit_quantifier_header(FILE *s, struct ir_quantified_var *qvar,
                                          const struct backend_ops *ops,
                                          int is_forall) {
    if (!qvar) return;
    
    char var = qvar->var_name;
    const char *type_name = qvar->type_name;
    
    /* Check if using cardinality notation (|x|) */
    if (qvar->use_cardinality) {
        /* Sequence: use |x| notation */
        if (is_forall) {
            if (ops->emit_forall_seq) {
                ops->emit_forall_seq(s, var, type_name);
            } else {
                ops->emit_forall_no_range(s, var);
            }
        } else {
            if (ops->emit_exists_seq) {
                ops->emit_exists_seq(s, var, type_name);
            } else {
                ops->emit_forall_no_range(s, var);
            }
        }
        return;
    }
    
    /* Get length string from backend */
    const char *length_str = ops->get_length_str(qvar->ref_type);
    
    if (length_str != NULL) {
        if (is_forall) {
            ops->emit_forall(s, var, type_name, length_str);
        } else {
            ops->emit_exists(s, var, type_name, length_str);
        }
    } else {
        /* No length string - use no range version */
        ops->emit_forall_no_range(s, var);
    }
}

/*
    Print an IR predicate node.
    Handles negation wrapping.
*/
static void cg_ir_print_predicate(struct ir_node *node, FILE *s, int *haserror) {
    const char *formatstr = "%s";
    if (node->is_negative) {
        formatstr = "!(%s)";
    }
    
    if (node->type != IR_PREDICATE) {
        (*haserror)++;
        return;
    }
    
    /* Get the expression for the current interpretation index */
    char *expr = NULL;
    if (node->data.predicate.si_queue && 
        node->data.predicate.si_queue->count > cg_pindex) {
        expr = (char *)gqueue(node->data.predicate.si_queue, cg_pindex);
    }
    
    if (expr && !ssearch(expr, "__REL__")) {
        fprintf(s, formatstr, expr);
    } else {
        /* Try the stored expression */
        if (node->data.predicate.expression && !ssearch(node->data.predicate.expression, "__REL__")) {
            fprintf(s, formatstr, node->data.predicate.expression);
        } else {
            semantic_error(
                "Predicate TSI not found for interpretation",
                "IR Predicate");
            (*haserror)++;
        }
    }
}

/*
    Walk the IR tree and generate specifications.
    This is the unified IR walker used by all backends.
*/
void cg_walk_ir(struct ir_node *node, FILE *s, int *haserror,
                const struct backend_ops *ops) {
    if (*haserror > 0) {
        return;
    }
    
    if (node == NULL) {
        /* NULL node is not necessarily an error - could be empty body */
        return;
    }
    
    switch (node->type) {
        case IR_CONNECTIVE:
            fprintf(s, "(");
            cg_walk_ir(node->data.connective.left, s, haserror, ops);
            fprintf(s, ")");
            fprintf(s, " %s ", connective_code[node->data.connective.ctype]);
            fprintf(s, "(");
            cg_walk_ir(node->data.connective.right, s, haserror, ops);
            fprintf(s, ")");
            break;
            
        case IR_PREDICATE:
            cg_ir_print_predicate(node, s, haserror);
            break;
            
        case IR_QUANTIFIER:
            {
                int is_forall = (node->data.quantifier.qtype == IR_FORALL);
                
                /* Emit quantifier header */
                cg_ir_emit_quantifier_header(s, node->data.quantifier.qvar, ops, is_forall);
                
                /* Emit quantifier body */
                fprintf(s, "(");
                cg_walk_ir(node->data.quantifier.body, s, haserror, ops);
                fprintf(s, ")");
            }
            break;
            
        case IR_VARIABLE:
            /* Variables are typically handled within predicates */
            if (node->data.variable.name) {
                fprintf(s, "%s", node->data.variable.name);
            }
            break;
            
        default:
            #if CGDEBUG
            fprintf(stderr, "cg_walk_ir: Unknown IR node type %d\n", node->type);
            #endif
            (*haserror)++;
            break;
    }
}

/*
    Main IR output function - entry point for code generation from IR.
*/
void cg_output_ir(struct ir_node *ir_root, const struct backend_ops *ops) {
    if (!ir_root || !ops) {
        fprintf(stderr, "cg_output_ir: NULL IR or backend ops\n");
        return;
    }
    
    int pmax = ir_count_max_interpretations(ir_root);
    
    fprintf(stdout, "pcount:%d\n", pmax);
    
    for (cg_pindex = 0; cg_pindex < pmax; ++cg_pindex) {
        char *buffer;
        size_t size;
        FILE *stream = open_memstream(&buffer, &size);
        
        int haserror = 0;
        cg_walk_ir(ir_root, stream, &haserror, ops);
        
        if (haserror == 0) {
            fflush(stream);
            printf("%s\n", buffer);
        } else {
            fprintf(stderr, "%s generation failed\n", ops->name);
        }
        
        fclose(stream);
    }
}

/* =========================================================================
   Legacy AST-based Code Generation (for backward compatibility)
   ========================================================================= */

/*
    Resolve the type name for a quantified variable from AST.
*/
static const char *cg_resolve_type_name(struct astnode *node, struct cstsymbol **out_alias) {
    struct cstsymbol *ac = NULL;
    const char *type_name = NULL;
    
    if (node->cstptr->is_argument_to_predicate) {
        type_name = (const char *)gqueue(node->cstptr->datatype->types, 0);
    } else {
        ac = searchalias(node->cstptr);
        if (ac != NULL) {
            type_name = (const char *)gqueue(ac->datatype->types, 0);
        } else {
            type_name = (const char *)gqueue(node->cstptr->datatype->types, 0);
        }
    }
    
    if (out_alias) *out_alias = ac;
    return type_name;
}

/*
    Resolve the length string for a quantified variable's datatype from AST.
*/
static const char *cg_resolve_length_str(struct astnode *node, struct cstsymbol *ac, 
                                          const struct backend_ops *ops) {
    const char *length_str = NULL;
    
    if (!ac) {
        if (node->cstptr->datatype->relative_datatype) {
            length_str = ops->get_length_str(node->cstptr->datatype->relative_datatype->r);
        } else {
            length_str = ops->get_length_str(node->cstptr->datatype->r);
        }
    } else {
        if (ac->datatype->relative_datatype) {
            length_str = ops->get_length_str(ac->datatype->relative_datatype->r);
        } else {
            length_str = ops->get_length_str(ac->datatype->r);
        }
    }
    
    return length_str;
}

/*
    Emit quantifier header from AST node (legacy).
*/
static void cg_emit_quantifier_header_ast(FILE *s, struct astnode *node, 
                                           const struct backend_ops *ops,
                                           int is_forall) {
    struct cstsymbol *ac = NULL;
    const char *type_name = cg_resolve_type_name(node, &ac);
    const char *length_str = cg_resolve_length_str(node, ac, ops);
    char var = (char)('i' + cg_pindex);  /* Simple variable naming */
    
    if (node->quantified_ranges->count == 0) {
        if (length_str != NULL) {
            if (is_forall) {
                ops->emit_forall(s, var, type_name, length_str);
            } else {
                ops->emit_exists(s, var, type_name, length_str);
            }
        } else {
            /* Sequence: use |x| notation (Dafny) */
            if (is_forall) {
                if (ops->emit_forall_seq) {
                    ops->emit_forall_seq(s, var, type_name);
                } else {
                    ops->emit_forall_no_range(s, var);
                }
            } else {
                if (ops->emit_exists_seq) {
                    ops->emit_exists_seq(s, var, type_name);
                } else {
                    ops->emit_forall_no_range(s, var);
                }
            }
        }
    } else {
        ops->emit_forall_no_range(s, var);
    }
}

/*
    Print a synthesised AST node (legacy).
*/
static void cg_printnode_ast(struct astnode *node, FILE *s, int *haserror) {
    const char *formatstr = "%s";
    if (node->isnegative == 1) {
        formatstr = "!(%s)";
    }
    
    switch (node->type) {
        case Synthesised:
            if (node->si_q && 
                !ssearch((char *)gqueue(node->si_q, cg_pindex), "__REL__") && 
                gqueue(node->si_q, cg_pindex) != NULL) {
                fprintf(s, formatstr, (char *)gqueue(node->si_q, cg_pindex));
            } else {
                semantic_error(
                    "There are at least 1 predicate TSI not found, however, "
                    "there are other elements resolved. please check.", 
                    "Relative TSI");
                (*haserror)++;
            }
            break;
        default:
            #if CGDEBUG
            fprintf(stderr, "cg_walktree: Unknown type(%d) encountered for symbol(%s).\n",
                    node->type, node->token ? node->token->symbol : "null");
            #endif
            (*haserror)++;
    }
}

/*
    Walk the AST and generate specifications (legacy).
    DEPRECATED: Use cg_walk_ir with IR instead.
*/
void cg_walktree(struct astnode *node, FILE *s, int *haserror, 
                 const struct backend_ops *ops) {
    if (*haserror > 0) {
        return;
    }
    
    if (node == NULL) {
        (*haserror)++;
        return;
    }
    
    switch (node->type) {
        case Connective:
            fprintf(s, "(");
            cg_walktree((struct astnode *)getastchild(node, 0), s, haserror, ops);
            fprintf(s, ")");
            fprintf(s, " %s ", connective_code[node->conntype]);
            fprintf(s, "(");
            cg_walktree((struct astnode *)getastchild(node, 1), s, haserror, ops);
            fprintf(s, ")");
            break;
            
        case Synthesised:
            cg_printnode_ast(node, s, haserror);
            break;
            
        case Quantifier:
            {
                int is_forall = (node->qtype == Quantifier_ForAll);
                
                /* Emit quantifier header */
                cg_emit_quantifier_header_ast(s, node, ops, is_forall);
                
                /* Emit quantifier body */
                fprintf(s, "(");
                for (int i = 0; i < countastchildren(node); ++i) {
                    cg_walktree((struct astnode *)getastchild(node, 0), s, haserror, ops);
                    if (i == 1 && countastchildren(node) == 2) {
                        fprintf(s, " && ");
                    }
                }
                fprintf(s, ")");
            }
            break;
            
        default:
            (*haserror)++;
            break;
    }
}

/*
    Count maximum interpretations across all AST nodes.
*/
static int cg_count_max_interpretations_ast(struct astnode *root) {
    int pmax = 1;
    struct queue *p = initqueue();
    enqueue(p, (void *)root);
    
    while (!isempty(p)) {
        struct astnode *tmp = dequeue(p);
        if (tmp->si_q != NULL && tmp->si_q->count > 1) {
            pmax = tmp->si_q->count;
        }
        for (int i = 0; i < countastchildren(tmp); ++i) {
            enqueue(p, (void *)getastchild(tmp, i));
        }
    }
    deallocatequeue(p, NULL);
    return pmax;
}

/*
    Legacy AST-based output function.
    DEPRECATED: Use cg_output_ir instead.
*/
void cg_output(struct astnode *root, const struct backend_ops *ops) {
    int pmax = cg_count_max_interpretations_ast(root);
    
    fprintf(stdout, "pcount:%d\n", pmax);
    
    for (cg_pindex = 0; cg_pindex < pmax; ++cg_pindex) {
        char *buffer;
        size_t size;
        FILE *stream = open_memstream(&buffer, &size);
        
        int haserror = 0;
        cg_walktree(root, stream, &haserror, ops);
        
        if (haserror == 0) {
            fflush(stream);
            printf("%s\n", buffer);
        } else {
            fprintf(stderr, "%s generation failed\n", ops->name);
        }
        
        fclose(stream);
    }
}

/*
    JML output function - wrapper for backward compatibility.
*/
void output(struct astnode *root) {
    cg_output(root, &jml_ops);
}

/*
    Dafny output function - wrapper for backward compatibility.
*/
void dafny_output(struct astnode *root) {
    cg_output(root, &dafny_ops);
}
