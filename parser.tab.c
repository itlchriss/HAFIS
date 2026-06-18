/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison implementation for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output, and Bison version.  */
#define YYBISON 30802

/* Bison version string.  */
#define YYBISON_VERSION "3.8.2"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* First part of user prologue.  */
#line 1 "./src/c.2024/parser.y"

    #include "core.h"
    #include "util.h"
    #include "cst.h"
    #include "si.h"
    #include "si_runtime.h"
    #include "event.h"
    #include "regex.h"
    #include "error.h"
    #include "tok.h"
    #define YYERROR_VERBOSE 1

    void print_debug(char *);
    void print_semantic_error(char *);

    // From main.c
    // extern int c;
    extern struct astnode *ast;  
    extern struct queue *predicates, *operators;
    extern struct queue *events, *silist;


    /* for parsing only */
    extern struct queue *_events, *_datarefs;

    struct _dataref {
        struct astnode *node;
        /* datatype of the node, if there is a type_term before the symbol is declared */
        // enum explicit_datatype datatype;
        struct datatype *datatype;
    };

    struct _event {
        struct astnode *_subtree_root;
        struct astnode *_eventnode;
        struct astnode *_entitynode;
    };

    int __tmp_event_match_entity_variable__(void *_e, void *_entity_variable) {
        struct _event *e = (struct _event *)_e;
        char *entity_var = (char *)_entity_variable;
        if (strcmp(e->_entitynode->token->symbol, entity_var) == 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    enum gramtype __string2gramtype(char *input) {
        if (strcmp(input, "Subj") == 0) return SubjectOf;
        else if (strcmp(input, "Acc") == 0) return AccusationOf;
        else if (strcmp(input, "AccI") == 0) return IntentionalAccusationOf; 
        else if (strcmp(input, "Dat") == 0) return Dative;
        else if (strcmp(input, "Gen") == 0) return Genitive; 
        else if (strcmp(input, "Abl") == 0) return Ablative;
        else if (strcmp(input, "Rel") == 0) return Relative;
        else if (strcmp(input, "Voc") == 0) return Vocative; 
        else if (strcmp(input, "AccE") == 0) return ExtentionalAccusationOf;
        else {
            fprintf(stderr, "Unknown Grammar type %s in the MR\n", input);
            exit(-2);
        }
    }

    struct _event *newtmpevent(struct astnode *_subtree_root, struct astnode *_eventnode, struct astnode *_entitynode) {
        struct _event *new = (struct _event *)malloc(sizeof(struct _event));
        new->_subtree_root = _subtree_root;
        new->_eventnode = _eventnode;
        new->_entitynode = _entitynode;
        return new;
    }

    // struct _dataref *newtmpdataref(struct astnode *_node, enum explicit_datatype datatype) {
    struct _dataref *newtmpdataref(struct astnode *_node, struct datatype *_datatype) {
        struct _dataref *new = (struct _dataref *)malloc(sizeof(struct _dataref));
        new->node = _node;
        new->datatype = _datatype;
        return new;
    }

    void pruneeventsubtrees() {
        while (!isempty(_events)) {
            struct _event *_e = (struct _event*)dequeue(_events);
            struct event *event = newevent(_e->_eventnode->cstptr);
            enqueue(event->entities, (void *)newentity(_e->_entitynode->cstptr, __string2gramtype(_e->_subtree_root->token->symbol)));

            deleteastnodeandedge(_e->_subtree_root, ast);
        }
    }

    int __search_syntax(struct si* si, enum ptbsyntax ptb) {
        for (int i = 0; i < si->syntax->count; ++i) {
            if ((enum ptbsyntax)gqueue(si->syntax, i) == ptb) {
                return TRUE;
            }
        }
        return FALSE;
    }

    int __javatype_simatcher(void *_si, void *_astnode) {
        struct si* si = (struct si*)_si;
        struct astnode *node = (struct astnode *)_astnode;
        if (strcmp(node->token->symbol, si->symbol) == 0 &&
                    __search_syntax(si, node->syntax) == TRUE) 
            return TRUE;
        else
            return FALSE;
    }

    struct queue *search_symbols(struct astnode *subtree_root, char *symbol) {
        struct queue *queue = initqueue();
        struct queue *cstptrs = initqueue();
        for (int i = 0; i < countastchildren(subtree_root); ++i) {
            enqueue(queue, (void *)getastchild(subtree_root, i));
        }
        while (!isempty(queue)) {
            struct astnode *node = (struct astnode *)dequeue(queue);            
            if ((node->type == Variable || node->type == EventEntity || node->type == EventVariable) && strcmp(node->token->symbol, symbol) == 0 && node->cstptr == NULL) {
                enqueue(cstptrs, (void *)node);
            }
            for (int i = 0; i < countastchildren(node); ++i) {
                enqueue(queue, (void *)getastchild(node, i));
            }
        }        
        deallocatequeue(queue, NULL);
        return cstptrs;
    }


    // c is for the line counter of hols
    int lbracs = 0, rbracs = 0, lineNum = 1, colNum = 1, error_count = 0;

#line 204 "parser.tab.c"

# ifndef YY_CAST
#  ifdef __cplusplus
#   define YY_CAST(Type, Val) static_cast<Type> (Val)
#   define YY_REINTERPRET_CAST(Type, Val) reinterpret_cast<Type> (Val)
#  else
#   define YY_CAST(Type, Val) ((Type) (Val))
#   define YY_REINTERPRET_CAST(Type, Val) ((Type) (Val))
#  endif
# endif
# ifndef YY_NULLPTR
#  if defined __cplusplus
#   if 201103L <= __cplusplus
#    define YY_NULLPTR nullptr
#   else
#    define YY_NULLPTR 0
#   endif
#  else
#   define YY_NULLPTR ((void*)0)
#  endif
# endif

#include "parser.tab.h"
/* Symbol kind.  */
enum yysymbol_kind_t
{
  YYSYMBOL_YYEMPTY = -2,
  YYSYMBOL_YYEOF = 0,                      /* "end of file"  */
  YYSYMBOL_YYerror = 1,                    /* error  */
  YYSYMBOL_YYUNDEF = 2,                    /* "invalid token"  */
  YYSYMBOL_PREDICATE = 3,                  /* PREDICATE  */
  YYSYMBOL_IDENTIFIER = 4,                 /* IDENTIFIER  */
  YYSYMBOL_KEYWORD_TRUEP = 5,              /* KEYWORD_TRUEP  */
  YYSYMBOL_6_ = 6,                         /* '.'  */
  YYSYMBOL_NEG = 7,                        /* NEG  */
  YYSYMBOL_COMMA = 8,                      /* COMMA  */
  YYSYMBOL_9_ = 9,                         /* '('  */
  YYSYMBOL_10_ = 10,                       /* ')'  */
  YYSYMBOL_EQUAL = 11,                     /* EQUAL  */
  YYSYMBOL_AND = 12,                       /* AND  */
  YYSYMBOL_OR = 13,                        /* OR  */
  YYSYMBOL_IMPLY = 14,                     /* IMPLY  */
  YYSYMBOL_EQUIV = 15,                     /* EQUIV  */
  YYSYMBOL_16_ = 16,                       /* '{'  */
  YYSYMBOL_17_ = 17,                       /* '}'  */
  YYSYMBOL_KEYWORD_QUANTIFIER = 18,        /* KEYWORD_QUANTIFIER  */
  YYSYMBOL_KEYWORD_TYPE = 19,              /* KEYWORD_TYPE  */
  YYSYMBOL_KEYWORD_PARAM = 20,             /* KEYWORD_PARAM  */
  YYSYMBOL_TAG = 21,                       /* TAG  */
  YYSYMBOL_EVENT = 22,                     /* EVENT  */
  YYSYMBOL_YYACCEPT = 23,                  /* $accept  */
  YYSYMBOL_formula = 24,                   /* formula  */
  YYSYMBOL_terms = 25,                     /* terms  */
  YYSYMBOL_term = 26,                      /* term  */
  YYSYMBOL_connective = 27,                /* connective  */
  YYSYMBOL_grammar_term = 28,              /* grammar_term  */
  YYSYMBOL_pronoun_term = 29,              /* pronoun_term  */
  YYSYMBOL_type_term = 30,                 /* type_term  */
  YYSYMBOL_param_term = 31,                /* param_term  */
  YYSYMBOL_predicate_term = 32,            /* predicate_term  */
  YYSYMBOL_arguments = 33,                 /* arguments  */
  YYSYMBOL_argument = 34,                  /* argument  */
  YYSYMBOL_event_term = 35,                /* event_term  */
  YYSYMBOL_quantified_term = 36            /* quantified_term  */
};
typedef enum yysymbol_kind_t yysymbol_kind_t;




#ifdef short
# undef short
#endif

/* On compilers that do not define __PTRDIFF_MAX__ etc., make sure
   <limits.h> and (if available) <stdint.h> are included
   so that the code can choose integer types of a good width.  */

#ifndef __PTRDIFF_MAX__
# include <limits.h> /* INFRINGES ON USER NAME SPACE */
# if defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stdint.h> /* INFRINGES ON USER NAME SPACE */
#  define YY_STDINT_H
# endif
#endif

/* Narrow types that promote to a signed type and that can represent a
   signed or unsigned integer of at least N bits.  In tables they can
   save space and decrease cache pressure.  Promoting to a signed type
   helps avoid bugs in integer arithmetic.  */

#ifdef __INT_LEAST8_MAX__
typedef __INT_LEAST8_TYPE__ yytype_int8;
#elif defined YY_STDINT_H
typedef int_least8_t yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef __INT_LEAST16_MAX__
typedef __INT_LEAST16_TYPE__ yytype_int16;
#elif defined YY_STDINT_H
typedef int_least16_t yytype_int16;
#else
typedef short yytype_int16;
#endif

/* Work around bug in HP-UX 11.23, which defines these macros
   incorrectly for preprocessor constants.  This workaround can likely
   be removed in 2023, as HPE has promised support for HP-UX 11.23
   (aka HP-UX 11i v2) only through the end of 2022; see Table 2 of
   <https://h20195.www2.hpe.com/V2/getpdf.aspx/4AA4-7673ENW.pdf>.  */
#ifdef __hpux
# undef UINT_LEAST8_MAX
# undef UINT_LEAST16_MAX
# define UINT_LEAST8_MAX 255
# define UINT_LEAST16_MAX 65535
#endif

#if defined __UINT_LEAST8_MAX__ && __UINT_LEAST8_MAX__ <= __INT_MAX__
typedef __UINT_LEAST8_TYPE__ yytype_uint8;
#elif (!defined __UINT_LEAST8_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST8_MAX <= INT_MAX)
typedef uint_least8_t yytype_uint8;
#elif !defined __UINT_LEAST8_MAX__ && UCHAR_MAX <= INT_MAX
typedef unsigned char yytype_uint8;
#else
typedef short yytype_uint8;
#endif

#if defined __UINT_LEAST16_MAX__ && __UINT_LEAST16_MAX__ <= __INT_MAX__
typedef __UINT_LEAST16_TYPE__ yytype_uint16;
#elif (!defined __UINT_LEAST16_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST16_MAX <= INT_MAX)
typedef uint_least16_t yytype_uint16;
#elif !defined __UINT_LEAST16_MAX__ && USHRT_MAX <= INT_MAX
typedef unsigned short yytype_uint16;
#else
typedef int yytype_uint16;
#endif

#ifndef YYPTRDIFF_T
# if defined __PTRDIFF_TYPE__ && defined __PTRDIFF_MAX__
#  define YYPTRDIFF_T __PTRDIFF_TYPE__
#  define YYPTRDIFF_MAXIMUM __PTRDIFF_MAX__
# elif defined PTRDIFF_MAX
#  ifndef ptrdiff_t
#   include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  endif
#  define YYPTRDIFF_T ptrdiff_t
#  define YYPTRDIFF_MAXIMUM PTRDIFF_MAX
# else
#  define YYPTRDIFF_T long
#  define YYPTRDIFF_MAXIMUM LONG_MAX
# endif
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned
# endif
#endif

#define YYSIZE_MAXIMUM                                  \
  YY_CAST (YYPTRDIFF_T,                                 \
           (YYPTRDIFF_MAXIMUM < YY_CAST (YYSIZE_T, -1)  \
            ? YYPTRDIFF_MAXIMUM                         \
            : YY_CAST (YYSIZE_T, -1)))

#define YYSIZEOF(X) YY_CAST (YYPTRDIFF_T, sizeof (X))


/* Stored state numbers (used for stacks). */
typedef yytype_int8 yy_state_t;

/* State numbers in computations.  */
typedef int yy_state_fast_t;

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif


#ifndef YY_ATTRIBUTE_PURE
# if defined __GNUC__ && 2 < __GNUC__ + (96 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_PURE __attribute__ ((__pure__))
# else
#  define YY_ATTRIBUTE_PURE
# endif
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# if defined __GNUC__ && 2 < __GNUC__ + (7 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_UNUSED __attribute__ ((__unused__))
# else
#  define YY_ATTRIBUTE_UNUSED
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YY_USE(E) ((void) (E))
#else
# define YY_USE(E) /* empty */
#endif

/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
#if defined __GNUC__ && ! defined __ICC && 406 <= __GNUC__ * 100 + __GNUC_MINOR__
# if __GNUC__ * 100 + __GNUC_MINOR__ < 407
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")
# else
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")              \
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# endif
# define YY_IGNORE_MAYBE_UNINITIALIZED_END      \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

#if defined __cplusplus && defined __GNUC__ && ! defined __ICC && 6 <= __GNUC__
# define YY_IGNORE_USELESS_CAST_BEGIN                          \
    _Pragma ("GCC diagnostic push")                            \
    _Pragma ("GCC diagnostic ignored \"-Wuseless-cast\"")
# define YY_IGNORE_USELESS_CAST_END            \
    _Pragma ("GCC diagnostic pop")
#endif
#ifndef YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_END
#endif


#define YY_ASSERT(E) ((void) (0 && (E)))

#if !defined yyoverflow

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* !defined yyoverflow */

#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yy_state_t yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (YYSIZEOF (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (YYSIZEOF (yy_state_t) + YYSIZEOF (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYPTRDIFF_T yynewbytes;                                         \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * YYSIZEOF (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / YYSIZEOF (*yyptr);                        \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, YY_CAST (YYSIZE_T, (Count)) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYPTRDIFF_T yyi;                      \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  31
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   135

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  23
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  14
/* YYNRULES -- Number of rules.  */
#define YYNRULES  34
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  94

/* YYMAXUTOK -- Last valid token kind.  */
#define YYMAXUTOK   272


/* YYTRANSLATE(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, with out-of-bounds checking.  */
#define YYTRANSLATE(YYX)                                \
  (0 <= (YYX) && (YYX) <= YYMAXUTOK                     \
   ? YY_CAST (yysymbol_kind_t, yytranslate[YYX])        \
   : YYSYMBOL_YYUNDEF)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex.  */
static const yytype_int8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       9,    10,     2,     2,     2,     2,     6,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    16,     2,    17,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     7,     8,    11,    12,    13,    14,    15,    18,    19,
      20,    21,    22
};

#if YYDEBUG
/* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_int16 yyrline[] =
{
       0,   180,   180,   199,   212,   227,   240,   253,   261,   271,
     280,   283,   286,   289,   292,   296,   299,   303,   312,   318,
     319,   320,   321,   325,   342,   363,   386,   412,   428,   435,
     440,   447,   451,   458,   471
};
#endif

/** Accessing symbol of state STATE.  */
#define YY_ACCESSING_SYMBOL(State) YY_CAST (yysymbol_kind_t, yystos[State])

#if YYDEBUG || 0
/* The user-facing name of the symbol whose (internal) number is
   YYSYMBOL.  No bounds checking.  */
static const char *yysymbol_name (yysymbol_kind_t yysymbol) YY_ATTRIBUTE_UNUSED;

/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "\"end of file\"", "error", "\"invalid token\"", "PREDICATE",
  "IDENTIFIER", "KEYWORD_TRUEP", "'.'", "NEG", "COMMA", "'('", "')'",
  "EQUAL", "AND", "OR", "IMPLY", "EQUIV", "'{'", "'}'",
  "KEYWORD_QUANTIFIER", "KEYWORD_TYPE", "KEYWORD_PARAM", "TAG", "EVENT",
  "$accept", "formula", "terms", "term", "connective", "grammar_term",
  "pronoun_term", "type_term", "param_term", "predicate_term", "arguments",
  "argument", "event_term", "quantified_term", YY_NULLPTR
};

