// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming
// #Algorithm_II_Day_18_Dynamic_Programming #Dynamic_Programming_I_Day_19
// #Udemy_Dynamic_Programming #Big_O_Time_O(n^2)_Space_O(n2)
// #2023_08_11_Time_4_ms_(90.13%)_Space_41.8_MB_(99.78%)
// Dafny version of Solution

requires (w1.length <= 500) && (w1.length >= 0)
requires (w1.length <= 500) && (w1.length >= 0)
requires (w1.size() <= 500) && (w1.size() >= 0)
requires (w1.length() <= 500) && (w1.length() >= 0)
requires (w1.size() <= 500) && (w1.size() >= 0)
requires (w2.length <= 500) && (w2.length >= 0)
requires (w2.length <= 500) && (w2.length >= 0)
requires (w2.size() <= 500) && (w2.size() >= 0)
requires (w2.length() <= 500) && (w2.length() >= 0)
requires (w2.size() <= 500) && (w2.size() >= 0)
// requires(*All characters in the string parameter `w1` and `w2` are lowercase English letters.*);
// ensures(*The integer result is equal to the minimum number of operations required to convert `w1` to `w2`.*);
// ensures(*If the string parameter `w1` is equal to "horse" and the string parameter `w2` is equal to "ros", the integer result is equal to 3.*);
// ensures(*If the string parameter `w1` is equal to "intention" and the string parameter `w2` is equal to "execution", the integer result is equal to 5.*);
    method minDistance(w1: string, w2: string) returns (result: int)
    {
        var n1: int := |w1|();
        var n2: int := |w2|();
        if n2 > n1 {
            result := minDistance(w2, w1);
            return;
        }
        var dp := new int[n2 + 1];
        // invariant //@ maintaining 0 <= j <= n2 + 1;
        for j := 0 to n2 + 1
            invariant j >= 0
            invariant j <= n2 + 1
        {
            dp[j] := j;
        }
        // invariant //@ maintaining 1 <= i <= n1 + 1;
        for i := 1 to n1 + 1
            invariant i >= 1
            invariant i <= n1 + 1
        {
            var pre: int := dp[0];
            dp[0] := i;
            // invariant //@ maintaining 1 <= j <= n2 + 1;
            for j := 1 to n2 + 1
                invariant j >= 1
                invariant j <= n2 + 1
            {
                var tmp: int := dp[j];
                // dp[j] =
                // w1.charAt(i - 1) != w2.charAt(j - 1)
                // ? 1 + Math.min(pre, Math.min(dp[j], dp[j - 1]))
                // : pre;
                pre := tmp;
            }
        }
        result := dp[n2];
        return;
    }
