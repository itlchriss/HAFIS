package g0401_0500.s0455_assign_cookies;

// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)

import java.util.Arrays;

public class Solution {
    /*@ public normal_behavior
      @ requires g != null && s != null;
      @ requires 1 <= g.length && g.length <= 30000;
      @ requires 0 <= s.length && s.length <= 30000;
      @ requires (\forall int i; 0 <= i && i < g.length; 1 <= g[i] && g[i] <= Integer.MAX_VALUE);
      @ requires (\forall int j; 0 <= j && j < s.length; 1 <= s[j] && s[j] <= Integer.MAX_VALUE);
      @ ensures \result >= 0 && \result <= Math.min(g.length, s.length);
      @ ensures \result <= g.length;
      @ ensures \result <= s.length;
      @ assignable \nothing;
      @*/
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);

        int result = 0;
        int i = 0;
        int j = 0;
        //@ maintaining 0 <= i <= g.length;
        //@ maintaining 0 <= j <= s.length;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                result++;
                i++;
            }
            j++;
        }
        return result;
    }
}