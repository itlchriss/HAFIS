#ifndef SYN_H
#define SYN_H

#include "ast.h"
#include "cst.h"

/*
    Synthesis module header.
    This header declares the synthesis functions for each Penn Treebank 
    syntactic category. Each category has a corresponding synthesis function
    that is called during the SI synthesis phase.
*/

struct queue *__COMP__cmd_synthesis__(struct cstsymbol *, struct cstsymbol *);

int event_synthesis(struct astnode *);

/* Synthesis functions for each PTB category */
int IN_code_synthesis(struct astnode *);
int JJ_code_synthesis(struct astnode *);
int JJR_code_synthesis(struct astnode *);
int JJS_code_synthesis(struct astnode *);
int NN_code_synthesis(struct astnode *);
int NNS_code_synthesis(struct astnode *);
int NNP_code_synthesis(struct astnode *);
int NNPS_code_synthesis(struct astnode *);
int CD_code_synthesis(struct astnode *);
int RB_code_synthesis(struct astnode *);
int TO_code_synthesis(struct astnode *);

/* Additional synthesis functions for extended PTB categories */
int CC_code_synthesis(struct astnode *);
int DT_code_synthesis(struct astnode *);
int EX_code_synthesis(struct astnode *);
int FW_code_synthesis(struct astnode *);
int LS_code_synthesis(struct astnode *);
int MD_code_synthesis(struct astnode *);
int PDT_code_synthesis(struct astnode *);
int POS_code_synthesis(struct astnode *);
int PRP_code_synthesis(struct astnode *);
int PRP_POS_code_synthesis(struct astnode *);
int RBR_code_synthesis(struct astnode *);
int RBS_code_synthesis(struct astnode *);
int RP_code_synthesis(struct astnode *);
int SYM_code_synthesis(struct astnode *);
int UH_code_synthesis(struct astnode *);
int VB_code_synthesis(struct astnode *);
int VBD_code_synthesis(struct astnode *);
int VBG_code_synthesis(struct astnode *);
int VBN_code_synthesis(struct astnode *);
int VBP_code_synthesis(struct astnode *);
int VBZ_code_synthesis(struct astnode *);
int WDT_code_synthesis(struct astnode *);
int WP_code_synthesis(struct astnode *);
int WP_POS_code_synthesis(struct astnode *);
int WRB_code_synthesis(struct astnode *);

/* Grammar synthesis functions */
int Gram_Prog_synthesis(struct astnode *);
int Gram_Rel_synthesis(struct astnode *);

/* Rel synthesis */
int __Rel_synthesis__(
    struct cstsymbol *, struct cstsymbol *, struct queue *);


void JJ_event_synthesis_post_operation(struct astnode *, struct queue *);

#endif
