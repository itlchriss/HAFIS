/*
    Dafny SI Synthesis Helpers for HAFIS compiler.
    
    This file contains Dafny-specific SI synthesis operations:
    array equality, containment, quantifier expressions, etc.
    The backend_ops struct is defined in dafny_codegen.c.
    
    Key differences from JML:
    - Quantifiers: forall i :: 0 <= i < n ==> P(i)
    - Existential: exists i :: 0 <= i < n && P(i)
    - Result: named return variable (vs \result)
    - Old value: old(x) (vs \old(x))
    - Array length: x.Length (for arrays) or |x| (for sequences)
    - Equality: a == b (vs Arrays.equals(a, b))
*/

#include "dafny.h"
#include "util.h"
#include "stdlib.h"
#include "string.h"
#include "si.h"
#include "sshare.h"
#include "error.h"
#include "backend.h"
#include <stdio.h>

extern struct queue *silist;

/* External function from jml_synth.c */
extern int __match_si_with_datatype_char_only__(void *_si, void *_datatype);

/*
    Dafny backend implementation for HAFIS compiler.
    This file contains Dafny-specific helper functions for generating
    Dafny specifications from meaning representation.
    
    Key differences from JML:
    - Quantifiers: forall i :: 0 <= i < n ==> P(i)  (vs \forall int i; 0 <= i < n; P(i))
    - Existential: exists i :: 0 <= i < n && P(i)   (vs \exists int i; 0 <= i < n; P(i))
    - Result: named return variable (vs \result)
    - Old value: old(x) (vs \old(x))
    - Array length: x.Length (for arrays) or |x| (for sequences)
    - Sequence access: x[i] (same as JML)
    - Equality: a == b (vs Arrays.equals(a, b))
*/

/* Helper function declarations (internal) */
char *dafny_array_equals_primitive_1_var_1_direct(char *var, char *sym);
char *dafny_array_equals_2_vars(char *var1, char *var2);
char *dafny_process_csv_checking(char *d1, char *data, char *length_repr, char *access_expr);

int dafny_is_primitive(char *s) {
    if ((ssearch(s, ",") == FALSE && 
        (isInt(s) == TRUE || isChar(s) == TRUE)) || isString(s) == TRUE) {
            return TRUE;
        }
    else {
        return FALSE;
    }
}

void dafny_itoa(int N, char *str) {
    int i = 0;

    if (N == 0) {
        str[0] = '0';
        str[1] = '\0';
        return;
    }
  
    int sign = N;

    if (N < 0)
        N = -N;

    while (N > 0) {
        str[i++] = N % 10 + '0';
        N /= 10;
    } 

    if (sign < 0) {
        str[i++] = '-';
    }

    str[i] = '\0';

    for (int j = 0, k = i - 1; j < k; j++, k--) {
        char temp = str[j];
        str[j] = str[k];
        str[k] = temp;
    }
}

/*
    Get length representation for Dafny types
    - Array: .Length
    - String: .Length (Dafny strings are sequences)
    - List/Sequence: |x| (cardinality)
*/
char *dafny_get_length_repr(struct datatype *dt) {
    if (dt->r == String) {
        return ".Length";
    } else if (dt->r == Array || dt->r == String_Array || dt->r == Array2 || dt->r == Array3) {
        return ".Length";
    } else if (dt->r == List || dt->r == Seq) {
        return "|%x|";  /* Dafny sequence cardinality */
    } else if (dt->r == Set || dt->r == ISet) {
        return "|%x|";  /* Dafny set cardinality */
    } else if (dt->r == Multiset) {
        return "|%x|";  /* Dafny multiset cardinality */
    } else if (dt->r == Map || dt->r == IMap) {
        return "|%x|";  /* Dafny map cardinality */
    } else {
        return NULL;
    }
}

