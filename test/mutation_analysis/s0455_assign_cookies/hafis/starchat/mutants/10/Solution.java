package g0401_0500.s0455_assign_cookies;

// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < s.length; s[i] <= 230) && (\forall int i; 0 <= i < s.length; s[i] >= 1));
//@ requires((\forall int i; 0 <= i < g.length; g[i] <= 230) && (\forall int i; 0 <= i < g.length; g[i] >= 1));
//@ requires((g.length <= 30000) && (g.length >= 1));
//@ requires((s.length <= 30000) && (s.length >= 1));
//@ ensures((\result >= 0) && (\result <= g.length));
//@ ensures(((g[0] == 1 && g[1] == 2 && g[2] == 3 && g.length == 3) && (s[0] == 1 && s[1] == 1 && s.length == 2)) ==> (\result == 1));
//@ ensures(((g[0] == 1 && g[1] == 2 && g.length == 2) && (s[0] == 1 && s[1] == 2 && s[2] == 3 && s.length == 3)) ==> (\result == 2));
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int result = 0;
        int i = 0;
        int j = 0;
        //@ maintaining 0 <= i <= g.length;
        //@ maintaining 0 <= j <= s.length;
        while (i < g.length) {
            if (s[j] >= g[i]) {
                result++;
                i++;
            }
            j++;
        }
        return result;
    }
}
