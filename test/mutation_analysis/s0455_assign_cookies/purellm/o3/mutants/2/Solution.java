package g0401_0500.s0455_assign_cookies;

// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)

import java.util.Arrays;

public class Solution {
    /*@
      @ public normal_behavior
      @
      @   requires g != null && s != null;                        // arrays exist
      @   requires 1  <= g.length && g.length <= 30000;           // constraints
      @   requires      0 <= s.length && s.length <= 30000;
      @   requires (\forall int i; 0 <= i && i < g.length;
      @                 1 <= g[i] && g[i] <= Integer.MAX_VALUE);  // 1 … 2³¹-1
      @   requires (\forall int j; 0 <= j && j < s.length;
      @                 1 <= s[j] && s[j] <= Integer.MAX_VALUE);
      @
      @
      @   ensures 0 <= \result && \result <= g.length
      @                && \result <= s.length;
      @
      @   ensures (\exists int[] child; \exists int[] cookie;
      @               child.length  == \result &&
      @               cookie.length == \result               &&
      @
      @               (\forall int i; 0 <= i && i < child.length;
      @                       0 <= child[i]  && child[i]  < g.length   &&
      @                       0 <= cookie[i] && cookie[i] < s.length  &&
      @                       s[cookie[i]] >= g[child[i]]             &&
      @                       (\forall int j; 0 <= j && j < i;
      @                             child[i]  != child[j]  &&
      @                             cookie[i] != cookie[j])));
      @
      @   ensures (\forall int n;
      @               n > \result && n <= g.length && n <= s.length ==>
      @                 !(\exists int[] child2; \exists int[] cookie2;
      @                       child2.length  == n &&
      @                       cookie2.length == n &&
      @                       (\forall int i; 0 <= i && i < n;
      @                               0 <= child2[i]  && child2[i]  < g.length   &&
      @                               0 <= cookie2[i] && cookie2[i] < s.length  &&
      @                               s[cookie2[i]] >= g[child2[i]]             &&
      @                               (\forall int j; 0 <= j && j < i;
      @                                        child2[i]  != child2[j]  &&
      @                                        cookie2[i] != cookie2[j]))));
      @
      @ pure                                                        // no side effects
      @*/
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int result = 0;
        int i = 0;
        int j = 0;
        //@ maintaining 0 <= i <= g.length;
        //@ maintaining 0 <= j <= s.length;
        while (i != g.length && j < s.length) {
            if (s[j] >= g[i]) {
                result++;
                i++;
            }
            j++;
        }
        return result;
    }
}