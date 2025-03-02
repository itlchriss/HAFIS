package g0901_1000.s0942_di_string_match;

// #Easy #Array #String #Math #Greedy #Two_Pointers
// #2022_03_30_Time_4_ms_(33.74%)_Space_48.7_MB_(20.18%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((s.length() <= 100000) && (s.length() >= 1));
//@ requires(\forall int i; 0 <= i < s.length(); s.charAt(i) == 'I' || s.charAt(i) == 'D');
//@ ensures((s.equals("IDID")) ==> (\result[0] == 0 && \result[1] == 4 && \result[2] == 1 && \result[3] == 3 && \result[4] == 2 && \result.length == 5));
//@ ensures((s.equals("DDI")) ==> (\result[0] == 3 && \result[1] == 2 && \result[2] == 0 && \result[3] == 1 && \result.length == 4));
//@ ensures((s.equals("III")) ==> (\result[0] == 0 && \result[1] == 1 && \result[2] == 2 && \result[3] == 3 && \result.length == 4));
    public int[] diStringMatch(String s) {
        int[] arr = new int[s.length() + 1];
        int max = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'D') {
                arr[i] = max;
                max--;
            }
        }
        for (int i = s.length() - 1; i >= 0 && max > 0; i--) {
            if (s.charAt(i) == 'I' && arr[i + 1] == 0) {
                arr[i - 1] = max;
                max--;
            }
        }
        for (int i = 0; i < arr.length && max > 0; i++) {
            if (arr[i] == 0) {
                arr[i] = max;
                max--;
            }
        }

        return arr;
    }
}
