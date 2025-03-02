package g0401_0500.s0455_assign_cookies;

// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ @ requires g != null && s != null;
//@ @ requires g.length >= 1 && g.length <= 30000;
//@ @ requires s.length >= 0 && s.length <= 30000;
//@ @ requires (\forall int i; 0 <= i && i < g.length; 1 <= g[i] && g[i] <= Integer.MAX_VALUE);
//@ @ requires (\forall int j; 0 <= j && j < s.length; 1 <= s[j] && s[j] <= Integer.MAX_VALUE);
//@ @ ensures \result >= 0 && \result <= g.length;
//@ @ ensures (\forall int i; 0 <= i && i < g.length;
//@ @             (\exists int j; 0 <= j && j < s.length; s[j] >= g[i]) ==>
//@ @             (\exists int k; 0 <= k && k < \result; s[k] >= g[i]));
//@ @*/
//@ public int findContentChildren(int[] g, int[] s) {
//@ // Method implementation goes here
//@ }
//@ ```
//@ 
//@ ### Explanation:
//@ 
//@ - **Preconditions (`requires`):**
//@ - `g` and `s` are not null.
//@ - The length of `g` is between 1 and 30,000.
//@ - The length of `s` is between 0 and 30,000.
//@ - Each element in `g` is between 1 and `Integer.MAX_VALUE`.
//@ - Each element in `s` is between 1 and `Integer.MAX_VALUE`.
//@ 
//@ - **Postconditions (`ensures`):**
//@ - The result is between 0 and the length of `g`, as the maximum number of content children cannot exceed the number of children.
//@ - For every child `i` that can be content with at least one cookie, there exists a cookie `k` in the result that satisfies the child's greed factor.
//@ 
//@ This JML specification provides a formal contract for the method, ensuring that it behaves correctly according to the problem's requirements.
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
