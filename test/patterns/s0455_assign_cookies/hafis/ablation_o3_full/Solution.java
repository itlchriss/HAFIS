package g0401_0500.s0455_assign_cookies;

// #Easy #Array #Sorting #Greedy #2022_07_18_Time_12_ms_(41.00%)_Space_52.6_MB_(78.45%)

import java.util.Arrays;

public class Solution {
//@ requires(*The parameter int[] "s" must not be null*);
//@ ensures(*result int is greater than or equal to 0*);
//@ ensures(*result int is less than or equal to the length of int[] "g"*);
//@ ensures(*result int is less than or equal to the length of int[] "s"*);
//@ ensures(*result int equals 0 when the length of int[] "s" equals 0*);
//@ ensures(*For any feasible assignment where each child receives at most one cookie whose size is greater than or equal to that child’s greed factor the number of content children in that assignment does not exceed result int*);
//@ ensures(*There exists a feasible assignment where each child receives at most one cookie whose size is greater than or equal to that child’s greed factor and the number of content children in that assignment equals result int*);
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