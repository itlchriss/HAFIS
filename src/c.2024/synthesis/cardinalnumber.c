#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "util.h"
#include "ast.h"
#include "si.h"
#include "cst.h"
#include "event.h"
#include "error.h"

#include "synthesis.h"
#include "sshare.h"


extern struct queue *predicates, *operators, *silist, *events, *alias;
extern struct astnode *root;


/*
    The predicate is a cardinal number. Therefore, the synthesised semantics must be an integer/long. We assume it as integer first.
*/
int CD_code_synthesis(struct astnode *node) { 
    struct astnode *child = (struct astnode *)getastchild(node, 0);
    child->cstptr->datatype->p = Integer;
    child->cstptr->datatype->r = UNDEFINED;
    child->cstptr->datatype->i = UNDEFINED;
    child->cstptr->type_assigned = TRUE;
    return __direct_syntax_synthesis__(node);
}
