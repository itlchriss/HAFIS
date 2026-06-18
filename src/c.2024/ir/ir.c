/*
    Intermediate Representation (IR) Implementation for HAFIS compiler.
    
    This file implements the IR data structures and operations.
    The IR is backend-agnostic and serves as an intermediate step
    between AST synthesis and code generation.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ir.h"

/* =========================================================================
   IR Construction Functions
   ========================================================================= */

struct ir_node *ir_node_new(enum ir_node_type type) {
    struct ir_node *node = (struct ir_node *)calloc(1, sizeof(struct ir_node));
    if (!node) {
        fprintf(stderr, "IR: Failed to allocate memory for IR node\n");
        exit(1);
    }
    node->type = type;
    node->is_negative = 0;
    node->interpretation_index = 0;
    node->max_interpretations = 1;
    return node;
}

struct ir_node *ir_quantifier_new(enum ir_quantifier_type qtype,
                                   struct ir_quantified_var *qvar,
                                   struct ir_node *body) {
    struct ir_node *node = ir_node_new(IR_QUANTIFIER);
    node->data.quantifier.qtype = qtype;
    node->data.quantifier.qvar = qvar;
    node->data.quantifier.body = body;
    node->data.quantifier.range_constraint = NULL;
    return node;
}

struct ir_node *ir_connective_new(enum ir_connective_type ctype,
                                   struct ir_node *left,
                                   struct ir_node *right) {
    struct ir_node *node = ir_node_new(IR_CONNECTIVE);
    node->data.connective.ctype = ctype;
    node->data.connective.left = left;
    node->data.connective.right = right;
    return node;
}

struct ir_node *ir_predicate_new(char *expression,
                                  struct cstsymbol *cst_ptr,
                                  struct queue *si_queue) {
    struct ir_node *node = ir_node_new(IR_PREDICATE);
    node->data.predicate.expression = expression ? strdup(expression) : NULL;
    node->data.predicate.cst_ptr = cst_ptr;
    node->data.predicate.si_queue = si_queue;
    return node;
}

struct ir_node *ir_variable_new(char *name, struct cstsymbol *cst_ptr) {
    struct ir_node *node = ir_node_new(IR_VARIABLE);
    node->data.variable.name = name ? strdup(name) : NULL;
    node->data.variable.cst_ptr = cst_ptr;
    return node;
}

struct ir_quantified_var *ir_qvar_new(char var_name,
                                       char *type_name,
                                       enum reference_datatype ref_type) {
    struct ir_quantified_var *qvar = (struct ir_quantified_var *)calloc(1, sizeof(struct ir_quantified_var));
    if (!qvar) {
        fprintf(stderr, "IR: Failed to allocate memory for quantified variable\n");
        exit(1);
    }
    qvar->var_name = var_name;
    qvar->type_name = type_name ? strdup(type_name) : NULL;
    qvar->range_start = NULL;
    qvar->range_end = NULL;
    qvar->ref_type = ref_type;
    qvar->use_cardinality = 0;
    qvar->next = NULL;
    return qvar;
}

/* =========================================================================
   IR Manipulation Functions
   ========================================================================= */

void ir_set_negative(struct ir_node *node, int is_negative) {
    if (node) {
        node->is_negative = is_negative;
    }
}

void ir_quantifier_set_body(struct ir_node *qnode, struct ir_node *body) {
    if (qnode && qnode->type == IR_QUANTIFIER) {
        qnode->data.quantifier.body = body;
    }
}

void ir_quantifier_set_range(struct ir_node *qnode, struct ir_node *range) {
    if (qnode && qnode->type == IR_QUANTIFIER) {
        qnode->data.quantifier.range_constraint = range;
    }
}

/* =========================================================================
   IR Traversal and Visitor Functions
   ========================================================================= */

void ir_walk(struct ir_node *node, ir_visitor_fn visitor, void *context) {
    if (!node || !visitor) return;
    
    /* Visit current node */
    visitor(node, context);
    
    /* Recurse into children based on node type */
    switch (node->type) {
        case IR_QUANTIFIER:
            if (node->data.quantifier.body) {
                ir_walk(node->data.quantifier.body, visitor, context);
            }
            if (node->data.quantifier.range_constraint) {
                ir_walk(node->data.quantifier.range_constraint, visitor, context);
            }
            break;
            
        case IR_CONNECTIVE:
            if (node->data.connective.left) {
                ir_walk(node->data.connective.left, visitor, context);
            }
            if (node->data.connective.right) {
                ir_walk(node->data.connective.right, visitor, context);
            }
            break;
            
        case IR_PREDICATE:
        case IR_VARIABLE:
            /* Leaf nodes - no children */
            break;
    }
}

/* Helper context for counting interpretations */
struct ir_count_context {
    int max_count;
};

static void ir_count_interpretations_visitor(struct ir_node *node, void *ctx) {
    struct ir_count_context *context = (struct ir_count_context *)ctx;
    if (node->type == IR_PREDICATE && node->data.predicate.si_queue) {
        int count = node->data.predicate.si_queue->count;
        if (count > context->max_count) {
            context->max_count = count;
        }
    }
}

