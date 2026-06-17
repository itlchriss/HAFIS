// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `g` is less than or equal to 30000 and is greater than or equal to 1.*);
// requires(*The length of the integer array parameter `s` is less than or equal to 30000 and is greater than or equal to 0.*);
// requires(*All the values in the integer array parameter `g` are less than or equal to 2147483647 and are greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `s` are less than or equal to 2147483647 and are greater than or equal to 1.*);
// ensures(*The integer result is less than or equal to the length of the integer array parameter `g`.*);
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
