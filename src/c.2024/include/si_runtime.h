#ifndef SI_RUNTIME_H
#define SI_RUNTIME_H

#include "ast.h"
#include "cst.h"

/*
    SI Runtime module.
    Functions for runtime SI creation and predicate type checking.
    These functions handle dynamic SI generation during compilation
    and provide predicate classification utilities.
*/

/*
    Add a runtime SI to the SI list.
    Creates a new SI with the given term, syntax, and interpretation.
    Returns the newly created SI.
*/
struct si* __add_runtime_si(char *term, enum ptbsyntax syntax, char *interpretation);

/*
    Generate a runtime SI for an AST node.
    If the SI already exists (by symbol), returns the existing one.
    Otherwise creates a new runtime SI.
*/
struct si* __generate_runtime_si__(struct astnode *node);

/*
    Generate a parameter SI.
    Used when LLM identifies a parameter and we trust the specification
    that there is such a parameter in the context.
*/
void generate_param_si(char *s);

/*
    Check if a non-noun syntax is being used as a noun.
    20230522 in Portugal:
    Words used as meaning like nouns but not tagged as nouns.
    For instance, "A is true", where true is tagged as adjective(JJ).
    In this case, this true has direct semantics as a literal.
    Another point is that, the si must be a symbol from the contextual information.
*/
int __is_nonnounsyntax_using_as_noun__(struct astnode *node, struct si *si);

/*
    Check if a node represents an event variable.
    Event variables start with 'e' (e.g., e01, e02).
*/
int __is_event_variable__(struct astnode *node);

/*
    Check if a node is a preposition predicate.
    A preposition predicate has IN syntax with 2 children,
    where the first child is an event variable and the second is not.
*/
int __is_preposition_predicate__(struct astnode *node);

/*
    Check if a node is a noun predicate.
    Noun predicates have syntax NN, NNP, NNS, or NNPS.
*/
int __is_noun_predicate__(struct astnode *node);

/*
    Check if a node is an event predicate.
    Event predicates have only one child which is an event variable.
    Such events require multiple synthesised predicates to finish the synthesis,
    therefore it is a special case to the current practice of si matching.
*/
int __is_event_predicate__(struct astnode *node);

#endif /* SI_RUNTIME_H */
