/*
    This is an implementation of semantic interpretation analysis and synthesis specially for the neo-davidsonian event semantics.
    
    Core SI infrastructure module containing:
    - sisynthesis(): synthesis dispatch
    - opresolution(): operator resolution
    - showsi(), deallocatesi(): SI display and memory management
    - code_syntheses[]: function pointer table for PTB synthesis
    - Gram_Rel_synthesis(), Gram_Prog_synthesis(): grammar synthesis
*/

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "util.h"
#include "si.h"
#include "si_matcher.h"
#include "si_runtime.h"
#include "si_analysis.h"
#include "ast.h"
#include "event.h"
#include "alias.h"
#include "error.h"
#include "sshare.h"
#include "synthesis.h"

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

extern struct astnode *root;
extern struct queue *predicates, *operators, *silist, *events, *alias;
extern struct queue *cst;

int selfSI[] = { 1, 0, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, 1, 1};

/* Replace SI at parent node with a new type and symbol */
void __replace_si_at_parent__(struct astnode *node, enum astnodetype type, char *si) {        
    free(node->token->symbol);
    node->token->symbol = (char*) strdup(si);
    node->type = type;
}

/* Synthesis predicate at root node */
int __synthesis_predicate_at_root__(struct si *si) {
    /* same as if and only if, we have to do operator resolution, simplification before synthesising the root predicate */
    if (predicates->count > 0) {
        /* we have to ensure all other predicates are synthesised */
        return 1;
    }
    free(root->token->symbol);
    root->token->symbol = (char*)strdup(si->interpretation);
    if (strcmp(root->token->symbol, "==>") == 0) {
        root->conntype = Op_Imply;
    } else if (strcmp(root->token->symbol, "&&") == 0) {
        root->conntype = Op_And;
    } else if (strcmp(root->token->symbol, "||") == 0) {
        root->conntype = Op_Or;
    } else if (strcmp(root->token->symbol, "<==>") == 0) {
        root->conntype = Op_Equivalent;
    } else {
        fprintf(stderr, "Unknown semantics %s is not allowed to be the root node semantic\n", root->token->symbol);
        exit(-16);
    }
    root->type = Connective;
    return 0;
}

/* =====================================================================================
 * PTB Synthesis Stubs
 * These are stub implementations for synthesis functions.
 * The actual implementations are in synthesis/*.c files where applicable.
 * ===================================================================================== */

int Vseries_code_synthesis(struct astnode *node) {
    return 0;
}

int CC_code_synthesis(struct astnode *node) { return 0; }

int DT_code_synthesis(struct astnode *node) { return 0; }
int EX_code_synthesis(struct astnode *node) { return 0; }
int FW_code_synthesis(struct astnode *node) { return 0; }

int LS_code_synthesis(struct astnode *node) { return 0; }
int MD_code_synthesis(struct astnode *node) { return 0; }

int NNPS_code_synthesis(struct astnode *node) { return 0; }
int PDT_code_synthesis(struct astnode *node) { return 0; }
int POS_code_synthesis(struct astnode *node) { return 0; }
int PRP_code_synthesis(struct astnode *node) { return 0; }
int PRP_POS_code_synthesis(struct astnode *node) { return 0; }

int RBR_code_synthesis(struct astnode *node) { return 0; }
int RBS_code_synthesis(struct astnode *node) { return 0; }
int RP_code_synthesis(struct astnode *node) { return 0; }
int SYM_code_synthesis(struct astnode *node) { return 0; }
int UH_code_synthesis(struct astnode *node) { return 0; }
int VB_code_synthesis(struct astnode *node) { 
    return Vseries_code_synthesis(node); 
}
int VBD_code_synthesis(struct astnode *node) {  
    return Vseries_code_synthesis(node);
}
int VBG_code_synthesis(struct astnode *node) { 
    return Vseries_code_synthesis(node);
}
int VBN_code_synthesis(struct astnode *node) { 
    return Vseries_code_synthesis(node);
}
int VBP_code_synthesis(struct astnode *node) { return 0; }
int VBZ_code_synthesis(struct astnode *node) {
    return Vseries_code_synthesis(node);
}
int WDT_code_synthesis(struct astnode *node) { return 0; }
int WP_code_synthesis(struct astnode *node) { return 0; }
int WP_POS_code_synthesis(struct astnode *node) { return 0; }
int WRB_code_synthesis(struct astnode *node) { return 0; }

