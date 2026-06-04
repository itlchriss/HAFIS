/*
    SI Matcher module.
    Functions for matching semantic interpretations with AST nodes
    and other SI-related matching operations.
*/

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "si_matcher.h"
#include "event.h"
#include "util.h"

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

extern struct queue *silist, *events;

/*
    Search for a ptbsyntax in the SI's syntax queue.
    Returns TRUE if found, FALSE otherwise.
*/
int search_syntax(struct si* si, enum ptbsyntax ptb) {
    for (int i = 0; i < si->syntax->count; ++i) {
        if ((enum ptbsyntax)gqueue(si->syntax, i) == ptb) {
            return TRUE;
        }
    }
    return FALSE;
}

/*
    Check if an SI symbol is duplicated.
    Used in queue search to find existing SI by symbol.
*/
int __sisymbol_duplicated(void *_si, void *_symbol) {
    char *symbol = (char*)_symbol;
    struct si* si = (struct si*)_si;
    if (strcmp(symbol, si->symbol) == 0) return TRUE;
    else return FALSE;
}

/*
    Match an SI with 2-argument datatype.
    Both arguments are matched without using event.
*/
int __match_si_with_2_arg_datatype__(void *_si, void *_astnode) {
    struct astnode *node = (struct astnode *)_astnode;    
    struct si *si = (struct si *)_si;
    /* a predicate not accepting 2 arguments can be filtered out */
    if (si->args->count != 2) return FALSE;
    struct si_arg *arg1 = (struct si_arg *)gqueue(si->args, 0), *arg2 = (struct si_arg *)gqueue(si->args, 1);
    struct astnode *child1 = (struct astnode *)getastchild(node, 0), *child2 = (struct astnode *)getastchild(node, 1);
    if ((arg1->datatype == child1->cstptr->datatype && arg2->datatype == child2->cstptr->datatype) ||
        (arg1->datatype == child2->cstptr->datatype && arg2->datatype == child1->cstptr->datatype)) return TRUE;
    else
        return FALSE;
}

/*
    Match if two nodes have the same first child variable.
    Used for adverb-predicate matching.
*/
int __match_same_child_variable__(void *_node, void *_inputnode) {
    struct astnode *current = (struct astnode *)_node;
    struct astnode *input = (struct astnode *)_inputnode;

    struct astnode *child1 = getastchild(current, 0), *child2 = getastchild(input, 0);

    if (child1->cstptr == child2->cstptr) 
        return TRUE;
    else
        return FALSE;
}

/*
    Main SI matcher function.
    Matches an SI with an AST node based on symbol, syntax, and argument count.
    Used in queue search operations.

    20230522 in portugal. 
    We want to tackle a problem of using a word tagged as not-a-noun at the end of a sentence, 
    or an declarative sentence, where this word should be a symbol.
    Adding a condition that if there is only one child, it is okay to skip the search_syntax. 
    Because this is to say that this symbol must be a symbol in the contextual information. 
    This can be wrong if there is an SI in the SI library conflicting with the contextual information. 
    TODO: we can put a flag in the SI to distinguish between contextual SI and std SI, such that this will not be a problem.
*/
int __simatcher(void *_si, void *_astnode) {
    struct si* si = (struct si*)_si;
    struct astnode *node = (struct astnode*)_astnode;
    
    int child_count = countastchildren(node);
    if (strcmp(node->token->symbol, si->symbol) == 0 &&
                search_syntax(si, node->syntax) == TRUE && 
                (si->args->count == child_count || 
                    (
                        getastchild(node, 0)->cstptr->symbol[0] == 'e' && 
                        si->args->count == __searchevent(getastchild(node, 0)->cstptr)->entities->count
                    )
                )
        ) 
        return TRUE;
    else
        return FALSE;
}

/* 
    Event SI matcher.
    This is an SI matcher that is specifically for the event predicates.
    Event predicates have only one child that is the event variable, which is not useful 
    in the SI matching with SIs should have specified the number of arguments required for synthesis.
    However, we have recorded the entities that have grammar relationships with this event variable.
    Such that, we can check the number of arguments required for the SI against the number of entities of the event variables.
*/
int __eventsimatcher(void *_si, void *_astnode) {
    struct si* si = (struct si*)_si;
    struct astnode *node = (struct astnode*)_astnode, *child = getastchild(node, 0);
    struct event *event = __searchevent(child->cstptr);    
    if (strcmp(si->symbol, node->token->symbol) == 0 &&
        search_syntax(si, node->syntax) == TRUE &&
        si->args->count == event->entities->count
        ) 
    {
        return TRUE;
    } else {
        return FALSE;
    }
}

/*
    Match SI by interpretation and get type.
    Matches SI by comparing symbol with a string.
*/
int __match_interpretation_and_get_type(void *_si, void *_s) {
    struct si* si = (struct si*)_si;
    char *s = (char*)_s;
    if (strcmp(si->symbol, s) == 0) return TRUE;
    else return FALSE;
}
