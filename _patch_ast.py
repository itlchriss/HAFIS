#!/usr/bin/env python3
"""Script to append consume functions to ast.c"""
import sys

filepath = 'src/c.2024/ast.c'

with open(filepath, 'r', encoding='utf-8', newline='') as f:
    content = f.read()

new_functions = r'''
/* =========================================================================
   Non-destructive AST manipulation (consume/mark instead of delete)
   ========================================================================= */

/* Mark a single node as consumed (logically removed from tree) */
void consumeastnode(struct astnode *node) {
    if (node) {
        node->status = AST_CONSUMED;
    }
}

/* Mark all children of a node as consumed */
void consumeastchildren(struct astnode *parent) {
    if (!parent) return;
    struct astnodelist *children = parent->children;
    while ((children = children->next) != NULL) {
        if (children->node) {
            children->node->status = AST_CONSUMED;
        }
    }
}

/*
    Mark a node as consumed and return the (possibly updated) root.
    Drop-in replacement for deleteastnodeandedge().
    Unlike deletion, the node remains in memory with AST_CONSUMED status,
    preserving the original tree structure for debugging.

    The IR builder handles cascading effects (parents with 0 or 1 active
    children) during IR construction, so we do not need to cascade here.
*/
struct astnode *consumeastnodeandedge(struct astnode *node, struct astnode *_root) {
    if (!node) return _root;

    node->status = AST_CONSUMED;

    /* If we consumed the root, find the logical root */
    if (node == _root || node->isroot == 1) {
        return find_logical_root(_root);
    }

    return _root;
}

/* Count only active (non-consumed) children of a node */
int countastchildren_active(struct astnode *node) {
    if (!node || !node->children || node->children->next == NULL) return 0;
    int i = 0;
    struct astnodelist *children = node->children;
    while ((children = children->next) != NULL) {
        if (children->node && children->node->status == AST_ACTIVE) {
            i++;
        }
    }
    return i;
}

/* Get the nth active (non-consumed) child of a node */
struct astnode *getastchild_active(struct astnode *parent, int position) {
    if (!parent) return NULL;
    struct astnodelist *children = parent->children;
    while ((children = children->next) != NULL) {
        if (children->node && children->node->status == AST_ACTIVE) {
            if (position-- == 0)
                return children->node;
        }
    }
    return NULL;
}

/*
    Find the logical root by walking down from a (possibly consumed) node
    to the first active descendant that should serve as the tree root.
*/
struct astnode *find_logical_root(struct astnode *node) {
    while (node && node->status == AST_CONSUMED) {
        /* Find first active child */
        struct astnode *active_child = NULL;
        struct astnodelist *children = node->children;
        while ((children = children->next) != NULL) {
            if (children->node && children->node->status == AST_ACTIVE) {
                active_child = children->node;
                break;
            }
        }
        if (active_child) {
            node = active_child;
        } else {
            break;  /* No active children, this is the best we can do */
        }
    }
    return node;
}

/*
    Show AST with status annotations for debugging.
    Displays [CONSUMED] markers on nodes that have been marked,
    allowing inspection of which children were assigned during synthesis.
*/
void showast_with_status(struct astnode *node, int depth) {
    int i;
    for(i = 0; i < depth; i++)
        printf("..");

    /* Show status annotation */
    if (node->status == AST_CONSUMED) {
        printf("[CONSUMED] ");
    }

    switch(node->type) {
        case Predicate:
            printf("%s(%s) Syntax: %s", node_type_name[node->type], node->token->symbol,
                    ptbsyntax2string(node->syntax));
            break;
        case GrammarNotation:
            printf("%s(%s)", node_type_name[node->type], node->token->symbol);
            break;
        case Quantifier:
            printf("%s %s %s", node_type_name[node->type], quantifier_name[node->qtype], node->token->symbol);
            break;
        case Connective:
            printf("%s", connective_name[node->conntype]);
            break;
        case Variable:
        case Operator:
            printf("%s(%s)", node_type_name[node->type], node->token->symbol);
            break;
        case Template:
        case Synthesised:
            printf("%s(%s)", node_type_name[node->type], node->token->symbol);
            break;
        case MultipleSIs:
            printf("%s(%s)(%d)", node_type_name[node->type], node->token->symbol, node->si_q ? node->si_q->count : 0);
            break;
        case TypePredicate:
            printf("%s(%s) Syntax: %s", node_type_name[node->type], node->token->symbol, ptbsyntax2string(node->syntax));
            break;
        default:
            printf("Unknown type for: %s(%d)", node->token->symbol, node->type);
    }
    if (node->isnegative == 1) {
        printf(" (Negative) ");
    }
    #if MEMDEBUG
    printf(" Memory(%p) ", (void*)node);
    #endif
    printf("\n");
    struct astnodelist *child = node->children;
    while((child = child->next) != NULL)
        showast_with_status(child->node, depth+1);
}
'''

content = content.rstrip() + '\n' + new_functions

with open(filepath, 'w', encoding='utf-8', newline='') as f:
    f.write(content)

print('Done - added consume functions to ast.c')
