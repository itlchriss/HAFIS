/*
    JML Backend Operations for HAFIS compiler.
    
    This file implements the JML-specific backend_ops for code generation.
    The SI synthesis operations remain in jml.c.
    
    JML Quantifier Syntax:
    - Universal: \forall int i; 0 <= i < n.length; P(i)
    - Existential: \exists int i; 0 <= i < n.length; P(i)
    
    JML Length Access:
    - Array: .length
    - String: .length()
    - Collection: .size()
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"
#include "util.h"
#include "backend.h"

/* =========================================================================
   JML Code Generation Operations
   ========================================================================= */

/*
    Emit JML universal quantifier header.
    Format: \forall int i; 0 <= i < type_name.length_str; 
*/
static void jml_emit_forall(FILE *s, char var, const char *type_name, 
                            const char *length_str) {
    fprintf(s, "\\ forall int %c; 0 <= %c < %s.%s; ",
            var, var, type_name, length_str);
}

/*
    Emit JML existential quantifier header.
    Format: \exists int i; 0 <= i < type_name.length_str; 
*/
static void jml_emit_exists(FILE *s, char var, const char *type_name,
                            const char *length_str) {
    fprintf(s, "\\ exists int %c; 0 <= %c < %s.%s; ",
            var, var, type_name, length_str);
}

/*
    Emit JML forall without range (for custom ranges).
    Format: \forall int i; 
*/
static void jml_emit_forall_no_range(FILE *s, char var) {
    fprintf(s, "\\ forall int %c; ", var);
}

/*
    Emit JML exists for sequences (JML doesn't have |x| notation).
    Falls back to standard format with size().
*/
static void jml_emit_exists_seq(FILE *s, char var, const char *type_name) {
    fprintf(s, "\\ exists int %c; 0 <= %c < %s.size(); ",
            var, var, type_name);
}

/*
    Emit JML forall for sequences (JML doesn't have |x| notation).
    Falls back to standard format with size().
*/
static void jml_emit_forall_seq(FILE *s, char var, const char *type_name) {
    fprintf(s, "\\ forall int %c; 0 <= %c < %s.size() ==> ",
            var, var, type_name);
}

/*
    Get JML length string for a reference datatype.
    - Array: "length"
    - String: "length()"
    - Collection/List: "size()"
*/
static const char *jml_get_length_str(enum reference_datatype r) {
    switch (r) {
        case Array:
        case String_Array:
            return "length";
        case String:
            return "length()";
        case List:
            return "size()";
        default:
            return "size()";
    }
}

/* =========================================================================
   JML Backend Operations Structure
   ========================================================================= */

struct backend_ops jml_ops = {
    .name = "jml",
    
    /* Code generation operations */
    .emit_forall = jml_emit_forall,
    .emit_exists = jml_emit_exists,
    .emit_forall_no_range = jml_emit_forall_no_range,
    .emit_exists_seq = jml_emit_exists_seq,
    .emit_forall_seq = jml_emit_forall_seq,
    .get_length_str = jml_get_length_str,
    
    /* SI synthesis operations are in jml.c - set to NULL here */
    .get_length_repr = NULL,
    .get_access_repr = NULL,
    .forall_expr = NULL,
    .exists_expr = NULL,
    .result_keyword = NULL,
    .old_expr = NULL,
    .array_equal = NULL,
    .contain_expr = NULL
};