static const char *
yysymbol_name (yysymbol_kind_t yysymbol)
{
  return yytname[yysymbol];
}
#endif

#define YYPACT_NINF (-35)

#define yypact_value_is_default(Yyn) \
  ((Yyn) == YYPACT_NINF)

#define YYTABLE_NINF (-1)

#define yytable_value_is_error(Yyn) \
  0

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
static const yytype_int16 yypact[] =
{
       6,    -9,     5,   -35,    73,    -1,    29,    23,    31,    26,
      61,   -35,    55,   -35,   -35,   -35,   -35,   -35,   -35,   -35,
      59,    83,    -1,    55,    79,    94,    86,   104,    90,    91,
      25,   -35,   -35,   -35,   -35,   -35,    45,    96,   -35,   106,
       6,   105,   110,    55,   108,    98,   101,     5,   -35,     4,
     -35,    -1,   -35,   111,   -35,   -35,   103,   112,    80,     6,
     114,   115,    25,   -35,   116,    53,   100,   117,    19,   -35,
     119,    25,    25,   -35,    55,    -1,    71,   113,   121,   120,
     -35,    78,    87,   122,   -35,   123,   124,   132,   -35,   -35,
      92,   -35,   -35,   -35
};

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
static const yytype_int8 yydefact[] =
{
       0,     0,     0,    16,     0,     0,     0,     0,     0,     0,
       0,     2,     7,    15,    18,    11,    12,    10,    14,    13,
       0,     0,     0,     8,     0,     0,     0,     0,     0,     0,
       0,     1,    19,    22,    21,    20,     0,     0,    17,     0,
       0,     0,     0,     0,     0,     0,     0,    31,    32,     0,
      30,     0,     4,     0,     9,     3,     0,     0,     0,     0,
       0,     0,     0,    23,     0,     0,     0,     0,     0,     6,
       0,     0,     0,    29,     5,     0,     0,     0,     0,     0,
      34,     0,     0,     0,    27,     0,     0,     0,    25,    26,
       0,    24,    33,    28
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -35,   -35,     0,    -3,    30,   -35,   -35,   -35,   -35,   -35,
     -34,    65,   -35,   -35
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
       0,    10,    48,    12,    58,    13,    14,    15,    16,    17,
      49,    50,    18,    19
};

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int8 yytable[] =
{
      11,    23,     1,    24,     3,    26,     4,    20,     5,     1,
       2,     3,    62,     4,    63,     5,    21,     6,     7,     8,
       9,    25,    39,    79,     6,     7,     8,     9,     1,    47,
       3,    76,     4,    27,     5,    30,    52,    81,    82,    28,
      55,    25,    36,     6,     7,     8,     9,    29,     1,     2,
       3,    64,     4,    40,    51,    69,     1,    47,     3,    70,
       4,    31,    75,     6,     7,     8,     9,    32,    33,    34,
      35,     6,     7,     8,     9,    83,     1,     2,     3,    62,
      37,    84,    22,     1,     2,     3,    62,    38,    88,    68,
      41,     6,     7,     8,     9,    62,    43,    89,     6,     7,
       8,     9,    93,    42,    32,    33,    34,    35,    56,    38,
      44,    45,    46,    53,    57,    60,    54,    59,    61,    66,
      65,    77,    67,    71,    72,    86,    74,    73,    78,    80,
      85,    87,    90,    91,    92,    56
};