/*
    Get access representation for Dafny types
    Dafny uses uniform indexing: x[i] for arrays, strings, and sequences
*/
char *dafny_get_access_repr(struct datatype *dt, int mode) {
    if (dt->r == String) {
        if (mode == 0) {
            return "%x[i]";  /* Dafny strings support indexing */
        } else {
            return "%x[%i]";
        }
    } else if (dt->r == Array || dt->r == String_Array || dt->r == Array2 || dt->r == Array3) {
        if (mode == 0) {
            return "%x[i]";
        } else {
            return "%x[%i]";
        }
    } else if (dt->r == List || dt->r == Seq) {
        if (mode == 0) {
            return "%x[i]";  /* Dafny sequences support indexing */
        } else {
            return "%x[%i]";
        }
    } else {
        return NULL;
    }
}

/*
    Dafny universal quantifier:
    forall i :: 0 <= i < n ==> P(i)
*/
char *dafny_forall_expr(char *var, char *range_start, char *range_end, char *body) {
    char *tmp = "forall %v:int :: %s <= %v < %e ==> %b";
    char *result = strrep(tmp, "%v", var);
    char *tmp2 = strrep(result, "%s", range_start);
    free(result);
    result = strrep(tmp2, "%e", range_end);
    free(tmp2);
    tmp2 = strrep(result, "%b", body);
    free(result);
    return tmp2;
}

/*
    Dafny existential quantifier:
    exists i :: 0 <= i < n && P(i)
*/
char *dafny_exists_expr(char *var, char *range_start, char *range_end, char *body) {
    char *tmp = "exists %v:int :: %s <= %v < %e && %b";
    char *result = strrep(tmp, "%v", var);
    char *tmp2 = strrep(result, "%s", range_start);
    free(result);
    result = strrep(tmp2, "%e", range_end);
    free(tmp2);
    tmp2 = strrep(result, "%b", body);
    free(result);
    return tmp2;
}

/*
    Dafny result keyword - uses named return variable
    For now, return a placeholder that the user should replace
*/
char *dafny_result_keyword(void) {
    return "r";  /* Named return variable - convention */
}

/*
    Dafny old value expression
    old(x) instead of \old(x)
*/
char *dafny_old_expr(char *expr) {
    char *tmp = "old(%e)";
    return strrep(tmp, "%e", expr);
}

/*
    Dafny array partially equal - check partial array equality
*/
char *dafny_array_partially_equal(char *d1, char *d2) {
    char *pos, *pos1;
    char *_s = strtok_r(d2, "_", &pos);
    char *startchr = (char *)strdup(_s);
    char *endchr = strdup(strtok_r(NULL, "_", &pos));
    char *seq = strdup(strtok_r(NULL, "_", &pos));

    char *result = NULL;
    char *tmp = "%x[%i] == %s";
    char *_a = strrep(tmp, "%x", d1);
    _s = strtok_r(seq, ",", &pos1);
    int start = (int)(startchr[0] - '0'), end = (int)(endchr[0] - '0');
    for (int i = start; i <= end; ++i) {
        char num[10];
        dafny_itoa(i, num);
        char *_b = strrep(_a, "%i", num);
        char *_target = strrep(_b, "%s", _s);
        free(_b);
        if (result != NULL) {
            result = combine_strings(3, result, " && ", _target);
        } else {
            result = _target;
        }
        _s = strtok_r(NULL, ",", &pos1);
    }
    free(startchr);
    free(endchr);
    free(seq);
    free(_a);
    return result;
}

