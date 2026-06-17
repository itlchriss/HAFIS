// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming #Stack #Big_O_Time_O(n)_Space_O(1)
// #2023_08_09_Time_1_ms_(100.00%)_Space_41.4_MB_(85.22%)
// Dafny version of Solution

requires (s.length <= 30000) && (s.length >= 0)
requires (s.length <= 30000) && (s.length >= 0)
requires (s.size() <= 30000) && (s.size() >= 0)
requires (s.length() <= 30000) && (s.length() >= 0)
requires (s.size() <= 30000) && (s.size() >= 0)
// requires(*The string parameter `s` only contains the characters '(' and ')'.*);
ensures \result <= s.length
ensures \result <= s.length
ensures \result <= s.size()
ensures \result <= s.length()
ensures \result <= s.size()
// ensures(*If the string parameter `s` is equal to "(()", the integer result is equal to 2.*);
// ensures(*If the string parameter `s` is equal to ")()())", the integer result is equal to 4.*);
ensures (s.length() == 0) ==> (\result == 0)
    method longestValidParentheses(s: string) returns (result: int)
    {
        var max: int := 0;
        var left: int := 0;
        var right: int := 0;
        var n: int := |s|();
        var ch: char;
        for i := 0 to n
            invariant i >= 0
            invariant i <= n
        {
            ch := s[i];
            if  {
                left := left + 1;
            } else {
                right := right + 1;
            }
            if right > left {
                left := 0;
                right := 0;
            }
            if left == right {
                max := max(max, left + right);
            }
        }
        left := 0;
        right := 0;
        var i := n - 1;
        while i >= 0
            invariant true
        {
            ch := s[i];
            if  {
                left := left + 1;
            } else {
                right := right + 1;
            }
            if left > right {
                left := 0;
                right := 0;
            }
            if left == right {
                max := max(max, left + right);
            }
            i := i - 1;
        }
        result := max;
        return;
    }