static const yytype_int8 yycheck[] =
{
       0,     4,     3,     4,     5,     5,     7,    16,     9,     3,
       4,     5,     8,     7,    10,     9,    11,    18,    19,    20,
      21,    22,    22,     4,    18,    19,    20,    21,     3,     4,
       5,    65,     7,     4,     9,     9,    36,    71,    72,    16,
      40,    22,    12,    18,    19,    20,    21,    16,     3,     4,
       5,    51,     7,    23,     9,    58,     3,     4,     5,    59,
       7,     0,     9,    18,    19,    20,    21,    12,    13,    14,
      15,    18,    19,    20,    21,    75,     3,     4,     5,     8,
      21,    10,     9,     3,     4,     5,     8,     4,    10,     9,
      11,    18,    19,    20,    21,     8,    10,    10,    18,    19,
      20,    21,    10,     9,    12,    13,    14,    15,     3,     4,
       6,    21,    21,    17,     4,    17,    10,     9,    17,    16,
       9,    21,    10,     9,     9,     4,    10,    62,    11,    10,
      17,    11,    10,    10,    10,     3
};

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
static const yytype_int8 yystos[] =
{
       0,     3,     4,     5,     7,     9,    18,    19,    20,    21,
      24,    25,    26,    28,    29,    30,    31,    32,    35,    36,
      16,    11,     9,    26,     4,    22,    25,     4,    16,    16,
       9,     0,    12,    13,    14,    15,    27,    21,     4,    25,
      27,    11,     9,    10,     6,    21,    21,     4,    25,    33,
      34,     9,    25,    17,    10,    25,     3,     4,    27,     9,
      17,    17,     8,    10,    25,     9,    16,    10,     9,    26,
      25,     9,     9,    34,    10,     9,    33,    21,    11,     4,
      10,    33,    33,    25,    10,    17,     4,    11,    10,    10,
      10,    10,    10,    10
};

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr1[] =
{
       0,    23,    24,    25,    25,    25,    25,    25,    25,    25,
      26,    26,    26,    26,    26,    26,    26,    26,    26,    27,
      27,    27,    27,    28,    29,    30,    31,    32,    32,    33,
      33,    34,    34,    35,    36
};

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr2[] =
{
       0,     2,     1,     4,     3,     5,     5,     1,     2,     4,
       1,     1,     1,     1,     1,     1,     1,     3,     1,     1,
       1,     1,     1,     4,     8,     7,     7,     7,     9,     3,
       1,     1,     1,     8,     6
};


