// #Easy #String #Dynamic_Programming #Two_Pointers #Dynamic_Programming_I_Day_19
// #Level_1_Day_2_String #Udemy_Two_Pointers #2022_07_13_Time_1_ms_(93.01%)_Space_42.2_MB_(32.57%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 100 and is greater than or equal to 0.*);
// requires(*The length of the string parameter `t` is less than or equal to 10000 and is greater than or equal to 0.*);
// requires(*The string parameter `s` and `t` consist only of lowercase English letters.*);
// ensures(*If the boolean result is equal to true, the string parameter `s` is a subsequence of the string parameter `t`.*);
// ensures(*If the boolean result is equal to false, the string parameter `s` is not a subsequence of the string parameter `t`.*);
    method isSubsequence(s: string, t: string) returns (result: bool)
    {
        var i: int := 0;
        var j: int := 0;
        var n: int := |t|();
        var m: int := |s|();
        if m == 0 {
            result := true;
            return;
        }
        while j < n
            invariant true
        {
            if s[i] == t[j] {
                i := i + 1;
                if i == m {
                    result := true;
                    return;
                }
            }
            j := j + 1;
        }
        result := false;
        return;
    }