/*
    Process CSV checking for Dafny
*/
char *dafny_process_csv_checking(char *d1, char *data, char *length_repr, char *access_expr) {
    char *pos;
    char *_t = strtok_r(data, ",", &pos), *connective = NULL;
    if (strcmp(_t, "or") == 0) {
        connective = " || ";
    } else {
        connective = " && ";
    }
    /* Dafny: forall i:int :: 0 <= i < x.Length ==> P(i) */
    char *head = "forall i:int :: 0 <= i < %x%l ==> %e";
    char *result = strrep(head, "%x", d1);
    char *tmp = strrep(result, "%l", length_repr);
    free(result);
    result = tmp;
    char *expr = NULL;
    _t = strtok_r(NULL, ",", &pos);
    while (_t != NULL) {
        if (strcmp(_t, "range") == 0) {
            char *start = strtok_r(NULL, ",", &pos);
            char *end = strtok_r(NULL, ",", &pos);
            char *tmp_str = strdup("%s <= (%a) as int && (%a) as int <= %e"); 
            char *_target = strrep(tmp_str, "%s", start);
            free(tmp_str);
            tmp_str = (char *)strdup(_target);
            free(_target);
            _target = strrep(tmp_str, "%e", end);
            free(tmp_str);
            tmp_str = strrep(_target, "%a", access_expr);
            free(_target);
            _target = strrep(tmp_str, "%x", d1);
            free(tmp_str);
            if (expr == NULL) {
                expr = _target;
            } else {
                expr = combine_strings(3, expr, connective, _target);
            }
        } else if (strcmp(_t, "equal") == 0) {
            char *c = strtok_r(NULL, ",", &pos);
            char *tmp_str = "%a == %s";
            char *_target = strrep(tmp_str, "%s", c);
            tmp_str = (char *)strdup(_target);
            free(_target);
            _target = strrep(tmp_str, "%a", access_expr);
            free(tmp_str);            
            tmp_str = strrep(_target, "%x", d1);
            free(_target);
            _target = tmp_str;
            if (expr == NULL) {
                expr = _target;
            } else {
                expr = combine_strings(3, expr, connective, _target);
            }
        }
        _t = strtok_r(NULL, ",", &pos);
    }
    tmp = strrep(result, "%e", expr);
    free(result);
    return tmp;
}

/*
    Dafny contain construct
*/
char *dafny_contain_construct(char *d1, char *d2, struct datatype *dt1, struct datatype *dt2) {
    struct si *si = searchqueue(silist, d2, __match_si_with_symbol_only__);
    if (si == NULL) {
        sinotfound_error(d2);
    }
    char *cdata = (char *)strdup(si->interpretation);
    char *length_repr = dafny_get_length_repr(dt1);
    if (strcmp(d2, "csvdata") == 0 && length_repr != NULL) {
        char *access_repr = dafny_get_access_repr(dt1, 0);
        char *result = dafny_process_csv_checking(d1, cdata, length_repr, access_repr);
        free(cdata);
        return result;
    } else {
        if (length_repr == NULL) {
            internal_error("The construct provided is not supported.");
        }
        internal_error("The construct provided is not supported.");
    }
    free(cdata);
    return NULL;
}

/*
    Dafny contain only string chararray
*/
char *dafny_contain_only_string_chararray(char *d1, char *d2) {
    char *pos;
    char *_t = strtok_r(d2, ",", &pos), *connective = NULL;
    if (strcmp(_t, "or") == 0) {
        connective = " || ";
    } else {
        connective = " && ";
    }
    /* Dafny: forall i:int :: 0 <= i < x.Length ==> P(i) */
    char *head = "forall i:int :: 0 <= i < %x.Length ==> ";
    char *result = strrep(head, "%x", d1);    
    _t = strtok_r(NULL, ",", &pos);
    char *end = NULL;
    while (_t != NULL) {
        char *interpretation = NULL, *tmp;
        if (_t[0] == '\'' && _t[strlen(_t) - 1] == '\'') {
            interpretation = _t;
            tmp = "%x[i] == %s";  /* Dafny string indexing */
        } else {
            struct queue *siq = q_searchqueue(silist, _t, __match_si_with_symbol_only__);
            if (siq == NULL || siq->count == 0) {
                sinotfound_error(_t);
            }
            struct si *si = searchqueue(siq, _t, __match_si_with_datatype_char_only__);
            if (si == NULL) {
                sinotfound_error(_t);
            }
            interpretation = si->interpretation;
            if (si->type == SI_INT_TYPE_JAVA_METHOD) {
                tmp = "%s(%x[i])";  /* Dafny function call */
            } else {
                tmp = "%x[i] == %s";
            }
        }        
        char *_target = strrep(tmp, "%s", interpretation);        
        tmp = (char *)strdup(_target);
        free(_target);
        _target = strrep(tmp, "%x", d1);
        free(tmp);        
        if (end != NULL) {
            end = combine_strings(3, end, connective, _target);
        } else {
            end = _target;
        }
        _t = strtok_r(NULL, ",", &pos);
    }
    result = combine_strings(2, result, end);
    return result;
}