enum { YYENOMEM = -2 };

#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab
#define YYNOMEM         goto yyexhaustedlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                    \
  do                                                              \
    if (yychar == YYEMPTY)                                        \
      {                                                           \
        yychar = (Token);                                         \
        yylval = (Value);                                         \
        YYPOPSTACK (yylen);                                       \
        yystate = *yyssp;                                         \
        goto yybackup;                                            \
      }                                                           \
    else                                                          \
      {                                                           \
        yyerror (YY_("syntax error: cannot back up")); \
        YYERROR;                                                  \
      }                                                           \
  while (0)

/* Backward compatibility with an undocumented macro.
   Use YYerror or YYUNDEF. */
#define YYERRCODE YYUNDEF


/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)




# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Kind, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*-----------------------------------.
| Print this symbol's value on YYO.  |
`-----------------------------------*/

static void
yy_symbol_value_print (FILE *yyo,
                       yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep)
{
  FILE *yyoutput = yyo;
  YY_USE (yyoutput);
  if (!yyvaluep)
    return;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/*---------------------------.
| Print this symbol on YYO.  |
`---------------------------*/

static void
yy_symbol_print (FILE *yyo,
                 yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyo, "%s %s (",
             yykind < YYNTOKENS ? "token" : "nterm", yysymbol_name (yykind));

  yy_symbol_value_print (yyo, yykind, yyvaluep);
  YYFPRINTF (yyo, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yy_state_t *yybottom, yy_state_t *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yy_state_t *yyssp, YYSTYPE *yyvsp,
                 int yyrule)
{
  int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %d):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       YY_ACCESSING_SYMBOL (+yyssp[yyi + 1 - yynrhs]),
                       &yyvsp[(yyi + 1) - (yynrhs)]);
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args) ((void) 0)
# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif






/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg,
            yysymbol_kind_t yykind, YYSTYPE *yyvaluep)
{
  YY_USE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yykind, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/* Lookahead token kind.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;




/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    yy_state_fast_t yystate = 0;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus = 0;

    /* Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* Their size.  */
    YYPTRDIFF_T yystacksize = YYINITDEPTH;

    /* The state stack: array, bottom, top.  */
    yy_state_t yyssa[YYINITDEPTH];
    yy_state_t *yyss = yyssa;
    yy_state_t *yyssp = yyss;

    /* The semantic value stack: array, bottom, top.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs = yyvsa;
    YYSTYPE *yyvsp = yyvs;

  int yyn;
  /* The return value of yyparse.  */
  int yyresult;
  /* Lookahead symbol kind.  */
  yysymbol_kind_t yytoken = YYSYMBOL_YYEMPTY;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;



#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yychar = YYEMPTY; /* Cause a token to be read.  */

  goto yysetstate;


/*------------------------------------------------------------.
| yynewstate -- push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;


/*--------------------------------------------------------------------.
| yysetstate -- set current state (the top of the stack) to yystate.  |
`--------------------------------------------------------------------*/
yysetstate:
  YYDPRINTF ((stderr, "Entering state %d\n", yystate));
  YY_ASSERT (0 <= yystate && yystate < YYNSTATES);
  YY_IGNORE_USELESS_CAST_BEGIN
  *yyssp = YY_CAST (yy_state_t, yystate);
  YY_IGNORE_USELESS_CAST_END
  YY_STACK_PRINT (yyss, yyssp);

  if (yyss + yystacksize - 1 <= yyssp)
#if !defined yyoverflow && !defined YYSTACK_RELOCATE
    YYNOMEM;
#else
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYPTRDIFF_T yysize = yyssp - yyss + 1;

# if defined yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        yy_state_t *yyss1 = yyss;
        YYSTYPE *yyvs1 = yyvs;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * YYSIZEOF (*yyssp),
                    &yyvs1, yysize * YYSIZEOF (*yyvsp),
                    &yystacksize);
        yyss = yyss1;
        yyvs = yyvs1;
      }
# else /* defined YYSTACK_RELOCATE */
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        YYNOMEM;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yy_state_t *yyss1 = yyss;
        union yyalloc *yyptr =
          YY_CAST (union yyalloc *,
                   YYSTACK_ALLOC (YY_CAST (YYSIZE_T, YYSTACK_BYTES (yystacksize))));
        if (! yyptr)
          YYNOMEM;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YY_IGNORE_USELESS_CAST_BEGIN
      YYDPRINTF ((stderr, "Stack size increased to %ld\n",
                  YY_CAST (long, yystacksize)));
      YY_IGNORE_USELESS_CAST_END

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }
#endif /* !defined yyoverflow && !defined YYSTACK_RELOCATE */


  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;


/*-----------.
| yybackup.  |
`-----------*/
yybackup:
  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either empty, or end-of-input, or a valid lookahead.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token\n"));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = YYEOF;
      yytoken = YYSYMBOL_YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else if (yychar == YYerror)
    {
      /* The scanner already issued an error message, process directly
         to error recovery.  But do not keep the error token as
         lookahead, it is too special and may lead us to an endless
         loop in error recovery. */
      yychar = YYUNDEF;
      yytoken = YYSYMBOL_YYerror;
      goto yyerrlab1;
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);
  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
  case 2: /* formula: terms  */
#line 180 "./src/c.2024/parser.y"
            {
        ast = (yyvsp[0].node);
        (yyvsp[0].node)->isroot = 1;
        pruneeventsubtrees();
        deallocatequeue(_events, NULL);
        deallocatequeue(_datarefs, NULL);
    }
#line 1290 "parser.tab.c"
    break;

  case 3: /* terms: NEG term connective terms  */
#line 199 "./src/c.2024/parser.y"
                                 {
        print_debug("(top rule) terms: NEG terms connective term");
        (yyvsp[-2].node)->isnegative = 1;
        if ((yyvsp[0].node) == NULL) {
            /* case of terms connective TrueP */
            (yyval.node) = (yyvsp[-2].node);
        } else {
            (yyval.node) = newastnode(Connective, NULL);
            (yyval.node)->conntype = (yyvsp[-1].conntype);
            addastchild((yyval.node), (yyvsp[-2].node));
            addastchild((yyval.node), (yyvsp[0].node));
        }
    }
#line 1308 "parser.tab.c"
    break;

  case 4: /* terms: term connective terms  */
#line 212 "./src/c.2024/parser.y"
                            {
        print_debug("(top rule) terms: terms connective term");
        if ((yyvsp[0].node) == NULL) {
            /* case of terms connective TrueP */
            (yyval.node) = (yyvsp[-2].node);
        } else if ((yyvsp[-2].node) == NULL) {
            /* case of TrueP connective term */
            (yyval.node) = (yyvsp[0].node);
        } else {
            (yyval.node) = newastnode(Connective, NULL);
            (yyval.node)->conntype = (yyvsp[-1].conntype);
            addastchild((yyval.node), (yyvsp[-2].node));
            addastchild((yyval.node), (yyvsp[0].node));
        }
    }
#line 1328 "parser.tab.c"
    break;

  case 5: /* terms: term connective '(' terms ')'  */
#line 227 "./src/c.2024/parser.y"
                                    {
        print_debug("terms: terms connective '(' term ')'");
        if ((yyvsp[-1].node) == NULL) {
            (yyval.node) = (yyvsp[-4].node);
        } else if ((yyvsp[-4].node) == NULL) {
            (yyval.node) = (yyvsp[-1].node);
        } else {
            (yyval.node) = newastnode(Connective, NULL);
            (yyval.node)->conntype = (yyvsp[-3].conntype);
            addastchild((yyval.node), (yyvsp[-4].node));
            addastchild((yyval.node), (yyvsp[-1].node));
        }
    }
#line 1346 "parser.tab.c"
    break;

  case 6: /* terms: '(' terms ')' connective term  */
#line 240 "./src/c.2024/parser.y"
                                     {
        print_debug("terms: terms connective '(' term ')'");
        if ((yyvsp[-3].node) == NULL) {
            (yyval.node) = (yyvsp[0].node);
        } else if ((yyvsp[0].node) == NULL) {
            (yyval.node) = (yyvsp[-3].node);
        } else {
            (yyval.node) = newastnode(Connective, NULL);
            (yyval.node)->conntype = (yyvsp[-1].conntype);
            addastchild((yyval.node), (yyvsp[-3].node));
            addastchild((yyval.node), (yyvsp[0].node));
        }
    }
#line 1364 "parser.tab.c"
    break;

  case 7: /* terms: term  */
#line 253 "./src/c.2024/parser.y"
           {
        print_debug("terms: term");
        if ((yyvsp[0].node) == NULL) {
            (yyval.node) = NULL;
        } else {
            (yyval.node) = (yyvsp[0].node);
        }
    }
#line 1377 "parser.tab.c"
    break;

  case 8: /* terms: NEG term  */
#line 261 "./src/c.2024/parser.y"
               {
        print_debug("term: NEG term");
        (yyval.node) = (yyvsp[0].node);
        (yyval.node)->isnegative = 1;
    }
