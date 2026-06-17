// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)
// Dafny version of Solution

requires (g.length <= 30000) && (g.length >= 1)
requires (g.length <= 30000) && (g.length >= 1)
requires (g.size() <= 30000) && (g.size() >= 1)
requires (g.length() <= 30000) && (g.length() >= 1)
requires (g.size() <= 30000) && (g.size() >= 1)
requires (s.length <= 30000) && (s.length >= 0)
requires (s.length <= 30000) && (s.length >= 0)
requires (s.size() <= 30000) && (s.size() >= 0)
requires (s.length() <= 30000) && (s.length() >= 0)
requires (s.size() <= 30000) && (s.size() >= 0)
requires (\forall int i; 0 <= i < g.length; g[i] <= 2147483647) && (\forall int i; 0 <= i < g.length; g[i] >= 1)
requires (\forall int i; 0 <= i < s.length; s[i] <= 2147483647) && (\forall int i; 0 <= i < s.length; s[i] >= 1)
ensures \result <= g.length
ensures \result <= g.length
ensures \result <= g.size()
ensures \result <= g.length()
ensures \result <= g.size()
// ensures(*If the integer array parameter `g` is equal to [1,2,3] and the integer array parameter `s` is equal to [1,1], the integer result is equal to 1.*);
// ensures(*If the integer array parameter `g` is equal to [1,2] and the integer array parameter `s` is equal to [1,2,3], the integer result is equal to 2.*);
    method findContentChildren(g: array<int>, s: array<int>) returns (result: int)
    {
        // Arrays.sort()
        // Arrays.sort()
        var result: int := 0;
        var i: int := 0;
        var j: int := 0;
        // invariant //@ maintaining 0 <= i <= g.length;
        // invariant //@ maintaining 0 <= j <= s.length;
        while i < |g| && j < |s|
            invariant true
        {
            if s[j] >= g[i] {
                result := result + 1;
                i := i + 1;
            }
            j := j + 1;
        }
        result := result;
        return;
    }