/*
    Dafny list to array equal
*/
char *dafny_list_2_array_equal(char *d1, char *d2) {
    return dafny_list_2_string_array_equal(d1, d2);
}

/*
    Dafny list to string array equal
*/
char *dafny_list_2_string_array_equal(char *d1, char *d2) {
    char *pos;
    char *_t = strtok_r(d2, ",", &pos);
    int c = 0;
    char *result = NULL;
    char *tmp = "%x[%i] == %s";  /* Dafny sequence indexing */
    char *_a = strrep(tmp, "%x", d1);
    while (_t != NULL) {
        char num[10];
        dafny_itoa(c, num);
        char *_b = strrep(_a, "%i", num);
        char *_target = strrep(_b, "%s", _t);
        free(_b);
        if (result != NULL) {
            result = combine_strings(3, result, " && ", _target);
        } else {
            result = _target;
        }
        c++;
        _t = strtok_r(NULL, ",", &pos);
    }
    free(_a);
    return result;
}

/*
    Dafny array equal
    In Dafny, arrays/sequences use == for equality (no Arrays.equals needed)
*/
char *dafny_array_equal(char *d1, char *d2) {
    int _d1_direct_value = ssearch(d1, ",");
    int _d2_direct_value = ssearch(d2, ",");
    if (_d1_direct_value == TRUE && _d2_direct_value == TRUE) {
        /* Both are direct values - rare case */
        return dafny_array_equals_2_vars(d1, d2);
    } else if (_d1_direct_value == TRUE || dafny_is_primitive(d1) == TRUE) {
        return dafny_array_equals_primitive_1_var_1_direct(d2, d1);
    } else if (_d2_direct_value == TRUE || dafny_is_primitive(d2) == TRUE) {
        return dafny_array_equals_primitive_1_var_1_direct(d1, d2);
    } else {
        /* In Dafny, sequence equality is simply == */
        char *result = combine_strings(3, d1, " == ", d2);
        return result;
    }
}

/*
    Dafny array equals with one variable and one direct value
*/
char *dafny_array_equals_primitive_1_var_1_direct(char *var, char *sym) {
    char *pos;
    char *_t = strtok_r(sym, ",", &pos);
    int c = 0;
    char *result = NULL;
    char *tmp = "%x[%i] == %d";
    char *_a = strrep(tmp, "%x", var);
    while (_t != NULL) {
        char num[10];
        dafny_itoa(c, num);
        char *_b = strrep(_a, "%i", num);
        char *_target = strrep(_b, "%d", _t);
        free(_b);
        if (result != NULL) {
            result = combine_strings(3, result, " && ", _target);
        } else {
            result = _target;
        }
        c++;
        _t = strtok_r(NULL, ",", &pos);
    }
    char num[10];
    dafny_itoa(c, num);
    result = combine_strings(5, result, " && ", var, ".Length == ", num);
    free(_a);
    return result;
}

/*
    Dafny array equals with two variables
    In Dafny: forall i :: 0 <= i < x.Length ==> x[i] == y[i]
*/
char *dafny_array_equals_2_vars(char *var1, char *var2) {
    char *tmp = "forall i:int :: 0 <= i < %x.Length ==> %x[i] == %y[i]";
    char *a = strrep(tmp, "%x", var1);
    char *result = strrep(a, "%y", var2);
    free(a);
    return result;
}

