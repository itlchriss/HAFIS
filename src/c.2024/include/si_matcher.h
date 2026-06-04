#ifndef SI_MATCHER_H
#define SI_MATCHER_H

#include "ast.h"

/*
    SI Matcher module.
    Functions for matching semantic interpretations with AST nodes
    and other SI-related matching operations.
*/

/*
    Search for a ptbsyntax in the SI's syntax queue.
    Returns TRUE if found, FALSE otherwise.
*/
int search_syntax(struct si* si, enum ptbsyntax ptb);

/*
    Main SI matcher function.
    Matches an SI with an AST node based on symbol, syntax, and argument count.
    Used in queue search operations.
*/
int __simatcher(void *_si, void *_astnode);

/*
    Event SI matcher.
    Specifically for event predicates that have one child (event variable).
    Matches based on the number of entities in the event.
*/
int __eventsimatcher(void *_si, void *_astnode);

/*
    Check if an SI symbol is duplicated.
    Used in queue search to find existing SI by symbol.
*/
int __sisymbol_duplicated(void *_si, void *_symbol);

/*
    Match SI by interpretation and get type.
    Matches SI by comparing symbol with a string.
*/
int __match_interpretation_and_get_type(void *_si, void *_s);

/*
    Match an SI with 2-argument datatype.
    Both arguments are matched without using event.
*/
int __match_si_with_2_arg_datatype__(void *_si, void *_astnode);

/*
    Match if two nodes have the same first child variable.
    Used for adverb-predicate matching.
*/
int __match_same_child_variable__(void *_node, void *_inputnode);

#endif /* SI_MATCHER_H */
