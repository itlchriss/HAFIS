/*
    Unified Code Generator for HAFIS compiler.
    
    This module provides a single tree walker that generates specifications
    for any backend via dependency injection (backend_ops*).
    
    Design:
    - Single walktree() function handles AST traversal
    - Backend-specific formatting delegated to ops->emit_* functions
    - No hard-coded language syntax in this file
    
    Usage:
    - JML:   output(ast) calls cg_output(ast, &jml_ops)
    - Dafny: dafny_output(ast) calls cg_output(ast, &dafny_ops)
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"
#include "util.h"
#include "cg.h"
#include "alias.h"
#include "error.h"
#include "backend.h"

/* Connective operators are the same for all backends */
static const char *connective_code[] = { "&&", "||", "<==>", "==>" };

/* Current interpretation index (for multiple interpretations per predicate) */
static int cg_pindex = 0;

/* Quantifier variable starts at ASCII 'i' (105) */
static int cg_quantify_variable = 105;

/*
    Print a synthesised node.
    The format string handles negation wrapping.
*/
static void cg_printnode(struct astnode *node, FILE *s, int *haserror) {
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
    Resolve the type name for a quantified variable.
    Returns the type name string from the CST datatype.
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
    Resolve the length string for a quantified variable's datatype.
    Returns NULL for sequences (Dafny uses |x| notation).
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
    Emit quantifier header based on quantifier type and backend.
    Handles ForAll and Exists, with and without length strings.
*/
static void cg_emit_quantifier_header(FILE *s, struct astnode *node, 
                                       const struct backend_ops *ops,
                                       int is_forall) {
    struct cstsymbol *ac = NULL;
    const char *type_name = cg_resolve_type_name(node, &ac);
    const char *length_str = cg_resolve_length_str(node, ac, ops);
    char var = (char)cg_quantify_variable;
    
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
        /* TODO: handle specified or multiple ranges */
        ops->emit_forall_no_range(s, var);
    }
}

/*
    Walk the AST and generate specifications.
    This is the unified tree walker used by all backends.
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
            cg_printnode(node, s, haserror);
            break;
            
        case Quantifier:
            {
                int is_forall = (node->qtype == Quantifier_ForAll);
                
                /* Emit quantifier header (forall/exists with range) */
                cg_emit_quantifier_header(s, node, ops, is_forall);
                
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
    Count maximum interpretations across all nodes.
*/
static int cg_count_max_interpretations(struct astnode *root) {
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
    Main output function - entry point for code generation.
    Called by both JML and Dafny backends.
*/
void cg_output(struct astnode *root, const struct backend_ops *ops) {
    int pmax = cg_count_max_interpretations(root);
    
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