int ir_count_max_interpretations(struct ir_node *root) {
    struct ir_count_context ctx = { .max_count = 1 };
    ir_walk(root, ir_count_interpretations_visitor, &ctx);
    return ctx.max_count;
}

/* =========================================================================
   IR Deallocation
   ========================================================================= */

void ir_qvar_free(struct ir_quantified_var *qvar) {
    if (!qvar) return;
    if (qvar->type_name) free(qvar->type_name);
    if (qvar->range_start) free(qvar->range_start);
    if (qvar->range_end) free(qvar->range_end);
    if (qvar->next) ir_qvar_free(qvar->next);
    free(qvar);
}

void ir_node_free(struct ir_node *node) {
    if (!node) return;
    
    switch (node->type) {
        case IR_QUANTIFIER:
            if (node->data.quantifier.qvar) {
                ir_qvar_free(node->data.quantifier.qvar);
            }
            if (node->data.quantifier.body) {
                ir_node_free(node->data.quantifier.body);
            }
            if (node->data.quantifier.range_constraint) {
                ir_node_free(node->data.quantifier.range_constraint);
            }
            break;
            
        case IR_CONNECTIVE:
            if (node->data.connective.left) {
                ir_node_free(node->data.connective.left);
            }
            if (node->data.connective.right) {
                ir_node_free(node->data.connective.right);
            }
            break;
            
        case IR_PREDICATE:
            if (node->data.predicate.expression) {
                free(node->data.predicate.expression);
            }
            /* Note: cst_ptr and si_queue are not owned by IR */
            break;
            
        case IR_VARIABLE:
            if (node->data.variable.name) {
                free(node->data.variable.name);
            }
            /* Note: cst_ptr is not owned by IR */
            break;
    }
    
    free(node);
}

void ir_free(struct ir_node *root) {
    ir_node_free(root);
}

/* =========================================================================
   IR Debug Functions
   ========================================================================= */

const char *ir_node_type_str(enum ir_node_type type) {
    switch (type) {
        case IR_QUANTIFIER: return "QUANTIFIER";
        case IR_CONNECTIVE: return "CONNECTIVE";
        case IR_PREDICATE:  return "PREDICATE";
        case IR_VARIABLE:   return "VARIABLE";
        default:            return "UNKNOWN";
    }
}

const char *ir_quantifier_type_str(enum ir_quantifier_type qtype) {
    switch (qtype) {
        case IR_FORALL:  return "FORALL";
        case IR_EXISTS:  return "EXISTS";
        default:         return "UNKNOWN";
    }
}

const char *ir_connective_type_str(enum ir_connective_type ctype) {
    switch (ctype) {
        case IR_AND:        return "AND";
        case IR_OR:         return "OR";
        case IR_EQUIVALENT: return "EQUIVALENT";
        case IR_IMPLY:      return "IMPLY";
        default:            return "UNKNOWN";
    }
}

static void ir_print_indent(int indent) {
    for (int i = 0; i < indent; i++) {
        printf("  ");
    }
}

void ir_dump(struct ir_node *node, int indent) {
    if (!node) {
        ir_print_indent(indent);
        printf("(null)\n");
        return;
    }
    
    ir_print_indent(indent);
    printf("[%s", ir_node_type_str(node->type));
    if (node->is_negative) {
        printf(" NEGATED");
    }
    printf("]\n");
    
    switch (node->type) {
        case IR_QUANTIFIER:
            ir_print_indent(indent + 1);
            printf("qtype: %s\n", ir_quantifier_type_str(node->data.quantifier.qtype));
            if (node->data.quantifier.qvar) {
                ir_print_indent(indent + 1);
                printf("qvar: %c:%s (ref_type=%d, cardinality=%d)\n",
                       node->data.quantifier.qvar->var_name,
                       node->data.quantifier.qvar->type_name ? node->data.quantifier.qvar->type_name : "unknown",
                       node->data.quantifier.qvar->ref_type,
                       node->data.quantifier.qvar->use_cardinality);
            }
            ir_print_indent(indent + 1);
            printf("body:\n");
            ir_dump(node->data.quantifier.body, indent + 2);
            if (node->data.quantifier.range_constraint) {
                ir_print_indent(indent + 1);
                printf("range:\n");
                ir_dump(node->data.quantifier.range_constraint, indent + 2);
            }
            break;
            
        case IR_CONNECTIVE:
            ir_print_indent(indent + 1);
            printf("ctype: %s\n", ir_connective_type_str(node->data.connective.ctype));
            ir_print_indent(indent + 1);
            printf("left:\n");
            ir_dump(node->data.connective.left, indent + 2);
            ir_print_indent(indent + 1);
            printf("right:\n");
            ir_dump(node->data.connective.right, indent + 2);
            break;
            
        case IR_PREDICATE:
            ir_print_indent(indent + 1);
            printf("expr: %s\n", node->data.predicate.expression ? node->data.predicate.expression : "(null)");
            if (node->data.predicate.si_queue) {
                ir_print_indent(indent + 1);
                printf("si_count: %d\n", node->data.predicate.si_queue->count);
            }
            break;
            
        case IR_VARIABLE:
            ir_print_indent(indent + 1);
            printf("name: %s\n", node->data.variable.name ? node->data.variable.name : "(null)");
            break;
    }
}
