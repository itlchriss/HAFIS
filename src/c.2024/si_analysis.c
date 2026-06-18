/*
    SI Analysis module.
    Functions for SI analysis, dependency checking, and ordering.
    This module handles the analysis pass that orders predicates
    based on their dependencies before synthesis.
*/

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "si_analysis.h"
#include "si_matcher.h"
#include "si_runtime.h"
#include "event.h"
#include "alias.h"
#include "error.h"
#include "sshare.h"
#include "util.h"

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

extern struct astnode *root;
extern struct queue *predicates, *silist, *events, *alias;

/*
    Search for a visited variable in the visited_variables queue.
    Returns TRUE if found, FALSE otherwise.
*/
int __search_visited_variables__(void *_child_cstptr, void *_input_cstptr) {
    struct cstsymbol *child = (struct cstsymbol *)_child_cstptr;
    struct cstsymbol *input = (struct cstsymbol *)_input_cstptr;
    if (child == input) return TRUE;
    else return FALSE;
}

/*
    Check validity of a node's SI queue.
    Throws sinotfound_error if the node has no matching SIs.
*/
void check_validity(struct astnode *node) {
    if (node == NULL || node->token == NULL || node->si_q == NULL) {
        fprintf(stderr, "SI not found error: Node or SI queue is NULL\n");
        exit(-1);
    }
    if (node->si_q->count == 0) {
        sinotfound_error(node->token->symbol);
    }
}

/*
    Check if a node's dependencies are satisfied.
    Returns:
        0 - dependencies not satisfied, should retry later
        1 - dependencies satisfied, ready for synthesis
        2 - dependencies satisfied but should be resolved last
*/
int satisfy(struct astnode *node, struct queue *visited_variables) {
    #if SIANALYSIS
    printf("checking satify %s...\n", node->token->symbol);
    #endif
    if (node == NULL || node->token == NULL) return 0;
    
    int child_count = countastchildren(node);
    if (child_count == 1) {
        struct astnode *child = getastchild(node, 0);
        if (child == NULL || child->cstptr == NULL) return 0;
        
        struct event *e = __searchevent(child->cstptr);
        if (e == NULL || e->entities == NULL) return 0;
        
        if (e->entities->count == 1) {
            struct entity *en = (struct entity *)gqueue(e->entities, 0);
            if (en == NULL || en->cstptr == NULL) return 0;
            #if SIANALYSIS
            printf("checking entity %s\n", en->cstptr->symbol);
            #endif
            /*
                used in function satisfy only 
                if an entity's cstptr's ref_count == 1, this means that the cstptr is only referenced by this entity only
                then, we replace this entity's pointer by its aliased cstptr
            */
            if (en->cstptr->ref_count == 1) {
                struct cstsymbol *_aliased_cstptr = searchalias(en->cstptr);
                if (_aliased_cstptr == NULL) internal_error("Please check with si.c -> satisfy function. There is an entity that does not have an alias, and its cstptr is only referenced by itself.");
                _aliased_cstptr->datatype = en->cstptr->datatype;
                en->cstptr = _aliased_cstptr;
                en->cstptr->ref_count++;
            } 
            if (!searchqueue(visited_variables, en->cstptr, __search_visited_variables__)) return 0;
            else return 1;
        } else {
            for (int i = 0; i < e->entities->count; ++i) {
                struct entity *en = (struct entity *)gqueue(e->entities, i);
                if (en == NULL || en->cstptr == NULL) continue;
                if (en->cstptr->ref_count == 1 || !en->cstptr->is_argument_to_predicate) {
                    /*
                    * !en->cstptr->is_argument_to_predicate: this indicates that the variable should always use its alias, because it is not an argument, then it will never have a synthesis to form an intermediate SI. therefore, we should point it to its alias
                    */
                    struct cstsymbol *_aliased_cstptr = searchalias(en->cstptr);
                    if (_aliased_cstptr == NULL) internal_error("Please check with si.c -> satisfy function. There is an entity that does not have an alias, and its cstptr is only referenced by itself.");
                    en->cstptr = _aliased_cstptr;
                } 
                if (!searchqueue(visited_variables, en->cstptr, __search_visited_variables__)) return 0;
            }       
            /* make it to the last one to be resolved */
            return 2;
        } 
    } else {
        int hasevent = FALSE;
        int node_child_count = countastchildren(node);
        for (int i = 0; i < node_child_count; ++i) {
            struct astnode *child = getastchild(node, i);
            if (child == NULL || child->cstptr == NULL) continue;
            if (child->cstptr->symbol != NULL && child->cstptr->symbol[0] == 'e') { hasevent = TRUE; continue; }
            /* A variable is considered ready if it has been visited OR if it has been assigned a type (via TypePredicate) */
            if (!searchqueue(visited_variables, child->cstptr, __search_visited_variables__) && !child->cstptr->type_assigned) return 0;
        }
        if (hasevent) 
            return 1;
        else
            return 2;
    }
}

