import os
import glob
import re

ROOT_PATH = './test/mutation_analysis'

# 0: the mutant is killed, i.e. KILLED
# 1: the mutant is not killed, i.e. SURVIVED
# -1: not enough evidence to decide if the mutant is killed, i.e. UNCERTAIN
#       e.g. timeout
KILLED = 0
SURVIVED = 1
UNCERTAIN = -1
FALSE_NEGATIVE = -2
IE_Terms = ['timeout']
def _killing_decision(oresult: str, mresult: str, mline: str) -> int:
    # UNCERTAIN conditions
    # condition 1: If any of the terms that provides the validity of result is unknown, the result is uncertain
    if any(t in mresult for t in IE_Terms):
        return UNCERTAIN
    ################################################
    # KILLED conditions:
    # condition 1: if the mutated line is precisely captured
    if '%s verify:' % mline in mresult:
        return KILLED
    # added condition: if the lines associated are different, it means that at least a new defect is found.
    pattern = r'Solution\.java:(\d+):\s+verify'
    o = re.findall(pattern, oresult)
    m = re.findall(pattern, mresult)
    if oresult and mresult:
        o_lines = set(o)
        m_lines = set(m)
        if o_lines != m_lines:
            return KILLED
    elif not oresult and mresult:
        return KILLED
    elif not mresult and not oresult:
        return SURVIVED
    # condition 2: if the number of verification failure caught in the mutated program is greater than that of the original program
    pattern = r'(\d+)\s+verification failure[s]?'
    o = re.search(pattern, oresult)
    m = re.search(pattern, mresult)
    if o and m:
        _o = int(o.group(1))
        _m = int(m.group(1))
        if _o >= _m:
            # we have to compare the lines here
            pattern = r'Solution\.java:(\d+):\s+verify'
            ol = re.findall(pattern, oresult)
            ml = re.findall(pattern, mresult)
            if (_o >= _m and sorted(ml) != sorted(ol)) or sorted(ml) != sorted(ol):
                return KILLED
            return SURVIVED
        elif _o < _m:
            return KILLED
        else:
            return SURVIVED
    elif not o and m:
        return KILLED
    elif not m and o:
        return SURVIVED
    else:
        return UNCERTAIN
    ################################################

def _get_data(t: str, model: str):
    results = {}
    for folder in glob.glob(ROOT_PATH + '/s*/%s/%s' % (t, model)):
        s = folder.split('/')[-3]
        with open(os.path.join(folder, 'org_result.txt'), 'r') as fp:
            odata = fp.read().strip()
        mutantdata = {}
        # the number of cases that the mutation is performed on a line that cannot be proved by the theorem prover
        type1_count = 0
        for mutantfolder in glob.glob(os.path.join(folder, 'mutants/*')):
            # x and print(mutantfolder)
            with open(os.path.join(mutantfolder, 'line.txt'), 'r') as fp:
                mutated_line = fp.read().strip()
            with open(os.path.join(mutantfolder, 'result.txt'), 'r') as fp:
                mdata = fp.read().strip()
            with open(os.path.join(mutantfolder, 'Solution.java'), 'r') as fp:
                program = fp.read().strip()
            if '@ requires' not in program and '@ ensures' not in program:
                # The program has no JML inserted
                # If the mutant is killed, it is not due to the JML
                mutantdata[mutantfolder.split('/')[-1]] = SURVIVED
            else:
                mutantdata[mutantfolder.split('/')[-1]] = _killing_decision(odata, mdata, mutated_line)
        results[s] = mutantdata
        # print(s, len(mutantdata.keys()))
        # uncomment this to check the number of exluded mutants in type 1
        # print('Type 1 excluded %d out of %d' % (type1_count, len(glob.glob(os.path.join(folder, 'mutants/*')))))
    return results

# gpt_results = _get_data('gpt35')
# hart_results = _get_data('hart')
models = ['gpt35', 'gpt4', 'starchat']
approaches = ['hafis', 'purellm']
mres = {'gpt35': {}, 'gpt4': {}, 'starchat': {}}
for model in models:
    for approach in approaches:
        mres[model][approach] = _get_data(approach, model)

