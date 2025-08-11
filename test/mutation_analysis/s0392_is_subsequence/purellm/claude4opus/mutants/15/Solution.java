package g0301_0400.s0392_is_subsequence;

// #Easy #String #Dynamic_Programming #Two_Pointers #Dynamic_Programming_I_Day_19
// #Level_1_Day_2_String #Udemy_Two_Pointers #2022_07_13_Time_1_ms_(93.01%)_Space_42.2_MB_(32.57%)

public class Solution {
    /*@ public normal_behavior
      @ requires s != null && t != null;
      @ requires 0 <= s.length() && s.length() <= 100;
      @ requires 0 <= t.length() && t.length() <= 10000;
      @ requires (\forall int i; 0 <= i && i < s.length();
      @           'a' <= s.charAt(i) && s.charAt(i) <= 'z');
      @ requires (\forall int i; 0 <= i && i < t.length();
      @           'a' <= t.charAt(i) && t.charAt(i) <= 'z');
      @ ensures \result == isSubsequence(s, t);
      @ pure
      @*/
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;
        int n = t.length();
        int m = s.length();
        if (m == 0) {
            return true;
        }
        while (j < n) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                if (i == m) {
                    return true;
                }
            }

        }
        return false;
    }
}