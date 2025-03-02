import sys
import os
import glob
import re

# def main(srcpath: str, app: str) -> None:
#     _apppath = os.path.join(srcpath, app)
#     _progpath = os.path.join(srcpath, "Solution.java.no_annotation")
#     if not os.path.exists(_apppath) or not os.path.exists(_progpath):
#         exit(-1)
    
#     with open(_progpath, 'r') as fp:
#         _program = fp.read()
#         if _program:
#             _program = _program.strip()
    
#     import glob
#     _files = glob.glob(os.path.join(_apppath, "jml", "*.jml"))
#     if not _files:
#         exit(-2)
    
#     _tmp = []
#     for file in _files:
#         with open(file, 'r') as fp:
#             data = fp.read()
#         _tmp.append(r'//@ ' + data.replace('\n', '').replace('\ forall', r'\forall'))
#     _tmp = '\n'.join(_tmp)

#     import re
#     _r = re.search(r'(\s+)?public.*\)(\s+)?[{]?', _program, re.ASCII)
#     _program = _program[:_r.start()] + '\n' + _tmp + _program[_r.start():]

#     _target = 'public class'
#     if '@SuppressWarning' in _program:
#         _target = '@SuppressWarning' 
#     if 'import java.util.Arrays' not in _program:
#         _program = _program.replace(_target, 'import java.util.Arrays;\n\n%s' % _target)
#     if 'import java.util.Collections' not in _program:
#         _program = _program.replace(_target, 'import java.util.Collections;\n\n%s' % _target)
    
#     print(_program)


# mode: 0 - all jmls is in one file. 1 - each jml is in a separate file.
def main(srcpath: str, jmlpath: str, mode: str) -> None:
    # _apppath = os.path.join(srcpath, app)
    # _progpath = os.path.join(srcpath, "Solution.java.no_annotation")
    # if not os.path.exists(_apppath) or not os.path.exists(_progpath):
    #     exit(-1)

    mode = int(mode)
    
    with open(srcpath, 'r') as fp:
        _program = fp.read()
        if _program:
            _program = _program.strip()
    
    _tmp = []
    if mode == 1:
        _files = glob.glob(os.path.join(jmlpath, "jml", "*.jml"))
    # if not _files:
        # exit(-2)
        for file in _files:
            with open(file, 'r') as fp:
                data = fp.read()
            _tmp.append(r'//@ ' + data.replace('\n', '').replace('\ forall', r'\forall'))
    else:
        with open(jmlpath, 'r') as fp:
            raw = fp.read()
        
        multi = False
        for line in raw.split('\n'):
            if 'Explanation' in line:
                break
            elif line.strip() == '```' or 'public' in line or line.strip() == '}':
                continue
            elif '//' in line and '//@' not in line:
                continue
            elif r := re.search(r'^- ((requires|ensures).*)', line):
                _tmp.append(r'//@ ' + r.group(1).strip())
            elif r := re.search(r'^/\*@$', line):
                # print(line)
                multi = True
            elif r := re.search(r'^@\*/$', line):
                multi = False
            elif multi:
                if r := re.search(r'((requires|ensures).*)', line):
                    _tmp.append(r'//@ ' + r.group(0).strip())
                else:
                    _tmp.append(line.strip())
            elif r := re.search(r'^//@ ((requires|ensures).*)', line):
                _tmp.append(r.group(0).strip())
    
    count = len(_tmp)
    _count_file_path = '/'.join(jmlpath.split('/')[:-1])
    with open(os.path.join(_count_file_path, 'count.txt'), 'w+') as fp:
        fp.write(str(count))
    _tmp = '\n'.join(_tmp)

    _r = re.search(r'(\s+)?public.*\)(\s+)?[{]?', _program, re.ASCII)
    _program = _program[:_r.start()] + '\n' + _tmp + _program[_r.start():]

    _target = 'public class'
    if '@SuppressWarning' in _program:
        _target = '@SuppressWarning' 
    if 'import java.util.Arrays' not in _program:
        _program = _program.replace(_target, 'import java.util.Arrays;\n\n%s' % _target)
    if 'import java.util.Collections' not in _program:
        _program = _program.replace(_target, 'import java.util.Collections;\n\n%s' % _target)
    
    print(_program)



if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2], sys.argv[3])