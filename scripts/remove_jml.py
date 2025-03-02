from glob import glob
import shutil
import sys

path = sys.argv[1]
line = int(sys.argv[2])

for file in glob(path + '/*/*.java'):
    print(file)

    with open(file, 'r') as fp:
        program = fp.read()

    program = program.split('\n')
    shutil.copyfile(file, file + '.bak')
    program[line - 1] = program[line - 1].replace('//@', '//')

    with open(file, 'w+') as fp:
        fp.write('\n'.join(program))