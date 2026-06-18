#ifndef DAFNY_H
#define DAFNY_H

#include "cst.h"
#include "backend.h"

/*
    Dafny backend for HAFIS compiler.
    This header defines Dafny-specific helper functions for generating
    Dafny specifications from meaning representation.
*/

/*
    Dafny-specific helper functions (equivalent to jml.c functions)
*/

/* Array/sequence operations */
char *dafny_array_equal(char *d1, char *d2);
char *dafny_array_partially_equal(char *d1, char *d2);
char *dafny_list_2_array_equal(char *d1, char *d2);
char *dafny_list_2_string_array_equal(char *d1, char *d2);
char *dafny_string_charArray_equal(char *d1, char *d2);
char *dafny_contain_only_string_chararray(char *d1, char *d2);
char *dafny_contain_only_template(char *d1, char *d2, struct datatype *dt1, struct datatype *dt2);
char *dafny_contain_construct(char *d1, char *d2, struct datatype *dt1, struct datatype *dt2);

/* Get length representation for Dafny types */
char *dafny_get_length_repr(struct datatype *dt);

/* Get access representation for Dafny types */
char *dafny_get_access_repr(struct datatype *dt, int mode);

/* Dafny quantifier expressions */
char *dafny_forall_expr(char *var, char *range_start, char *range_end, char *body);
char *dafny_exists_expr(char *var, char *range_start, char *range_end, char *body);

/* Dafny keywords */
char *dafny_result_keyword(void);
char *dafny_old_expr(char *expr);

/* Code generation */
void dafny_output(struct astnode *root);

/* Initialize backend operations structure for Dafny */
void dafny_init_ops(void);

#endif /* DAFNY_H */
