#include "alias.h"
#include "cst.h"
#include "util.h"
#include <stdlib.h>

extern struct queue *alias;


// void addalias(struct queue *_alias, struct cstsymbol *_a, struct cstsymbol *_b) {
void addalias(struct cstsymbol *_a, struct cstsymbol *_b) {
    struct alias *new = (struct alias*) malloc (sizeof(struct alias));
    new->a = _a;
    new->b = _b;
    enqueue(alias, (void*)new);
}

int __aliascomparator(void *_a, void *_target) {
    struct alias *_alias = (struct alias*)_a;
    struct cstsymbol *target = (struct cstsymbol*)_target;
    if (_alias->a == target || _alias->b == target) return TRUE;
    else return FALSE;
}

// struct cstsymbol *searchalias(struct queue *_alias, struct cstsymbol *target) {
struct cstsymbol *searchalias(struct cstsymbol *target) {
    struct alias *tmp = searchqueue(alias, target, __aliascomparator);
    if (tmp == NULL) return NULL;
    else {
        if (tmp->a == target) return tmp->b;
        else return tmp->a;
    }
}

/*
    Set direct alias relationship between two cstsymbols.
    This is the preferred method for establishing aliases,
    as it's more efficient than the alias table lookup.
*/
void setaliasof(struct cstsymbol *source, struct cstsymbol *target) {
    if (source != NULL && target != NULL) {
        source->alias_of = target;
    }
}

/*
    Unified co-reference resolution function.
    Resolves alias relationships by checking:
    1. Direct alias_of field (fastest)
    2. Legacy alias table (fallback for backward compatibility)
    
    Returns the aliased cstsymbol, or NULL if no alias found.
*/
struct cstsymbol *resolve_coref(struct cstsymbol *target) {
    if (target == NULL) return NULL;
    
    // First check direct alias_of field
    if (target->alias_of != NULL) {
        return target->alias_of;
    }
    
    // Fall back to legacy alias table
    return searchalias(target);
}