/*
    Main SI analysis pass.
    Orders predicates based on their dependencies.
    Handles adverb preprocessing and predicate reordering.
*/
void sianalysis() {
    struct astnode *node = NULL;
    struct queue *visited_variables = initqueue(), *target = initqueue(), *last = initqueue();
    int check = -1;
    #if SIANALYSIS
    for (int i = 0; i < predicates->count; ++i) {
        printf("%s\n", ((struct astnode *)gqueue(predicates, i))->token->symbol);
    }
    #endif

    /* 
        adverbs that are restrictive such as 'only' must be resolved first.
        they are resolved by combining them to the verb predicate, 
        how do we indicate which verb to combine?
        check the event variable, they should have the same event variable
        if there is no event variable the same as an adverb, 
        the MR is considered to have semantic error
    */
    struct queue *rbqueue = initqueue();
    while (!isempty(predicates)) {
        node = (struct astnode *)dequeue(predicates);
        #if SIANALYSIS
        if (node->token->symbol != NULL) {
            printf("Analysing symbol (%s).......\n", node->token->symbol);
        }
        #endif
        /*
        * however, we have to consider an exceptional case
        *  that is, if the adverb is not modifying a verb
        *  such that, the adverb should not be deleted, instead,
        *   it should be synthesis individually
        */
        /*
         * we need a check on the node's child, 
         * to see if its child is being accepted by another predicate 
        */
        if (node->syntax == RB &&
            getastchild(node, 0)->cstptr->ref_count/2 > 1) 
            enqueue(rbqueue, (void *)node);
        else enqueue(target, (void *)node);
        /* further mark the Variable node that is an argument to a predicate. such that for those that are not arguments should have alias */
        if (countastchildren(node) == 1 && (getastchild(node, 0))->type == Variable) {
            (getastchild(node, 0))->cstptr->is_argument_to_predicate = TRUE;
        }
    }
    while (!isempty(target)) { enqueue(predicates, dequeue(target)); }
    target = initqueue();
    while (!isempty(rbqueue)) {
        node = (struct astnode *)dequeue(rbqueue);
        /* find the predicate that accepts the same variable */
        struct astnode *r = searchqueue(predicates, node, __match_same_child_variable__);
        /* if not found, throw semantic error */
        if (r == NULL) semantic_error("Event variable matching for adverb predicate(%s) has failed.", node->token->symbol);
        /* if found, append the adverb predicate to that predicate */
        char *tmp = __combine_3_strings__(r->token->symbol, "_", node->token->symbol);
        free(r->token->symbol);
        r->token->symbol = tmp;
        /* delete the node */
        root = consumeastnodeandedge(node, root);
    }

    /* two sortings, at most n^4 */
    int count = 0, max = predicates->count * predicates->count * predicates->count * predicates->count; 
    while (!isempty(predicates)) {
        if (count > max) {
            internal_error("SI analysis has exceeded the maximum count. please check with the MR. ");
        }
        node = (struct astnode *)dequeue(predicates);
        #if SIANALYSIS
        printf("analysing %s...\n", node->token->symbol);
        #endif
        node->si_q = initqueue();
        switch(node->syntax) {
            case CD:
                enqueue(node->si_q, (void*)__generate_runtime_si__(node));
                check_validity(node);
                enqueue(visited_variables, (void *)getastchild(node, 0)->cstptr);
                enqueue(target, (void *)node);
                break;
            case NN:
            case NNS:
            case NNP:
            case NNPS:
            case PRP:
                node->si_q = q_searchqueue(silist, node, __simatcher);                
                check_validity(node);           
                if (check_need_assigned_entity(node) && !has_Rel_SI(node->si_q)) {
                    /* current assumption of this case is that there must be an alias to the variable */
                    struct cstsymbol *_aliased_cstptr = searchalias(getastchild(node, 0)->cstptr);
                    if (_aliased_cstptr == NULL) {
                        semantic_error("Please check with sianalysis function for case NN. An entity for predicate(%s) that does not have an alias, and its cstptr is only referenced by itself.", node->token->symbol);
                    } else {
                        if (!searchqueue(visited_variables, _aliased_cstptr, __search_visited_variables__)) {
                            /* TODO: we should implement the node relocation, inserting the node just after the aliased node is visited */
                            internal_error("SI analysis (not yet implemented part): The aliased variable is not yet visisted\n");
                        } else {
                            enqueue(visited_variables, (void *)getastchild(node, 0)->cstptr);
                            enqueue(target, (void *)node);
                        }                        
                    }
                } else {
                    enqueue(visited_variables, (void *)getastchild(node, 0)->cstptr);
                    enqueue(target, (void *)node);
                }
                break;
            case Gram_Rel:
                if (satisfy(node, visited_variables)) {
                    enqueue(target, (void *)node);
                } else {
                    if (isempty(predicates)) {
                        enqueue(target, (void *)node);
                    } else {
                        struct astnode *tmp = dequeue(predicates);
                        push(predicates, node);
                        push(predicates, tmp);
                    }
                }
                break;
            default:
                check = satisfy(node, visited_variables);
                if (!check) {
                    struct astnode *tmp = NULL;
                    struct queue *tmpqueue = initqueue();
                    enqueue(tmpqueue, (void *)node);
                    #if SIANALYSIS
                    printf("before...\n");
                    for (int i = 0; i < predicates->count; ++i) {
                        printf("%s\n", ((struct astnode *)gqueue(predicates, i))->token->symbol);
                    }
                    #endif
                    while (!isempty(predicates)) {
                        tmp = (struct astnode *)dequeue(predicates);
                        #if SIANALYSIS
                        printf("checking %s(%d)\n", tmp->token->symbol, tmp->syntax);
                        #endif
                        if (tmp->syntax != NN && 
                            tmp->syntax != NNS && 
                            tmp->syntax != NNP && 
                            tmp->syntax != NNPS && 
                            tmp->syntax != CD) {
                            enqueue(tmpqueue, (void *)tmp);
                            #if SIANALYSIS
                            printf("enqueue %s...\n", tmp->token->symbol);
                            #endif
                            }
                        else {
                            #if SIANALYSIS
                            printf("pushing %s...\n", tmp->token->symbol);
                            #endif
                            push(tmpqueue, (void *)tmp);
                            break;
                        }
                    }
                    while (!isempty(tmpqueue)) {
                        push(predicates, (void *)pop(tmpqueue));
                    }
                    deallocatequeue(tmpqueue, NULL);
                    #if SIANALYSIS
                    for (int i = 0; i < predicates->count; ++i) {
                        printf("%s\n", ((struct astnode *)gqueue(predicates, i))->token->symbol);
                    }
                    #endif
                } else {
                    node->si_q = q_searchqueue(silist, node, __simatcher);
                    check_validity(node);
                    if (check == 1) 
                        enqueue(target, (void *)node);
                    else
                        enqueue(last, (void *)node);
                }
                break;
        }
        count++;
    }
    while (!isempty(last)) enqueue(target, dequeue(last));
    #if SIANALYSIS
    printf("Analysed predicate sequence:\n");
    for (int i = 0; i < target->count; ++i) {
        node = (struct astnode *)gqueue(target, i);
        printf("predicate %s\n", node->token->symbol);
    }
    #endif
    deallocatequeue(last, NULL);
    deallocatequeue(predicates, NULL);
    deallocatequeue(visited_variables, NULL);
    predicates = target;
}