#line 1387 "parser.tab.c"
    break;

  case 9: /* terms: NEG '(' terms ')'  */
#line 271 "./src/c.2024/parser.y"
                        {
        print_debug("NEG '(' terms ')'");
        (yyval.node) = (yyvsp[-1].node);
        (yyval.node)->isnegative = 1;
    }
#line 1397 "parser.tab.c"
    break;

  case 10: /* term: predicate_term  */
#line 280 "./src/c.2024/parser.y"
                     {
        (yyval.node) = (yyvsp[0].node);
    }
#line 1405 "parser.tab.c"
    break;

  case 11: /* term: type_term  */
#line 283 "./src/c.2024/parser.y"
                {
        (yyval.node) = (yyvsp[0].node);
    }
#line 1413 "parser.tab.c"
    break;

  case 12: /* term: param_term  */
#line 286 "./src/c.2024/parser.y"
                 {
        (yyval.node) = (yyvsp[0].node);
    }
#line 1421 "parser.tab.c"
    break;

  case 13: /* term: quantified_term  */
#line 289 "./src/c.2024/parser.y"
                      {
        (yyval.node) = (yyvsp[0].node);
    }
#line 1429 "parser.tab.c"
    break;

  case 14: /* term: event_term  */
#line 292 "./src/c.2024/parser.y"
                 {
        // $$ = NULL;
        (yyval.node) = (yyvsp[0].node);
    }
#line 1438 "parser.tab.c"
    break;

  case 15: /* term: grammar_term  */
#line 296 "./src/c.2024/parser.y"
                   {
        (yyval.node) = (yyvsp[0].node);
    }
#line 1446 "parser.tab.c"
    break;

  case 16: /* term: KEYWORD_TRUEP  */
#line 299 "./src/c.2024/parser.y"
                    {
        print_debug("term: PREDICATE(TrueP)");
        (yyval.node) = NULL;
    }
#line 1455 "parser.tab.c"
    break;

  case 17: /* term: IDENTIFIER EQUAL IDENTIFIER  */
#line 303 "./src/c.2024/parser.y"
                                  {
        print_debug("term: '(' IDENTIFIER EQUAL IDENTIFIER ')'");
        (yyval.node) = newastnode(Operator, (yyvsp[-1].t));
        struct astnode *left = newastnode(Variable, (yyvsp[-2].t));
        struct astnode *right = newastnode(Variable, (yyvsp[0].t));
        addastchild((yyval.node), left);
        addastchild((yyval.node), right);
        enqueue(operators, (void*)(yyval.node));
    }
#line 1469 "parser.tab.c"
    break;

  case 18: /* term: pronoun_term  */
#line 312 "./src/c.2024/parser.y"
                   {
        (yyval.node) = (yyvsp[0].node);
    }
#line 1477 "parser.tab.c"
    break;

  case 19: /* connective: AND  */
#line 318 "./src/c.2024/parser.y"
          { (yyval.conntype) = Op_And; }
#line 1483 "parser.tab.c"
    break;

  case 20: /* connective: EQUIV  */
#line 319 "./src/c.2024/parser.y"
            { (yyval.conntype) = Op_Equivalent; }
#line 1489 "parser.tab.c"
    break;

  case 21: /* connective: IMPLY  */
#line 320 "./src/c.2024/parser.y"
            { (yyval.conntype) = Op_Imply; }
#line 1495 "parser.tab.c"
    break;

  case 22: /* connective: OR  */
#line 321 "./src/c.2024/parser.y"
            { (yyval.conntype) = Op_Or; }
#line 1501 "parser.tab.c"
    break;

  case 23: /* grammar_term: TAG '(' arguments ')'  */
#line 325 "./src/c.2024/parser.y"
                            {
        print_debug("grammar_term: TAG '(' arguments ')'");
        if (string2ptbsyntax((yyvsp[-3].t)->symbol) == Gram_Prog) {
            (yyval.node) = (yyvsp[-1].nodelist)->node;
            free((yyvsp[-1].nodelist));
        } else {
            (yyval.node) = newastnode(GrammarNotation, (yyvsp[-3].t)); 
            (yyval.node)->syntax = string2ptbsyntax((yyvsp[-3].t)->symbol);
            addastchildren((yyval.node), (yyvsp[-1].nodelist));
            // we treat the grammar_tag as a predicate
            fprintf(stderr, "DEBUG parser: enqueue predicate '%s' syntax=%d children=%d\n", (yyvsp[-3].t)->symbol, (yyval.node)->syntax, countastchildren((yyval.node)));
            enqueue(predicates, (void*)(yyval.node));
        }
    }
#line 1520 "parser.tab.c"
    break;

  case 24: /* pronoun_term: '(' IDENTIFIER EQUAL PREDICATE '{' TAG '}' ')'  */
#line 342 "./src/c.2024/parser.y"
                                                     {
        print_debug("pronoun_term: IDENTIFIER EQUAL PREDICATE '{' TAG '}'");
        (yyval.node) = newastnode(Pronoun, (yyvsp[-4].t));
        if ((yyval.node)->token->symbol[0] == '_') {
            /* removing the underscore */
            popchar((yyval.node)->token->symbol);
        }
        (yyval.node)->syntax = string2ptbsyntax((yyvsp[-2].t)->symbol);
        addastchild((yyval.node), newastnode(Variable, (yyvsp[-6].t)));
        enqueue(predicates, (void*)(yyval.node));
    }
#line 1536 "parser.tab.c"
    break;

  case 25: /* type_term: KEYWORD_TYPE '{' TAG '}' '(' arguments ')'  */
#line 363 "./src/c.2024/parser.y"
                                                 {
        //print_debug("TYPE(%s), Syntax(%s)\n", $1->symbol, $3->symbol);
        if (getnodelistlength((yyvsp[-1].nodelist)) > 1) {
            print_semantic_error("A type predicate can only have one argument.");
        }
        popchar((yyvsp[-6].t)->symbol);
        (yyval.node) = newastnode(TypePredicate, (yyvsp[-6].t));
        addastchildren((yyval.node), (yyvsp[-1].nodelist));
        (yyval.node)->syntax = string2ptbsyntax((yyvsp[-4].t)->symbol);
        struct queue *siq = q_searchqueue(silist, (yyval.node), __javatype_simatcher);
        if (siq->count == 0) {
            syntax_error("SI for type predicate(%s) is not found.", (yyvsp[-6].t)->symbol);
        } 
        struct si *si = (struct si *)gqueue(siq, 0);
        enqueue(_datarefs, (void *)newtmpdataref((yyval.node), si->synthesised_datatype));                       
    }
