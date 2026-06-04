/*
    SI Runtime module.
    Functions for runtime SI creation and predicate type checking.
    These functions handle dynamic SI generation during compilation
    and provide predicate classification utilities.
*/

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "si_runtime.h"
#include "si_matcher.h"
#include "util.h"

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

extern struct queue *silist;

/*
    Check if a node represents an event variable.
    Event variables start with 'e' (e.g., e01, e02).
*/
int __is_event_variable__(struct astnode *node) {
    return node->token->symbol[0] == 'e';
}

/*
    Check if a node is a preposition predicate.
    A preposition predicate has IN syntax with 2 children,
    where the first child is an event variable and the second is not.
*/
int __is_preposition_predicate__(struct astnode *node) {
    return (node->syntax == IN && countastchildren(node) == 2) &&
     __is_event_variable__((struct astnode *)getastchild(node, 0)) && !__is_event_variable__((struct astnode *)getastchild(node, 1));
}

/*
    Check if a node is a noun predicate.
    Noun predicates have syntax NN, NNP, NNS, or NNPS.
*/
int __is_noun_predicate__(struct astnode *node) {
    return node->syntax == NN || node->syntax == NNP || node->syntax == NNS || node->syntax == NNPS;
}

/*
    Add a runtime SI to the SI list.
    Creates a new SI with the given term, syntax, and interpretation.
    Returns the newly created SI.
*/
struct si* __add_runtime_si(char *term, enum ptbsyntax syntax, char *interpretation) {
    struct si *new = (struct si*) malloc (sizeof(struct si));
    new->symbol = (char*)strdup(term);
    new->args = initqueue();
    struct si_arg *arg = (struct si_arg *)malloc(sizeof(struct si_arg));
    new->type = SI_INT_TYPE_DIRECT;
    arg->symbol = (char*) strdup("(*)");
    arg->datatype = (struct datatype *)malloc(sizeof(struct datatype));
    arg->datatype->p = AnyPrimitiveType;
    arg->datatype->r = AnyRefType;
    arg->datatype->types = NULL;
    new->synthesised_datatype = (struct datatype *)malloc(sizeof(struct datatype));
    if (syntax == CD) {
        new->synthesised_datatype->p = Integer;
        new->synthesised_datatype->r = UNDEFINED;
        new->synthesised_datatype->i = UNDEFINED;
    } else {
        new->synthesised_datatype->p = AnyPrimitiveType;
        new->synthesised_datatype->r = AnyRefType;
    }    
    new->synthesised_datatype->types = NULL;
    enqueue(new->args, (void*)arg);
    new->interpretation = (char*)strdup(interpretation);
    new->syntax = initqueue();
    enqueue(new->syntax, (void *)syntax);
    enqueue(silist, (void*)new);
    return new;    
}

/*
    Generate a runtime SI for an AST node.
    If the SI already exists (by symbol), returns the existing one.
    Otherwise creates a new runtime SI.
*/
struct si* __generate_runtime_si__(struct astnode *node) {
    struct si *dup = searchqueue(silist, node->token->symbol, __sisymbol_duplicated);
    if (dup == NULL) {
        return __add_runtime_si(node->token->symbol, node->syntax, node->token->symbol);
    } else {
        return dup;
    }
}

/*
    Generate a parameter SI.
    Used when LLM identifies a parameter and we trust the specification
    that there is such a parameter in the context.
*/
void generate_param_si(char *s) {
    __add_runtime_si(s, NN, s);
}

/* 
    20230522 in Portugal
    Check if a non-noun syntax is being used as a noun.
    Words used as meaning like nouns but not tagged as nouns.
    For instance, "A is true", where true is tagged as adjective(JJ).
    In this case, this true has direct semantics as a literal.
    Another point is that, the si must be a symbol from the contextual information.
*/
int __is_nonnounsyntax_using_as_noun__(struct astnode *node, struct si *si) {
    if (countastchildren(node) == 1)
        return 0;
    else 
        return 1;
}

/*
    Check if a node is an event predicate.
    Because the event predicate has only one child, and such event requires 
    multiple synthesised predicates to finish the synthesis, it is a special 
    case to the current practice of si matching, which is designed for usual 
    higher-order logic.
*/
int __is_event_predicate__(struct astnode *node) {
    if (countastchildren(node) > 1) {
        return FALSE;
    } else {
        struct astnode *child = getastchild(node, 0);
        if (child->type != Variable || child->token->symbol[0] != 'e') {
            return FALSE;
        } else {
            return TRUE;
        }
    }
}
