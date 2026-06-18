#ifndef _AST_H
#define _AST_H

#include "util.h"
#include "cst.h"
#include "event.h"

// this has to agree exactly with the array in ast.c
enum astnodetype { Quantifier, Predicate, Variable, Connective, Synthesised, Template, Operator, GrammarNotation, MultipleSIs, TypePredicate, EventVariable, EventEntity, Pronoun };

enum quantifiertype { Quantifier_Exists, Quantifier_ForAll };
enum conntype { Op_And, Op_Or,  Op_Equivalent, Op_Imply };
// Penn tree bank syntax
enum ptbsyntax {
    CC, CD,DT,EX,FW,IN,JJ,JJR,JJS,LS,MD,NN,NNS,NNP,NNPS,PDT,POS,PRP,PRP_POS,RB,RBR,RBS,RP,SYM,TO,UH,VB,VBD,VBG,VBN,VBP,VBZ,WDT,WP,WP_POS,WRB,  Gram_Prog, Gram_Rel};
enum contextualtype { Cont_Class, Cont_Interface, Cont_Method, Cont_Parameter };

/*
    AST node status for non-destructive tree manipulation.
    Instead of deleting nodes from the parse AST, we mark them as consumed.
    The IR builder then skips consumed nodes, preserving the original tree
    for debugging and inspection of child assignment status.
*/
enum astnodestatus { AST_ACTIVE, AST_CONSUMED };

struct token {
    char *symbol;    
    int line, column;
};

struct quantified_range {
    int from, to;
};

struct astnode {
    enum astnodetype type;        
    struct token *token;    
    struct astnode *parent;
    struct astnodelist *children;
    /* type of contextual element */
    enum contextualtype ctype;
    /* type of quantifier if the type == Quantifier */
    enum quantifiertype qtype;
    /* 
        specify the ranges when the qtype is ForAll. 
        the default range is 0 to x's length or size, where x is the quantified variable
        if x has no datatype, then the ranges are applied to all types and n JMLs with different range types are generated
        each element in this queue specifies a range.
        if there are multiple ranges, they are connected with the operator AND(&&)
    */
    struct queue *quantified_ranges;
    /* type of connective if the type == Connective */
    enum conntype conntype;
    /* type of word, according to penn-tree bank */
    enum ptbsyntax syntax;   
    struct cstsymbol *cstptr;  
    /* default 0 for non-root nodes. 1 indicates this node is a root node */
    int isroot;
    /* default 0 for positive. 1 for negative */
    int isnegative;
    /* storing the matched SIs. there can be multiple SIs because multiple Java types can be related to a semantic. */
    /* if the node->type is Synthesised, this queue holds the synthesised SIs of the subtree rooted at this node. */
    struct queue *si_q;
    /* 
        node status: AST_ACTIVE (part of the logical tree) or AST_CONSUMED (processed and removed from logical view).
        Consumed nodes remain in memory for debugging but are skipped during IR construction.
    */
    enum astnodestatus status;

};

struct astnodelist {
    struct astnode *node;
    struct astnodelist *next;
    struct astnodelist *prev;
};

struct astnode *getlastchild(struct astnodelist *);
int getnodelistlength(struct astnodelist *list);
void appendnode(struct astnodelist *list, struct astnode *n);
struct astnodelist *newastnodelist(struct astnode* n);
struct token *newtoken(char *text, int line, int column);
struct astnode *newastnode(enum astnodetype type, struct token *token);
void addastchild(struct astnode *parent, struct astnode *child);
void deleteastnode(struct astnode *node);
int deleteastchild(struct astnode *parent, struct astnode *child);
void deleteastchildren(struct astnode *parent);
void addastchildren(struct astnode *parent, struct astnodelist *children);
void insertastchild(struct astnode *parent, struct astnode *child, int position);
struct astnode *getastchild(struct astnode *parent, int position);
int countastchildren(struct astnode *node);
struct astnode * deleteastnodeandedge(struct astnode *, struct astnode *);
void showast(struct astnode *root, int depth);
void deallocateast(struct astnode *root);
enum ptbsyntax string2ptbsyntax(char *input);
char *ptbsyntax2string(enum ptbsyntax ptb);

/* 
    simplifying the tree after semantic identification and operator resolution 
    this simplification may update the pointer of root node
    return an astnode pointer pointing at root
*/
struct astnode *astsimplification(struct astnode *);

/* =========================================================================
   Non-destructive AST manipulation (consume/mark instead of delete)
   
   These functions mark nodes as AST_CONSUMED instead of freeing them.
   The original parse tree is preserved for debugging, allowing inspection
   of which children were assigned during synthesis.
   The IR builder skips consumed nodes and applies simplification during
   IR construction.
   ========================================================================= */

/* Mark a single node as consumed (logically removed from tree) */
void consumeastnode(struct astnode *node);

/* Mark all children of a node as consumed */
void consumeastchildren(struct astnode *parent);

/* 
    Mark a node as consumed and return the (possibly updated) root.
    Drop-in replacement for deleteastnodeandedge().
    Unlike deletion, the node remains in memory with AST_CONSUMED status,
    preserving the original tree structure for debugging.
*/
struct astnode *consumeastnodeandedge(struct astnode *node, struct astnode *_root);

/* Count only active (non-consumed) children of a node */
int countastchildren_active(struct astnode *node);

/* Get the nth active (non-consumed) child of a node */
struct astnode *getastchild_active(struct astnode *parent, int position);

/*
    Find the logical root by walking down from a (possibly consumed) node
    to the first active descendant that should serve as the tree root.
    Handles cascading consumed nodes.
*/
struct astnode *find_logical_root(struct astnode *node);

/*
    Post-synthesis AST simplification (non-destructive).
    Marks Quantifier/Connective nodes with 0 active children as consumed,
    cascading bottom-up. Updates root pointer if needed.
*/
void ast_simplify_after_synthesis(struct astnode **root_ptr);

/* 
    Show AST with status annotations for debugging.
    Displays [CONSUMED] markers on nodes that have been marked,
    allowing inspection of which children were assigned during synthesis.
*/
void showast_with_status(struct astnode *node, int depth);

int iscomparator(char *relation);
int isequality(char *relation);
#endif