#line 1557 "parser.tab.c"
    break;

  case 26: /* param_term: KEYWORD_PARAM '{' TAG '}' '(' arguments ')'  */
#line 386 "./src/c.2024/parser.y"
                                                  {
        // printf("PARAM(%s), Syntax(%s)\n", $1->symbol, $3->symbol); 
        if (getnodelistlength((yyvsp[-1].nodelist)) > 1) {
            print_semantic_error("A type predicate can only have one argument.");
        }
        (yyval.node) = newastnode(Predicate, (yyvsp[-6].t));
        for (int i = 0; i < 7; ++i) {
            popchar((yyval.node)->token->symbol);
        }        
        (yyval.node)->token->symbol[strlen((yyval.node)->token->symbol) - 1] = '\0';
        addastchildren((yyval.node), (yyvsp[-1].nodelist));
        (yyval.node)->syntax = string2ptbsyntax((yyvsp[-4].t)->symbol);
        if ((yyval.node)->syntax != NN && (yyval.node)->syntax != NNS && (yyval.node)->syntax != NNP && (yyval.node)->syntax != NNPS) {
            print_semantic_error("A parameter must be regconised as a noun.");
        }
        (yyval.node)->syntax = NN;
        /*
            this is added after using LLM. we no longer include the program context here
        */
        generate_param_si((yyval.node)->token->symbol);
        fprintf(stderr, "DEBUG parser: enqueue predicate '%s' syntax=%d (param_term)\n", (yyval.node)->token->symbol, (yyval.node)->syntax);
        enqueue(predicates, (void*)(yyval.node));
    }
#line 1585 "parser.tab.c"
    break;

  case 27: /* predicate_term: PREDICATE '{' TAG '}' '(' arguments ')'  */
#line 412 "./src/c.2024/parser.y"
                                              {
        print_debug("term: PREDICATE '{' TAG '}' '(' arguments ')'");
        #if PARDEBUG
        printf("Predicate(%s), Syntax(%s)\n", (yyvsp[-6].t)->symbol, (yyvsp[-4].t)->symbol);
        #endif        
        (yyval.node) = newastnode(Predicate, (yyvsp[-6].t));    
        if ((yyval.node)->token->symbol[0] == '_') {
            /* removing the underscore */
            popchar((yyval.node)->token->symbol);
        }
        addastchildren((yyval.node), (yyvsp[-1].nodelist));
        (yyval.node)->syntax = string2ptbsyntax((yyvsp[-4].t)->symbol);
        /* predicate node is marked in a queue and si identification is processed later  */
        fprintf(stderr, "DEBUG parser: enqueue predicate '%s' syntax=%d (predicate_term)\n", (yyval.node)->token->symbol, (yyval.node)->syntax);
        enqueue(predicates, (void*)(yyval.node));
    }
#line 1606 "parser.tab.c"
    break;

  case 28: /* predicate_term: PREDICATE '{' TAG '}' '(' '(' terms ')' ')'  */
#line 428 "./src/c.2024/parser.y"
                                                  {
        print_debug("term: PREDICATE(Modal)) '{' TAG '}' '(' '(' terms ')' ')'");
        (yyval.node) = (yyvsp[-2].node);
    }
#line 1615 "parser.tab.c"
    break;

  case 29: /* arguments: arguments COMMA argument  */
#line 435 "./src/c.2024/parser.y"
                               {
        print_debug("arguments: arguments IDENTIFIER");
        appendnode((yyvsp[-2].nodelist), (yyvsp[0].node));
        (yyval.nodelist) = (yyvsp[-2].nodelist);
    }
#line 1625 "parser.tab.c"
    break;

  case 30: /* arguments: argument  */
#line 440 "./src/c.2024/parser.y"
               {
        print_debug("arguments: argument");
        (yyval.nodelist) = newastnodelist((yyvsp[0].node));
    }
#line 1634 "parser.tab.c"
    break;

  case 31: /* argument: IDENTIFIER  */
#line 447 "./src/c.2024/parser.y"
                 {
        print_debug("argument: IDENTIFIER");        
        (yyval.node) = newastnode(Variable, (yyvsp[0].t));
    }
#line 1643 "parser.tab.c"
    break;

  case 32: /* argument: terms  */
#line 451 "./src/c.2024/parser.y"
            {
        print_debug("argument: terms");
        (yyval.node) = (yyvsp[0].node);
    }
#line 1652 "parser.tab.c"
    break;

  case 33: /* event_term: '(' EVENT '(' IDENTIFIER ')' EQUAL IDENTIFIER ')'  */
#line 458 "./src/c.2024/parser.y"
                                                        {
        print_debug("event_term: '(' EVENT '(' IDENTIFIER ')' EQUAL IDENTIFIER ')'"); 
        // struct astnode *subtree_root = newastnode(GrammarNotation, $2);
        (yyval.node) = newastnode(GrammarNotation, (yyvsp[-6].t));
        struct astnode *eventnode = newastnode(EventVariable, (yyvsp[-4].t));
        struct astnode *entitynode = newastnode(EventEntity, (yyvsp[-1].t));
        addastchild((yyval.node), eventnode);
        addastchild((yyval.node), entitynode);
        enqueue(_events, (void *)newtmpevent((yyval.node), eventnode, entitynode));
    }
#line 1667 "parser.tab.c"
    break;

  case 34: /* quantified_term: KEYWORD_QUANTIFIER IDENTIFIER '.' '(' terms ')'  */
