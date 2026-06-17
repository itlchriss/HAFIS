// #Easy #String #Dynamic_Programming #Two_Pointers #Dynamic_Programming_I_Day_19
// #Level_1_Day_2_String #Udemy_Two_Pointers #2022_07_13_Time_1_ms_(93.01%)_Space_42.2_MB_(32.57%)
// Dafny version of Solution

requires (s.length <= 100) && (s.length >= 0)
requires (s.length <= 100) && (s.length >= 0)
requires (s.size() <= 100) && (s.size() >= 0)
requires (s.length() <= 100) && (s.length() >= 0)
requires (s.size() <= 100) && (s.size() >= 0)
requires (t.length <= 10000) && (t.length >= 0)
requires (t.length <= 10000) && (t.length >= 0)
requires (t.size() <= 10000) && (t.size() >= 0)
requires (t.length() <= 10000) && (t.length() >= 0)
requires (t.size() <= 10000) && (t.size() >= 0)
// requires(*The string parameter `s` and `t` consist only of lowercase English letters.*);
ensures (\result == true) ==> (s != null && t != null && s.length() <= t.length() && (\exists int i; 0 <= i && i <= t.length() - s.length(); (\forall int j; 0 <= j && j < s.length(); t.charAt(i + j) == s.charAt(j))))
ensures (\result == false) ==> (!(s != null && t != null && s.length() <= t.length() && (\exists int i; 0 <= i && i <= t.length() - s.length(); (\forall int j; 0 <= j && j < s.length(); t.charAt(i + j) == s.charAt(j)))))
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