/* =====================================================================================
 * Grammar Synthesis Functions
 * ===================================================================================== */

/*
    synthesising Rel predicates
    the Rel predicates must accept 2 SI-Assigned arguments
    One of the arguments must have Assigned SI starting with '__Rel__', let it be d
    d is treated as a dependent value, such that its final SI is decided by another argument's (call this x) datatype
    If x does not have a datatype (aka, there are many SIs), then the number of synthesised SIs is equal to the number of SIs that x has multiplied by the number of SIs that d has
*/
int Gram_Rel_synthesis(struct astnode *node) {
    if (node == NULL) return -1;
    
    struct astnode *child1 = getastchild(node, 0);
    struct astnode *child2 = getastchild(node, 1);
    
    if (child1 == NULL || child2 == NULL) {
        semantic_error("Gram_Rel_synthesis: Rel predicate must have exactly 2 arguments.", node->token ? node->token->symbol : "unknown");
        return -1;
    }
    
    if (child1->cstptr == NULL || child2->cstptr == NULL) {
        semantic_error("Gram_Rel_synthesis: Rel arguments must have cstptr.", node->token ? node->token->symbol : "unknown");
        return -1;
    }
    
    struct astnode *x, *d;
    if (__is_Rel_dependent__(child1->cstptr)) {
        d = child1;
        x = child2;
    } else {
        d = child2;
        x = child1;
    }

    /* 
        Although it looks like the normal relationship synthesis, but it is in fact different 
        the SI is not searched from the parent node, instead, it is searched from d's si_q
    */
    if (d->cstptr->datalist == NULL || d->cstptr->datalist->count == 0) {
        semantic_error("Gram_Rel_synthesis: Rel dependent value has no data.", d->token ? d->token->symbol : "unknown");
        return -1;
    }
    
    char *rel_symbol = (char *)gqueue(d->cstptr->datalist, 0);
    if (rel_symbol == NULL) {
        semantic_error("Gram_Rel_synthesis: Rel symbol is NULL.", "unknown");
        return -1;
    }
    
    d->si_q = q_searchqueue(silist, (void *)rel_symbol, __match_si_with_symbol_only__);
    if (d->si_q->count == 0) sinotfound_error(rel_symbol);
    
    if (x->cstptr->datatype == NULL) {
        semantic_error("Gram_Rel_synthesis: Rel argument has no datatype.", x->token ? x->token->symbol : "unknown");
        return -1;
    }
    
    struct queue *siq = q_searchqueue(d->si_q, x->cstptr->datatype, __match_si_with_input_arg_datatype__);
    if (siq->count == 0) sinotfound_error(rel_symbol);
    __Rel_synthesis__(x->cstptr, d->cstptr, siq);
    deallocatequeue(siq, NULL);

    d->cstptr->status = Assigned;
    d->cstptr->ref_count--;
    root = deleteastnodeandedge(node, root);
    return 0;
}

/* Reserve for future usage */
int Gram_Prog_synthesis(struct astnode *node) {
    return 0;
}