# print('GPT specs killed')
# for s in gpt_results.keys():
#     print(s, len(gpt_results[s].keys()), len([v for v in gpt_results[s].values() if v == KILLED]))

# print('HART specs killed')
# for s in gpt_results.keys():
#     print(s, len(hart_results[s].keys()), len([v for v in hart_results[s].values() if v == KILLED]))

########### gather data start #############

with open(ROOT_PATH + '/opdata') as fp:
    opdata = fp.read()

with open(ROOT_PATH + '/optypes') as fp:
    r = fp.read()
    types = r.strip().split(',')
    optypes = {}
    for t in types:
        optypes[t] = 0

lines = opdata.strip().split('\n')
opdata = {}
for line in lines:
    data = line.split(' ')
    if data[0] not in opdata.keys():
        opdata[data[0]] = []
    opdata[data[0]].append(data[2])

# opstat = {'hart': optypes.copy(), 'llm': optypes.copy()}
opstat = {}

for model in models:
    for approach in approaches:
        opstat[approach + '_' + model] = optypes.copy()
######## gather data end #############


def compare(gpt_results, hart_results):
    gpt_better_cases = []
    hart_better_cases = []
    no_kill_cases = []
    ident_cases = []
    no_valid_mutant = []
    ######## consider a vienne diagram ############
    #   A: set of killed mutants from GPT
    #   B: set of killed mutants from HART
    #   If A% > B% of (A U B), then A provides more effective spec in a problem
    #   vice versa ###############
    number_of_mutants = 0
    for s in gpt_results.keys():
        print('problem: %s' % s)  
        gl = len(gpt_results[s].keys())
        hl = len(hart_results[s].keys())
        gk = len([v for v in gpt_results[s].values() if v == KILLED])
        hk = len([v for v in hart_results[s].values() if v == KILLED])

        gv = [v for v in gpt_results[s].values() if v == KILLED]
        hv = [v for v in hart_results[s].values() if v == KILLED]
        if s in opdata:
            ops = opdata[s]
            # for v in gv:
            #     opstat['llm'][ops[int(v)]] += 1
            # for v in hv:
            #     opstat['hart'][ops[int(v)]] += 1 

        union_mutants = list(set(list(gpt_results[s].keys()) + list(hart_results[s].keys())))
        if len(union_mutants) == 0:
            # the case that no mutants are valid
            no_valid_mutant.append(s)
            continue
        else:
            number_of_mutants += len(union_mutants)
        gpt_killed_mutants = [k for k in gpt_results[s].keys() if gpt_results[s][k] == KILLED]
        hart_killed_mutants = [k for k in hart_results[s].keys() if hart_results[s][k] == KILLED]
        killed_union = set(gpt_killed_mutants + hart_killed_mutants)
        killed_union_percentage = len(killed_union)/len(union_mutants)
        gpt_killed_percentage = len(gpt_killed_mutants) / len(union_mutants)
        hart_killed_percentage = len(hart_killed_mutants) / len(union_mutants)
        print("union killed %%: %f%%, GPT killed %%: %f%%, HAFIS killed %%: %f%%" % (killed_union_percentage * 100, gpt_killed_percentage * 100, hart_killed_percentage * 100))
        # find mutual kills
        
        if len(killed_union) == 0:
            # no mutants are killed, no comparison can be made
            no_kill_cases.append(s)
            continue
        gpt_contribute = len(set(gpt_killed_mutants).intersection(killed_union)) / len(killed_union)
        hart_contribute = len(set(hart_killed_mutants).intersection(killed_union)) / len(killed_union)
        print("GPT contributed kill %%: %f%%, HAFIS contributed kill %%: %f%%" % (gpt_contribute * 100, hart_contribute * 100))
        # comparison
        # condition 1: if A has more kill than B, then A dominates
        if len(gpt_killed_mutants) > len(hart_killed_mutants):
            gpt_better_cases.append(s)
        elif len(gpt_killed_mutants) < len(hart_killed_mutants):
            hart_better_cases.append(s)
        else:
            # condition 2: if A and B kill the same number, then we check the contribution, if A contributes more than B, then A dominates
            if gpt_contribute > hart_contribute:
                gpt_better_cases.append(s)
            elif gpt_contribute < hart_contribute:
                hart_better_cases.append(s)
            else:
                # if even the contribution is the same, then there are no differences
                if gpt_contribute == 1 and hart_contribute == 1:
                    ident_cases.append(s)
                pass

    print("Comparison: ")
    print("Total cases: %d programs with %d mutants" % (len(gpt_results.keys()), number_of_mutants))
    print("GPT is better in %d cases and HAFIS is better in %d cases" % (len(gpt_better_cases), len(hart_better_cases)))

    print("Identical contributions in killing mutants: %d" % len(ident_cases))
    print("There are %d cases without mutants killed" % len(no_kill_cases))
    print(no_kill_cases)
    print("There are %d cases without valid mutants" % len(no_valid_mutant), no_valid_mutant)

    missing_case = [i for i in gpt_results.keys() if i not in gpt_better_cases and i not in hart_better_cases and i not in ident_cases and i not in no_kill_cases and i not in no_valid_mutant]

    if missing_case:
        print("The following case is missing in the above results: ", missing_case)

    # print(gpt_results['s0455_assign_cookies'])  
    # print(hart_results['s0455_assign_cookies'])

    # print('Number of mutants produced by these operators')
    # print('HART')
    # print(opstat['hart'])
    # print('GPT')
    # print(opstat['llm'])

