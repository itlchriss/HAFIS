#ifndef BACKEND_H
#define BACKEND_H

#include <stdio.h>
#include "ast.h"
#include "cst.h"

/*
    Backend interface for HAFIS compiler.
    This header defines the abstraction layer for supporting multiple 
    target specification languages (JML, Dafny, etc.)
    
    Design: Dependency Injection via function pointers.
    The unified code generator (cg_unified.c) accepts a backend_ops*
    and delegates all language-specific formatting to the backend.
*/

/* Backend type enumeration */
enum backend_type {
    BACKEND_TYPE_JML = 0,
    BACKEND_TYPE_DAFNY = 1
};

/* Backend-specific operations structure */
struct backend_ops {
    /* Name of the backend */
    const char *name;
    
    /* =========================================================================
       Code Generation Operations (used by cg_unified.c)
       ========================================================================= */
    
    /* Emit universal quantifier header
     * JML:   \ forall int i; 0 <= i < type_name.length_str; 
     * Dafny: forall i:int :: 0 <= i < type_name.length_str ==> 
     */
    void (*emit_forall)(FILE *s, char var, const char *type_name, const char *length_str);
    
    /* Emit existential quantifier header
     * JML:   \ exists int i; 0 <= i < type_name.length_str; 
     * Dafny: exists i:int :: 0 <= i < type_name.length_str && 
     */
    void (*emit_exists)(FILE *s, char var, const char *type_name, const char *length_str);
    
    /* Emit forall without range (for custom ranges)
     * JML:   \ forall int i; 
     * Dafny: forall i:int :: 
     */
    void (*emit_forall_no_range)(FILE *s, char var);
    
    /* Emit exists for sequences (no length_str, use |type_name| notation)
     * Dafny: exists i:int :: 0 <= i < |type_name| && 
     */
    void (*emit_exists_seq)(FILE *s, char var, const char *type_name);
    
    /* Emit forall for sequences (no length_str, use |type_name| notation)
     * Dafny: forall i:int :: 0 <= i < |type_name| ==> 
     */
    void (*emit_forall_seq)(FILE *s, char var, const char *type_name);
    
    /* Get length string for a reference datatype
     * JML:   "length" (array), "length()" (string), "size()" (collection)
     * Dafny: ".Length" (array/string), NULL (sequence -> use |x|)
     */
    const char *(*get_length_str)(enum reference_datatype r);
    
    /* =========================================================================
       SI Synthesis Operations (used by dafny.c, jml.c)
       ========================================================================= */
    
    /* Get length representation for a datatype */
    char *(*get_length_repr)(struct datatype *dt);
    
    /* Get access representation for a datatype (array/list/string access) */
    char *(*get_access_repr)(struct datatype *dt, int mode);
    
    /* Generate universal quantifier string */
    char *(*forall_expr)(char *var, char *range_start, char *range_end, char *body);
    
    /* Generate existential quantifier string */
    char *(*exists_expr)(char *var, char *range_start, char *range_end, char *body);
    
    /* Generate result keyword */
    char *(*result_keyword)(void);
    
    /* Generate old value expression */
    char *(*old_expr)(char *expr);
    
    /* Generate array equality expression */
    char *(*array_equal)(char *d1, char *d2);
    
    /* Generate contain expression */
    char *(*contain_expr)(char *container, char *element, struct datatype *dt);
};

/* Get the current backend operations based on selection */
struct backend_ops *get_backend_ops(enum backend_type type);

/* Set the active backend */
void set_backend(enum backend_type type);

/* Get the active backend type */
enum backend_type get_backend(void);

/* Get active backend ops (convenience function) */
struct backend_ops *get_active_ops(void);

/* External backend ops declarations */
extern struct backend_ops jml_ops;
extern struct backend_ops dafny_ops;

#endif /* BACKEND_H */
