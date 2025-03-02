import os
from glob import glob
import re

path = './test/mutate_check/s*'
methods = ['purellm', 'hafis']
models = ['gpt35', 'gpt4', 'starchat']

rep = r'^.*Solution\.java\:(\d+)\:\s+error\:.*$'

for folder in glob(path):
    for method in methods:
        for model in models:
            result_file_path = os.path.join(folder, method, model, 'result.txt')
            java_file_path = os.path.join(folder, method, model, 'Solution.java')
            with open(result_file_path, 'r') as f:
                result = f.read()
            if result:
                error_lines = []
                for line in result.split('\n'):
                    if r := re.search(rep, line):
                        # m = r.group()
                        error_lines.append(r.group(1))
                with open(java_file_path, 'r') as f:
                    program = f.read()
                p = program.split('\n')
                for line in error_lines:
                    p[int(line)-1] = p[int(line)-1].replace("//@", "//")
                # print('\n'.join(p))
                # exit(1)
                os.rename(java_file_path, java_file_path+'.bak')
                with open(java_file_path, 'w') as f:
                    f.write('\n'.join(p))