def boxplot(model: str, gpt_results, hart_results):
    programs = [str(i) for i in gpt_results.keys()]
    total = {}
    for i in programs:
        total[i] = len(glob.glob(ROOT_PATH + '/%s/hafis/%s/mutants/*' % (i, model)))

    g = []
    p = []
    u = []
    d = []
    import numpy as np
    import matplotlib.pyplot as plt
    for i in programs:
        hk = [k for k in hart_results[i] if hart_results[i][k] == KILLED]
        gk = [k for k in gpt_results[i] if gpt_results[i][k] == KILLED]
        inters = set(hk).intersection(gk)
        union = list(set(gk + hk))
        nh = len(hk)
        ng = len(gk)
        t = total[i]
        if t == 0:
            continue
        g.append((ng/t))
        p.append((nh/t) )
        u.append((len(union)/t) )
        d.append(len(inters)/t)

    data = [np.array(g), np.array(p), np.array(u), np.array(d)]

    if len(p) == 0:
        avg_h = 0
    else:
        avg_h = sum(p)/len(p)
    
    if len(g) == 0:
        avg_g = 0
    else:
        avg_g = sum(g)/len(g)
    
    if len(u) == 0:
        avg_u = 0
    else:
        avg_u = sum(u)/len(u)
    print("HAFIS: ", avg_h, ", LLM: ",  avg_g,". Union: ",  avg_u)




    fig, ax = plt.subplots()
    bp = ax.boxplot(data, labels=['Pure LLM \nusing ' + model, 'HAFIS integrated\n with ' + model, 'Union', 'Intersection'])  
    plt.title("Mutation score of the contracts generated by \npure LLM and HAFIS using " + model)  
    plt.savefig('./results/%s_boxplt.pdf' % model);

def efficiency(result: dict):
    e = 0.0
    k = 0
    for s in result.keys():
        M = len(result[s].keys())
        if not M:
            continue
        c_bar_M = len([k for k in result[s].keys() if result[s][k] == KILLED])
        # print(total_mutants, len(killed_mutants))
        mutation_score = c_bar_M / M
        e += mutation_score
        k += 1
    eff = e/k
    return eff

for approach in approaches:
    print('----------------' + approach + '----------------')
    for model in models:
        print('      ----------------' + model + '----------------')
        print('      efficiency: %f' % efficiency(mres[model][approach]))


for model in models:
    boxplot(model, mres[model]['purellm'], mres[model]['hafis'])

