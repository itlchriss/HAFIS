import glob
import os

models = ['gpt35', 'gpt4', 'starchat']
jml_count = 0
error_count = 0
error_set = []

expected_wrong = [
    {
        'dataset': './test/s0165_compare_version_numbers/hafis/gpt35/build/Solution.java',
        'jml': [
            r'//@ ensures((\result == -1) ==> (version1 < version2));',
            r'//@ ensures((\result == 1) ==> (version1 > version2));',
            # r'requires((s <= 100) && (s >= 1));'
        ]
    },
    {
        'dataset': './test/s0008_string_to_integer_atoi/hafis/gpt35/build/Solution.java',
        'jml': [
            r'//@ ensures((\result == 0) ==> (\exists int i; 0 <= i < s.length(); Character.isDigit(s.charAt(i))));',
            r'//@ ensures(((\result == -42) && (s.equals(" negative 42"))) ==> (\result == -42));',
            r'//@ ensures(((\result == 4193) && (s.equals("4193 with words"))) ==> (\result == 4193));',
            r'//@ ensures(((\result == 42) && (s.equals("42"))) ==> (\result == 42));',
            r'//@ ensures(((\result == -2147483648) && (s.equals("-91283472332"))) ==> (\result == -2147483648));',
            r'//@ ensures((\result == 0) ==> (!(\exists int i; 0 <= i < s.length(); Character.isDigit(s.charAt(i)))));',
            r"//@ ensures((result == 0) ==> (!(\exists int i; 0 <= i < s.length(); Character.isDigit(s.charAt(i)))));"
        ]
    },
    {
        'dataset': './test/s0191_number_of_1_bits/hafis/gpt35/build/Solution.java',
        'jml': [
            r'//@ ensures((n == 11111111111111111111111111111101) ==> (\result == 31));'
        ]
    },
    {
        'dataset': './test/s0476_number_complement/hafis/gpt35/build/Solution.java',
        'jml': [r'//@ requires((num >= 1) && (num < 2147483648));']
    },
    {
        'dataset': './test/s0165_compare_version_numbers/hafis/gpt35/build/Solution.java',
        'jml': [
            r'//@ ensures((\result == -1) ==> (version1 < version2));',
            r'//@ ensures((\result == 1) ==> (version1 > version2));'
        ]
    },
    {
        'dataset': './test/s0008_string_to_integer_atoi/hafis/gpt35/build/Solution.java',
        'jml': [
            r'//@ ensures((\result == 0) ==> (!(\exists int i; 0 <= i < s.length(); Character.isDigit == (s.charAt(i)))));',
            r'//@ ensures(((\result == -2147483648) && (s.equals("-91283472332"))) ==> (\result == -2147483648));',
            r'//@ ensures(((\result == -42) && (s.equals(" negative 42"))) ==> (\result == -42));',
            r'//@ ensures(((\result == 4193) && (s.equals("4193 with words"))) ==> (\result == 4193));',
            r'//@ ensures(((\result == 42) && (s.equals("42"))) ==> (\result == 42));'

        ]
    },
    {
        'dataset': './test/s0191_number_of_1_bits/hafis/gpt4/build/Solution.java',
        'jml': [
            r'//@ ensures((n == 11111111111111111111111111111101) ==> (\result == 31));'
        ]
    },
    {
        'dataset': './test/s0476_number_complement/hafis/gpt4/build/Solution.java',
        'jml': [r'//@ requires((num < 2147483648) && (num >= 1));'] 
    },
    {
        'dataset': './test/s0888_fair_candy_swap/hafis/gpt4/build/Solution.java',
        'jml': [r'//@ ensures((\result[0] + \result[1]) && (((\sum int i; 0 <= i < aliceSizes.length; aliceSizes[i]) - \result[0] == (\sum int i; 0 <= i < bobSizes.length; bobSizes[i]) - \result[1]) && (\result[1] + \result[0])));']
    },
    {
        'dataset': './test/s0299_bulls_and_cows/hafis/starchat/build/Solution.java',
        'jml': [r'//@ ensures((\result >= 0) && (\result <= secret.length()));']
    },
    {
        'dataset': './test/s0476_number_complement/hafis/starchat/build/Solution.java',
        'jml': [r'//@ ensures((\result < 2147483648) && (\result >= 0));',
                r'//@ requires((num < 2147483648) && (num >= 1));']
    },
    {
        'dataset': './test/s0331_verify_preorder_serialization_of_a_binary_tree/hafis/starchat/build/Solution.java',
        'jml': [
            r'//@ ensures((preorder.equals("1,#")) ==> (\result == 0));',
            r'//@ ensures((preorder.equals("9,#,#,1")) ==> (\result == 0));',
            r'//@ ensures((preorder.equals("9,3,4,#,#,1,#,#,2,#,6,#,#")) ==> (\result == 1));',
            r'//@ ensures((\result == 1) || (\result == 0));'
        ]
    },
    {
        'dataset': './test/s0229_majority_element_ii/hafis/starchat/build/Solution.java',
        'jml': [
            r'//@ ensures((nums.get(0) == 1) ==> (\result.get(0) == 1));',
            r'//@ ensures((nums.get(0) == 1 && nums.get(1) == 2) ==> (\result.get(0) == 1 && \result.get(1) == 2));',
            r'//@ ensures((nums.get(0) == 3 && nums.get(1) == 2 && nums.get(2) == 3) ==> (\result.get(0) == 3));',
            r'//@ requires((\forall int i; 0 <= i < nums.size(); nums.get(i) <= 1000000000) && (\forall int i; 0 <= i < nums.size(); nums.get(i) >= -1000000000));',
            r'//@ requires((nums.size() <= 50000) && (nums.size() >= 1));'
        ]
    },
    {
        'dataset': './test/s0191_number_of_1_bits/hafis/starchat/build/Solution.java',
        'jml': [
            r'//@ ensures((n == 11111111111111111111111111111101) ==> (\result == 31));'
        ]
    },
    {
        'dataset': './test/s0020_valid_parentheses/hafis/starchat/build/Solution.java',
        'jml': [
            r'//@ ensures((s.equals("([)]")) ==> (\result == 0));',
            r'//@ ensures((s.equals("{[]}")) ==> (\result == 1));',
            r'//@ ensures((s.equals("()[]{}")) ==> (\result == 1));',
            r'//@ ensures((s.equals("(]")) ==> (\result == 0));',
            r'//@ ensures((s.equals("()")) ==> (\result == 1));',
            r'//@ ensures((\result == 0) || (\result == 1));'
        ]
    }
]

