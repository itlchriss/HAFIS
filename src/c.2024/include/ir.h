#ifndef IR_H
#define IR_H

#include <stdio.h>
#include "util.h"
#include "cst.h"

/*
    Intermediate Representation (IR) for HAFIS compiler.
    
    The IR is a backend-agnostic representation of specifications.
    It sits between the AST (after synthesis) and code generation.
    
    Pipeline: Source -> Parse -> AST -> Synthesis -> IR -> Code Gen -> Backend Output
    
    The IR captures:
    - Quantifiers (forall, exists) with their variables and ranges
    - Connectives (and, or, imply, equivalent)
    - Predicates (synthesized SI expressions)
    - Negation
    
    Backend-specific formatting is handled during code generation,
    not in the IR itself.
*/

/* IR Node Types */
enum ir_node_type {
    IR_QUANTIFIER,      /* ForAll or Exists quantifier */
    IR_CONNECTIVE,      /* Logical connective (and, or, imply, equiv) */
    IR_PREDICATE,       /* Synthesized predicate expression */
    IR_VARIABLE         /* Quantified variable reference */
};

/* Quantifier types */
enum ir_quantifier_type {
    IR_FORALL,          /* Universal quantifier */
    IR_EXISTS           /* Existential quantifier */
};

/* Connective types (matches ast.h conntype) */
enum ir_connective_type {
    IR_AND,             /* Logical AND */
    IR_OR,              /* Logical OR */
    IR_EQUIVALENT,      /* Logical equivalence */
    IR_IMPLY            /* Logical implication */
};

/* Quantified variable information */
struct ir_quantified_var {
    char var_name;                          /* Variable name (e.g., 'i', 'j') */
    char *type_name;                        /* Type name (e.g., "int", "String") */
    char *range_start;                      /* Range start expression (NULL for default) */
    char *range_end;                        /* Range end expression (NULL for default) */
    enum reference_datatype ref_type;       /* Reference datatype (Array, String, List, etc.) */
    int use_cardinality;                    /* 1 if using |x| notation (Dafny sequences) */
    struct ir_quantified_var *next;         /* For multiple quantified variables */
};

/* IR Node structure */
struct ir_node {
    enum ir_node_type type;
    int is_negative;                        /* 1 if negated, 0 otherwise */
    int interpretation_index;               /* For multiple interpretations per predicate */
    int max_interpretations;                /* Total number of interpretations */
    
    union {
        /* IR_QUANTIFIER */
        struct {
            enum ir_quantifier_type qtype;
            struct ir_quantified_var *qvar;     /* Quantified variable info */
            struct ir_node *body;               /* Quantifier body (usually connective) */
            struct ir_node *range_constraint;   /* Range constraint (for custom ranges) */
        } quantifier;
        
        /* IR_CONNECTIVE */
        struct {
            enum ir_connective_type ctype;
            struct ir_node *left;
            struct ir_node *right;
        } connective;
        
        /* IR_PREDICATE */
        struct {
            char *expression;                   /* Synthesized expression string */
            struct cstsymbol *cst_ptr;          /* Reference to CST symbol (for type info) */
            struct queue *si_queue;             /* Reference to SI queue (for multiple interpretations) */
        } predicate;
        
        /* IR_VARIABLE */
        struct {
            char *name;                         /* Variable name */
            struct cstsymbol *cst_ptr;          /* Reference to CST symbol */
        } variable;
    } data;
};

/* IR Node list for children */
struct ir_node_list {
    struct ir_node *node;
    struct ir_node_list *next;
    struct ir_node_list *prev;
};

/* =========================================================================
   IR Construction Functions
   ========================================================================= */

/* Create a new IR node of the specified type */
struct ir_node *ir_node_new(enum ir_node_type type);

/* Create a quantifier node */
struct ir_node *ir_quantifier_new(enum ir_quantifier_type qtype, 
                                   struct ir_quantified_var *qvar,
                                   struct ir_node *body);

/* Create a connective node */
struct ir_node *ir_connective_new(enum ir_connective_type ctype,
                                   struct ir_node *left,
                                   struct ir_node *right);

/* Create a predicate node */
struct ir_node *ir_predicate_new(char *expression, 
                                  struct cstsymbol *cst_ptr,
                                  struct queue *si_queue);

/* Create a variable node */
struct ir_node *ir_variable_new(char *name, struct cstsymbol *cst_ptr);

/* Create a quantified variable info structure */
struct ir_quantified_var *ir_qvar_new(char var_name, 
                                       char *type_name,
                                       enum reference_datatype ref_type);

/* =========================================================================
   IR Manipulation Functions
   ========================================================================= */

/* Set negation on a node */
void ir_set_negative(struct ir_node *node, int is_negative);

/* Add a child node to a quantifier's body */
void ir_quantifier_set_body(struct ir_node *qnode, struct ir_node *body);

/* Set range constraint for a quantifier */
void ir_quantifier_set_range(struct ir_node *qnode, struct ir_node *range);

/* =========================================================================
   IR Traversal and Visitor Functions
   ========================================================================= */

/* Walk the IR tree with a visitor function */
typedef void (*ir_visitor_fn)(struct ir_node *node, void *context);
void ir_walk(struct ir_node *node, ir_visitor_fn visitor, void *context);

/* Count maximum interpretations across all predicate nodes */
int ir_count_max_interpretations(struct ir_node *root);

/* =========================================================================
   IR Deallocation
   ========================================================================= */

/* Free an IR node and all its children */
void ir_node_free(struct ir_node *node);

/* Free a quantified variable info structure */
void ir_qvar_free(struct ir_quantified_var *qvar);

/* Free the entire IR tree */
void ir_free(struct ir_node *root);

/* =========================================================================
   IR Debug Functions
   ========================================================================= */

/* Print IR tree for debugging */
void ir_dump(struct ir_node *node, int indent);

/* Get string representation of node type */
const char *ir_node_type_str(enum ir_node_type type);

/* Get string representation of quantifier type */
const char *ir_quantifier_type_str(enum ir_quantifier_type qtype);

/* Get string representation of connective type */
const char *ir_connective_type_str(enum ir_connective_type ctype);

/* =========================================================================
   IR Builder Functions (in ir_builder.c)
   ========================================================================= */

/* Forward declaration of AST node */
struct astnode;

/* Build IR from AST after synthesis */
struct ir_node *ir_build_from_ast(struct astnode *root);

#endif /* IR_H */
