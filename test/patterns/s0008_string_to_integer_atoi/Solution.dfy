// #Medium #Top_Interview_Questions #String #2024_01_04_Time_1_ms_(100.00%)_Space_42.7_MB_(8.86%)
// Dafny version of Solution

// requires(*The string parameter `str` is less than or equal to 200 characters in length.*);
// requires(*The string parameter `str` can contain English letters (lower-case and upper-case), digits (0-9), spaces, '+', '-', and '.'.*);
// ensures(*The integer result is within the range of a 32-bit signed integer [-2^31, 2^31 - 1].*);
// ensures(*If the string parameter `str` is equal to "42", the integer result is equal to 42.*);
// ensures(*If the string parameter `str` is equal to " -42", the integer result is equal to -42.*);
// ensures(*If the string parameter `str` is equal to "4193 with words", the integer result is equal to 4193.*);
// ensures(*If the string parameter `str` is equal to "words and 987", the integer result is equal to 0.*);
// ensures(*If the string parameter `str` is equal to "-91283472332", the integer result is equal to -2147483648.*);
    method myAtoi(str: string) returns (result: int)
    {
        if str == null || |str|() == 0 {
            result := 0;
            return;
        }
        var i: int := 0;
        var negetiveSign: bool := false;
        var input: array<char> := str.toCharArray();
        // invariant 0 <= i <= input.length;
        while i < |input| && input[i] == ' '
            invariant true
        {
            i := i + 1;
        }
        if i == |input| {
            result := 0;
            return;
        } else if input[i] == '+' {
            i := i + 1;
        } else if input[i] == '-' {
            i := i + 1;
            negetiveSign := true;
        }
        var num: int := 0;
        // invariant 0 <= i <= input.length;
        while i < |input| && input[i] <= '9' && input[i] >= '0'
            invariant true
        {
            // current char
            var tem: int := input[i] - '0';
            tem := if negetiveSign then -tem else tem;
            // avoid invalid number like 038
            if num == 0 && tem == '0' {
                i := i + 1;
            } else if num == -2147483648 / 10 && tem <= -8 || num < -2147483648 / 10 {
                result := -2147483648;
                return;
            } else if num == 2147483647 / 10 && tem >= 7 || num > 2147483647 / 10 {
                result := 2147483647;
                return;
            } else {
                num := num * 10 + tem;
                i := i + 1;
            }
        }
        result := num;
        return;
    }