error_set = {}
import re
error_count = 0

for model in models:
    for file in glob.glob('./test/s*/hafis/' + model):
        # with open(os.path.join(file, 'count.txt')) as fp:
            # _r = fp.read()
        # _count = int(_r)
        _count = len(glob.glob(file + '/jml/*'))
        with open(os.path.join(file, 'syntax_checking.txt')) as fp:
            _error = fp.read()
        if 'error' in _error:
            # error_set.append(file)
            # print(_error)
            for d in expected_wrong:
                if d['dataset'].replace('/build/Solution.java', '') == file:
                    for line in _error.split('\n'):
                        if r := re.search(r'//@ (ensures|requires).*', line):
                            # print(r.group())
                            if r.group().strip() not in d['jml']:
                                s = d['jml'][-1].replace('\\', '')
                                k = r.group().replace('\\', '')
                                for i, c in enumerate(s):
                                    if c != k[i]:
                                        print(i, c, k[i])
                                        print(s[:i])
                                        print(k[:i])
                                        break
                                if file not in error_set:
                                    error_set[file] = []
                                error_set[file].append(r.group())
                                # error_jml.append(r.group())
                            error_count += 1
            # error_jml.append(_error)
        # jml_count += _count


print('Error count:', error_count)
print('Unexpected error set:')
for file in error_set:
    print(file)
    for error in error_set[file]:
        print(error)
    print()
