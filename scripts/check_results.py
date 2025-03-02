

from glob import glob
import os
from typing import List


def calc_mr(model: str):
    SRCPATH = "./test/patterns/s*/hafis"
    data = []
    failed = []
    # refer to incomplete MR beta reduction
    incomplete = []
    complete = []

    for folder in glob(SRCPATH):    
        with open(os.path.join(folder, model, "tmp/conditions.yml"), "r") as fp:
            lines = fp.read()
        temp = "%s.%s.mr"
        head = ""
        count = 0    
        for line in lines.split("\n"):
            if line.startswith("requires"):
                head = "pre"
                count = 0
            elif line.startswith("ensures"):
                head = "post"
                count = 0
            elif not line.startswith("-"):
                break
            else:
                data.append(os.path.join(folder, model, "tmp", temp % (head, str(count))))
                count += 1
        for file in glob(os.path.join(folder, model, "tmp/*.mr")):
            with open(file, "r") as fp:
                _tmp = fp.read()
                if _tmp and _tmp.strip() == "Failed":
                    failed.append(file)
                elif _tmp and '\\' in _tmp.strip():
                    incomplete.append(file)
                else:
                    complete.append(file)
            data.remove(file)
    return data, failed, incomplete, complete

def calc_jml(model: str, mrlist: List[str]):
    _mrlist = mrlist
    SRCPATH = "./test/patterns/s*/hafis/%s/jml/*.jml"
    for file in glob(SRCPATH % model):    
        _file = file.replace('.jml', '.mr').replace('jml', 'tmp')
        if _file in _mrlist:
            _mrlist.remove(_file)
    return _mrlist

# with open('./scripts/gpt35_check') as fp:
#     gd = fp.read()

# gd = gd.split(',')
# gd = [i.strip() for i in gd]

for model in ["starchat", "gpt35", "gpt4"]:
    data, failed, incomplete, complete = calc_mr(model)
    # if data:
    print("%s MR Missing:" % model)
    print("N/A count: ", len(data))
    print("Failed count: ", len(failed))
    print("Incomplete beta reduction: ", len(incomplete))
    print("Completed: ", len(complete))
    c = len(complete)
    mj = calc_jml(model, complete)
    print("Missing JML: ", len(mj))
    print("Completed JML: ", c - len(mj))
    
    # for j in mj:
    #     x = j.replace('./test/patterns/', '').replace('.mr', '')
    #     y = x.split('/')[0]
    #     k = x.split('/')[-1]
    #     if model == 'gpt35' and (y.split('_')[0] + '.' + k) not in gd:
    #         print(y.split('_')[0] + '.' + k)
    # if failed:
    #     print("%s Failed due to NLP cannot parse the sentence" % model)
    #     for i in failed:
    #         print(i)
    #     print("count: ", len(failed))

    # if incomplete:
    #     print("%s Incomplete MR beta reduction" % model)
    #     for i in incomplete:
    #         print(i)
    #     print("count: ", len(incomplete))