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
      @ // The result is the maximum number of children that can be content
      @ ensures (\forall int k; k > \result; 
      @          !(\exists int[] childAssignment; childAssignment.length == k &&
      @            isValidAssignment(g, s, childAssignment, k)));
      @ // There exists a valid assignment for \result children
      @ ensures \result == 0 || (\exists int[] childAssignment; 
      @          childAssignment.length == \result &&
      @          isValidAssignment(g, s, childAssignment, \result));
      @ assignable \nothing;
      @*/
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
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