/* Function pointer table for PTB synthesis dispatch */
int (*code_syntheses[])(struct astnode *) = {CC_code_synthesis, CD_code_synthesis, DT_code_synthesis, EX_code_synthesis, FW_code_synthesis, IN_code_synthesis, JJ_code_synthesis, JJR_code_synthesis, JJS_code_synthesis, LS_code_synthesis, MD_code_synthesis, NN_code_synthesis, NNS_code_synthesis, NNP_code_synthesis, NNPS_code_synthesis, PDT_code_synthesis, POS_code_synthesis, PRP_code_synthesis, PRP_POS_code_synthesis, RB_code_synthesis, RBR_code_synthesis, RBS_code_synthesis, RP_code_synthesis, SYM_code_synthesis, TO_code_synthesis, UH_code_synthesis, VB_code_synthesis, VBD_code_synthesis, VBG_code_synthesis, VBN_code_synthesis, VBP_code_synthesis, VBZ_code_synthesis, WDT_code_synthesis, WP_code_synthesis, WP_POS_code_synthesis, WRB_code_synthesis, Gram_Prog_synthesis, Gram_Rel_synthesis};

/* =====================================================================================
 * Core SI Functions
 * ===================================================================================== */

/* 
    semantic interpretation synthesis 
    this is a process to identify as many si as possible presenting in the meaning representation
    - parameter descriptions
        predicates  : a queue holding pointers of predicates present in an abstract syntax tree parsed from meaning representation
        silist      : a queue holding semantic interpretations parsed from standard semantic interpretation database
        cst         : a queue holding the compile time symbols, aka the identitiers in the meaning representation
*/
void sisynthesis() {
    struct astnode *node;
    struct queue *tmp = initqueue();
    #if SIDEBUG
    printf("si synthesis: after sorting, there are %d predicates in the queue.\n", predicates->count);
    for (int i = 0; i < predicates->count; ++i) {
        node = (struct astnode*)gqueue(predicates, i);
        printf("%d. %s(%s) %d\n", i + 1, node->token->symbol, ptbsyntax2string(node->syntax), node->si_q->count);
    }
    #endif

    
    while (!isempty(predicates)) {    
        node = (struct astnode*)dequeue(predicates);
        #if SIDEBUG
        printf("si synthesis: processing predicate %s(%s) with %d SIs available.\n", node->token->symbol, ptbsyntax2string(node->syntax), node->si_q->count);
        showast(root, 0);
        showqueue(cst, showcstsymbol);
        #endif

        /* 
            Rigorously checking the child status
            1. If there is only one child, then
                i. if the child is an event variable, all the event components must be Assigned
                ii. if the child is not an event variable, the node must be a noun or a cardinal number predicate
                iii. else, semantic error is thrown
            2. If there are two or more children, then all children must be Assigned
        */
        int child_count = countastchildren(node);
        /* NOTE: remember to update the event variable making its status to Assigned when all the event components are Assigned */
        if (child_count == 1) { 
            struct astnode *child = (struct astnode *) getastchild(node, 0);
            if (__is_event_variable__(child)) {
                /* the child is an event variable, and it is marked with Assigned which indicates all event components (related variables) are marked Assigned */
                if (child->cstptr->status != Assigned) {
                    semantic_error("Synthesis is stopped because a event predicate(%s) has not completed component synthesis.", node->token->symbol);
                } else {
                    event_synthesis(node);
                }
                
            } else if (child->cstptr->status != Assigned && 
                !__is_noun_predicate__(node) && 
                node->syntax != CD) {
                /* there can be a case that the preposition comes before the adjectives. we have to think of retry */
                semantic_error("Synthesis is stopped because a predicate(%s) has non-noun and non-CD syntax and its argument has not been assigned.", node->token->symbol); 
            } else {
                #if SIDEBUG
                printf("si synthesis: processing predicate %s\n", node->token->symbol);
                #endif
                /* do the synthesis according to the syntax of predicate */
                (*code_syntheses[node->syntax])(node);       
            }
        } else {
            /* checking all children, if one of them is not assigned with semantics, the synthesis cannot be done */            
            for (int i = 0; i < child_count; ++i) {
                struct astnode *tmp = (struct astnode *) getastchild(node, i);
                if ((tmp->cstptr->status != Assigned && !__is_preposition_predicate__(node)) ||
                    (__is_preposition_predicate__(node) && tmp->cstptr->status != Assigned && tmp->cstptr->symbol[0] != 'e')) {
                    #if SIDEBUG
                    printf("The child %s is not assigned\n", tmp->cstptr->symbol);
                    #endif
                    semantic_error("Synthesis is stopped because a predicate(%s) has children that are not Assigned.", node->token->symbol);
                }
            }
            /* TODO: 
                if the return is FALSE, we should push it back to the predicates queue. 
                there is a case in NN, that the predicate depends on a variable with type.
                therefore, the variable needs to wait for its aliased variable to be assigned.                
            */
            int result = (*code_syntheses[node->syntax])(node);       
            if (result == FALSE && node->syntax == IN) {
                enqueue(tmp, (void *)dequeue(predicates));
                push(predicates, node);
                push(predicates, dequeue(tmp));
            }
        }
        /* ================================================================================================ */
        #if ASTDEBUG
        printf(" ================================================================================================\n");
        printf("si synthesis: After processing predicate\n");
        showast(root, 0);
        showqueue(cst, showcstsymbol);
        printf(" ================================================================================================\n");
        fflush(stdout);
        #endif

        /*
            if a variable is a component of a event, such as Subj(e) = x, then x is a subject of event e,
            then after assigning value to x, we have to check if all components of e are assigned.
            if so, then we update e as Assigned
        */
        update_events();
    }
    #if SIDEBUG
    printf("si synthesis finished\n");
    #endif
}