/*
    Dafny string charArray equal
*/
char *dafny_string_charArray_equal(char *d1, char *d2) {
    int _d1_direct_value = ssearch(d1, ",");
    
    char *string, *charArray;
    if (!_d1_direct_value) {
        string = d1;
        charArray = d2;
    } else {
        charArray = d1;
        string = d2;
    }

    char *pos;
    char *_t = strtok_r(charArray, ",", &pos), *connective = NULL;
    if (strcmp(_t, "or") == 0) {
        connective = " || ";
    } else {
        connective = " && ";
    }
    /* Dafny: forall i:int :: 0 <= i < x.Length ==> P(i) */
    char *head = "forall i:int :: 0 <= i < %x.Length ==> ";
    char *result = strrep(head, "%x", string);    
    _t = strtok_r(NULL, ",", &pos);
    char *end = NULL;
    while (_t != NULL) {
        char *tmp = "%x[i] == %s";  /* Dafny string indexing */
        char *c = NULL;
        if (strlen(_t) == 1 || (_t[0] != '\'' && _t[strlen(_t) - 1] != '\'')) {
            c = __combine_3_strings__("\'", _t, "\'");    
        } else {
            c = strdup(_t);
        }
        char *_target = strrep(tmp, "%s", c);
        free(c);        
        tmp = (char *)strdup(_target);
        free(_target);
        _target = strrep(tmp, "%x", string);
        free(tmp);        
        if (end != NULL) {
            end = combine_strings(3, end, connective, _target);
        } else {
            end = _target;
        }
        _t = strtok_r(NULL, ",", &pos);
    }
    result = combine_strings(2, result, end);
    return result;
}

/*
    Dafny contain only template
*/
int __match_si_with_template_datatype_only_dafny__(void *_si, void *_datatype) {
    struct si *si = (struct si *)_si;
    struct datatype *dt = (struct datatype *)_datatype;
    struct si_arg *arg = (struct si_arg *)gqueue(si->args, 0);
    if (__compare_datatype__(arg->datatype, dt) == TRUE) {
        return TRUE;
    } else {
        return FALSE;
    }
}

char *dafny_contain_only_template(char *d1, char *d2, struct datatype *dt1, struct datatype *dt2) {
    int _d1_direct_value = ssearch(d1, "__TEMPLATE__");
    struct datatype *dt_direct;
    
    char *direct, *template;
    if (!_d1_direct_value) {
        direct = d1;
        template = d2;
        dt_direct = dt1;
    } else {
        template = d1;
        direct = d2;
        dt_direct = dt2;
    }

    struct queue *siq = q_searchqueue(silist, template, __match_si_with_symbol_only__);
    if (siq == NULL) {
        sinotfound_error(template);
    }
    struct si *si = searchqueue(siq, dt_direct, __match_si_with_template_datatype_only_dafny__);
    if (si == NULL) {
        sinotfound_error(template);
    }
    char *tmp = combine_strings(3, "(", ((struct si_arg *)gqueue(si->args, 0))->symbol, ")");
    char *result = strrep(si->interpretation, tmp, direct);
    free(tmp);
    return result;
}

/*
    Backend operations structure for Dafny.
    The dafny_ops struct is defined in dafny_codegen.c.
    We populate the SI synthesis operations here.
*/
extern struct backend_ops dafny_ops;

void dafny_init_ops(void) {
    dafny_ops.get_length_repr = dafny_get_length_repr;
    dafny_ops.get_access_repr = dafny_get_access_repr;
    dafny_ops.forall_expr = dafny_forall_expr;
    dafny_ops.exists_expr = dafny_exists_expr;
    dafny_ops.result_keyword = dafny_result_keyword;
    dafny_ops.old_expr = dafny_old_expr;
    dafny_ops.array_equal = dafny_array_equal;
    dafny_ops.contain_expr = NULL;  /* To be implemented */
}
