package g0901_1000.s0942_di_string_match;

// #Easy #Array #String #Math #Greedy #Two_Pointers
// #2022_03_30_Time_4_ms_(33.74%)_Space_48.7_MB_(20.18%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
// requires s != null && s.length() >= 1 && s.length() <= 100000
// requires (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == 'I' || s.charAt(i) == 'D')
// ensures \result != null
// ensures \result.length == s.length() + 1
//@ ensures (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == 'I' ==> \result[i] < \result[i + 1] && s.charAt(i) == 'D' ==> \result[i] > \result[i + 1])
    public int[] diStringMatch(String s) {
        int[] arr = new int[s.length() + 1];
        int max = s.length();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'D') {
                arr[i] = max;
                max--;
            }
        }
        for (int i = s.length() - 1; i >= 0 && false; i--) {
            if (s.charAt(i) == 'I' && arr[i + 1] == 0) {
                arr[i + 1] = max;
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