/* 
    Operator resolution.
    According to the semantics of the higher order logic, the equal operator represents 
    an alias relationship between two variables. Therefore, we build an alias table to 
    remark these relationships. Once the relationship is built, the subtree with operator 
    as tree root can be pruned.
*/
void opresolution() {
    struct astnode *node, *left, *right;    
    while (operators->count > 0) {
        node = (struct astnode*)dequeue(operators);
        left = getastchild(node, 0);
        right = getastchild(node, 1);
        addalias(left->cstptr, right->cstptr);   
        if (has_datatype(left->cstptr) && !has_datatype(right->cstptr)) {
            right->cstptr->datatype = left->cstptr->datatype;
        } else if  (!has_datatype(left->cstptr) && has_datatype(right->cstptr)) {
            left->cstptr->datatype = right->cstptr->datatype;
        }
        /*
            because we are going to remove these two nodes
            decreasing the count before deletion or the pointer is not found
        */
        left->cstptr->ref_count--;
        right->cstptr->ref_count--;
        root = deleteastnodeandedge(node, root);
        #ifdef SIDEBUG
        showast(root, 0);
        #endif
    }
}

/* Display SI information */
void showsi(void *_si) {
    struct si *si = (struct si*)_si;
    printf("==========================Semantic interpretations: =========================\n");
    printf("Symbol          Syntactic Category       Arguments    Interpretation\n");
    printf("Symbol: %s   Syntactic Category: ", si->symbol);        
    for (int j = 0; j < si->syntax->count; ++j) {
        printf("%s ", ptbsyntax2string((enum ptbsyntax)gqueue(si->syntax, j)));
    }
    printf("  Arguments: ");
    for (int j = 0; j < si->args->count; ++j) {
        struct si_arg *arg = (struct si_arg*)gqueue(si->args, j);
        printf("%s(p:%d, r:%d)", arg->symbol, arg->datatype->p, arg->datatype->r);
    }
    printf("     Synthesised datatype: p:%d, r:%d   ", si->synthesised_datatype->p, si->synthesised_datatype->r);
    printf("   %s\n", si->interpretation);
    printf("============================================================================\n");
}

/* Deallocate SI argument */
void deallocatesi_arg(void *tmp) {
    struct si_arg *arg = (struct si_arg*)tmp;
    if (arg->symbol)
        free(arg->symbol);    
}

/* Deallocate SI */
void deallocatesi(void *tmp) {    
    struct si *si = (struct si*)tmp;
    if (si->syntax)
        deallocatequeue(si->syntax, NULL);
    if (si->args)
        deallocatequeue(si->args, deallocatesi_arg);
    if (si->interpretation)
        free(si->interpretation);     
    free(si->symbol);
    free(si);
}
