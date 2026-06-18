/*
    Dafny Backend Operations for HAFIS compiler.
    
    This file implements the Dafny-specific backend_ops for code generation.
    The SI synthesis operations are in dafny_synth.c.
    
    Dafny Quantifier Syntax:
    - Universal: forall i:int :: 0 <= i < n.Length ==> P(i)
    - Existential: exists i:int :: 0 <= i < n.Length && P(i)
    
    Dafny Length Access:
    - Array: .Length
    - String: .Length (sequences of chars)
    - Sequence: |x| (cardinality)
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"
#include "util.h"
#include "backend.h"

/* =========================================================================
   Dafny Code Generation Operations
   ========================================================================= */

/*
    Emit Dafny universal quantifier header.
    Format: forall i:int :: 0 <= i < type_name.length_str ==> 
*/
static void dafny_cg_emit_forall(FILE *s, char var, const char *type_name,
                                  const char *length_str) {
    fprintf(s, "forall %c:int :: 0 <= %c < %s%s ==> ",
            var, var, type_name, length_str);
}

/*
    Emit Dafny existential quantifier header.
    Format: exists i:int :: 0 <= i < type_name.length_str && 
*/
static void dafny_cg_emit_exists(FILE *s, char var, const char *type_name,
                                  const char *length_str) {
    fprintf(s, "exists %c:int :: 0 <= %c < %s%s && ",
            var, var, type_name, length_str);
}

/*
    Emit Dafny forall without range (for custom ranges).
    Format: forall i:int :: 
*/
static void dafny_cg_emit_forall_no_range(FILE *s, char var) {
    fprintf(s, "forall %c:int :: ", var);
}

/*
    Emit Dafny exists for sequences (use |x| notation).
    Format: exists i:int :: 0 <= i < |type_name| && 
*/
static void dafny_cg_emit_exists_seq(FILE *s, char var, const char *type_name) {
    fprintf(s, "exists %c:int :: 0 <= %c < |%s| && ",
            var, var, type_name);
}

/*
    Emit Dafny forall for sequences (use |x| notation).
    Format: forall i:int :: 0 <= i < |type_name| ==> 
*/
static void dafny_cg_emit_forall_seq(FILE *s, char var, const char *type_name) {
    fprintf(s, "forall %c:int :: 0 <= %c < |%s| ==> ",
            var, var, type_name);
}

/*
    Get Dafny length string for a reference datatype.
    - Array: ".Length"
    - String: ".Length" (Dafny strings are sequences)
    - List/Sequence: NULL (use |x| notation)
*/
static const char *dafny_cg_get_length_str(enum reference_datatype r) {
    switch (r) {
        case Array:
        case String_Array:
        case String:
            return ".Length";
        case List:
            return NULL;  /* Use |x| notation for sequences */
        default:
            return ".Length";
    }
}

/* =========================================================================
   Dafny Backend Operations Structure
   ========================================================================= */

struct backend_ops dafny_ops = {
    .name = "dafny",
    
    /* Code generation operations */
    .emit_forall = dafny_cg_emit_forall,
    .emit_exists = dafny_cg_emit_exists,
    .emit_forall_no_range = dafny_cg_emit_forall_no_range,
    .emit_exists_seq = dafny_cg_emit_exists_seq,
    .emit_forall_seq = dafny_cg_emit_forall_seq,
    .get_length_str = dafny_cg_get_length_str,
    
    /* SI synthesis operations are in dafny.c - set to NULL here */
    .get_length_repr = NULL,
    .get_access_repr = NULL,
    .forall_expr = NULL,
    .exists_expr = NULL,
    .result_keyword = NULL,
    .old_expr = NULL,
    .array_equal = NULL,
    .contain_expr = NULL
};
