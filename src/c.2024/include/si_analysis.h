#ifndef SI_ANALYSIS_H
#define SI_ANALYSIS_H

#include "ast.h"
#include "cst.h"

/*
    SI Analysis module.
    Functions for SI analysis, dependency checking, and ordering.
    This module handles the analysis pass that orders predicates
    based on their dependencies before synthesis.
*/

/*
    Search for a visited variable in the visited_variables queue.
    Returns TRUE if found, FALSE otherwise.
*/
int __search_visited_variables__(void *_child_cstptr, void *_input_cstptr);

/*
    Check if a node's dependencies are satisfied.
    Returns:
        0 - dependencies not satisfied, should retry later
        1 - dependencies satisfied, ready for synthesis
        2 - dependencies satisfied but should be resolved last
*/
int satisfy(struct astnode *node, struct queue *visited_variables);

/*
    Check validity of a node's SI queue.
    Throws sinotfound_error if the node has no matching SIs.
*/
void check_validity(struct astnode *node);

/*
    Main SI analysis pass.
    Orders predicates based on their dependencies.
    Handles adverb preprocessing and predicate reordering.
    
    The analysis:
    1. Preprocesses adverbs (RB) by combining them with their target predicates
    2. Orders predicates based on dependency satisfaction
    3. Ensures proper synthesis order for correct SI propagation
*/
void sianalysis();

#endif /* SI_ANALYSIS_H */