#line 471 "./src/c.2024/parser.y"
                                                      {
        print_debug("quantify_expr: KEYWORD_QUANTIFIER IDENTIFIER");
        #if PARDEBUG
        printf("quantified variable: %s\n", (yyvsp[-4].t)->symbol);
        #endif
        (yyval.node) = newastnode(Quantifier, (yyvsp[-4].t));
        if (strcmp((yyvsp[-5].t)->symbol, "exists") == 0) {
            (yyval.node)->qtype = Quantifier_Exists;
            (yyval.node)->quantified_ranges = NULL;
        } else {
            (yyval.node)->qtype = Quantifier_ForAll;
            (yyval.node)->quantified_ranges = initqueue();
        }
        struct queue *in_scope_symbol_nodes = search_symbols((yyvsp[-1].node), (yyvsp[-4].t)->symbol);
        if (in_scope_symbol_nodes->count == 0) {
            printf("Precaution: there are no entity(%s) references in the quantified scope.\n", (yyvsp[-4].t)->symbol);
        }
        addastchild((yyval.node), (yyvsp[-1].node));  
        (yyval.node)->cstptr = newcstsymbol((yyvsp[-4].t)->symbol);

        (yyval.node)->cstptr->ref_count += in_scope_symbol_nodes->count;
        /* finding all the variable nodes that have the same symbol as the quantified variable, and find if there is a node has TYPE resolved */
        for (int i = 0; i < in_scope_symbol_nodes->count; ++i) {
            struct astnode *_node = (struct astnode *)gqueue(in_scope_symbol_nodes, i);
            if (_node->parent->type == TypePredicate) {
                struct _dataref *ref = NULL;
                for (int j = 0; j < _datarefs->count; ++j) {
                    ref = (struct _dataref *)gqueue(_datarefs, j);
                    if (ref->node == _node->parent) {
                        break;
                    }
                }
                /* TO BE DONE: needed refinement. incoming type can be a primitive type, and the stored type is a reference type */
                if (has_datatype((yyval.node)->cstptr)) {
                    /* 
                        Try to merge datatype 
                        two datatypes can be merged if * in a type and another is not
                        for instance, datatype X has primitive type as * and Y has primitive type as 0,
                        however, if both their primitive types have valid types, we consider that the entity may have two or more datatypes
                            being specified, providing combinatorial results are considered as future work.
                    */
                    if (((yyval.node)->cstptr->datatype->p >= 0 && ref->datatype->p >= 0) || ((yyval.node)->cstptr->datatype->r == ref->datatype->r)) {                        
                        semantic_error("The entity(%s) has two or more datatypes found. Please solve this conflict.", (yyvsp[-4].t)->symbol);
                    } else if (((yyval.node)->cstptr->datatype->r == String || ref->datatype->r == String) && (yyval.node)->cstptr->datatype->r != ref->datatype->r) {
                        /* we can only use whatever we want when the parsed datatype is an array or a list */
                        /* if the parsed datatype is an array, then the incoming datatype can only be a primitive type */
                        /* if the parsed datatype is a list, then the incoming datatype can be any type */
                        /* TODO: custom types are not supported currently */
                        (yyval.node)->cstptr->datatype->element_datatype = (struct datatype *)malloc(sizeof(struct datatype));
                        (yyval.node)->cstptr->datatype->element_datatype->types = initqueue();
                        (yyval.node)->cstptr->datatype->element_datatype->r = String;
                        (yyval.node)->cstptr->datatype->element_datatype->p = AnyPrimitiveType;     
                        if ((yyval.node)->cstptr->datatype->r == String) {
                            (yyval.node)->cstptr->datatype->p = ref->datatype->p;
                            (yyval.node)->cstptr->datatype->r = ref->datatype->r;
                        }
                    } else {
                        if (ref->datatype->p >= 0) (yyval.node)->cstptr->datatype->p = ref->datatype->p;
                        if (ref->datatype->r >= 0) (yyval.node)->cstptr->datatype->r = ref->datatype->r;
                        if (ref->datatype->r == 2) {
                            while(!isempty(ref->datatype->types)) enqueue((yyval.node)->cstptr->datatype->types, (void *)dequeue(ref->datatype->types));
                        }
                    }
                } else {
                    (yyval.node)->cstptr->datatype = ref->datatype;
                }
                /* the variable is being removed. deduct the reference count */
                (yyval.node)->cstptr->ref_count--;
                (yyval.node)->cstptr->type_assigned = TRUE;
                (yyval.node)->cstptr->status = Assigned;
                deleteastchild(ref->node->parent, ref->node);
            } else {
                _node->cstptr = (yyval.node)->cstptr;
            }
        }
        deallocatequeue(in_scope_symbol_nodes, NULL);
    }
#line 1749 "parser.tab.c"
    break;


#line 1753 "parser.tab.c"

      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", YY_CAST (yysymbol_kind_t, yyr1[yyn]), &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */
  {
    const int yylhs = yyr1[yyn] - YYNTOKENS;
    const int yyi = yypgoto[yylhs] + *yyssp;
    yystate = (0 <= yyi && yyi <= YYLAST && yycheck[yyi] == *yyssp
               ? yytable[yyi]
               : yydefgoto[yylhs]);
  }

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYSYMBOL_YYEMPTY : YYTRANSLATE (yychar);
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
      yyerror (YY_("syntax error"));
    }

  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:
  /* Pacify compilers when the user code never invokes YYERROR and the
     label yyerrorlab therefore never appears in user code.  */
  if (0)
    YYERROR;
  ++yynerrs;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  /* Pop stack until we find a state that shifts the error token.  */
  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYSYMBOL_YYerror;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYSYMBOL_YYerror)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  YY_ACCESSING_SYMBOL (yystate), yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", YY_ACCESSING_SYMBOL (yyn), yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturnlab;


/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturnlab;


/*-----------------------------------------------------------.
| yyexhaustedlab -- YYNOMEM (memory exhaustion) comes here.  |
`-----------------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  goto yyreturnlab;


/*----------------------------------------------------------.
| yyreturnlab -- parsing is finished, clean up and return.  |
`----------------------------------------------------------*/
yyreturnlab:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  YY_ACCESSING_SYMBOL (+*yyssp), yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif

  return yyresult;
}

#line 549 "./src/c.2024/parser.y"


void print_debug(char *s) {
    #if PARDEBUG
    printf("%s\n", s);
    #endif  
}

void print_semantic_error(char *s) {
    printf("Semantic representation semantic error. %s\n", s);
    exit(-1);
}

extern
void yyerror(const char *s) {
    extern char* input_line;
    fprintf(stderr,"Parser: parse error!\n  Message: %s in line %d, column %d\n", s, lineNum, colNum);
    fprintf(stderr,"%s\n", input_line);
    free(input_line);
    for(int i = 0; i < colNum - 1; i++)
        fprintf(stderr,"_");
    fprintf(stderr,"^\n"); 
    exit(-1); 
}
