package g0901_1000.s0942_di_string_match;

// #Easy #Array #String #Math #Greedy #Two_Pointers
// #2022_03_30_Time_4_ms_(33.74%)_Space_48.7_MB_(20.18%)

public class Solution {
    /*@
   public normal_behavior
     //  ──  PRE-CONDITIONS  ────────────────────────────────────────────────
     requires s != null;
     requires 1 <= s.length() && s.length() <= 100000;
     requires (\forall int k; 0 <= k && k < s.length();
                  s.charAt(k) == 'I' || s.charAt(k) == 'D');

     //  ──  POST-CONDITIONS  ───────────────────────────────────────────────
     ensures \result != null;
     ensures \result.length == s.length() + 1;

     //  every value 0 … n occurs exactly once
     ensures (\forall int v; 0 <= v && v <= s.length();
                (\exists int idx; 0 <= idx && idx < \result.length;
                                 \result[idx] == v));
     ensures (\forall int i, j; 0 <= i && i < j && j < \result.length;
                                \result[i] != \result[j]);

     //  order constraints imposed by the characters in s
     ensures (\forall int i; 0 <= i && i < s.length();
                (s.charAt(i) == 'I' ==> \result[i]   < \result[i+1]) &&
                (s.charAt(i) == 'D' ==> \result[i]   > \result[i+1]));

     //  no visible side effects except object creation
////assignable \strictly_nothing;
 @*/
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
            if (arr[i + 1] == 0) {
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