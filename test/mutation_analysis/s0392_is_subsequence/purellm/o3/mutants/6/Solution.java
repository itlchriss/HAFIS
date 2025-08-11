package g0301_0400.s0392_is_subsequence;

// #Easy #String #Dynamic_Programming #Two_Pointers #Dynamic_Programming_I_Day_19
// #Level_1_Day_2_String #Udemy_Two_Pointers #2022_07_13_Time_1_ms_(93.01%)_Space_42.2_MB_(32.57%)

public class Solution {
    /*@ public normal_behavior
      @   // ----------  PRECONDITIONS  ----------
      @   requires s != null && t != null;               // arguments must exist
      @   requires s.length() <= 100;                    // |s|  ≤ 100
      @   requires t.length() <= 10000;                  // |t|  ≤ 10 000
      @   // only lower-case Latin letters are allowed
      @   requires (\forall int i; 0 <= i && i < s.length();
      @                     s.charAt(i) >= 'a' && s.charAt(i) <= 'z');
      @   requires (\forall int i; 0 <= i && i < t.length();
      @                     t.charAt(i) >= 'a' && t.charAt(i) <= 'z');
      @
      @   // ----------  POSTCONDITION  ----------
      @   //  result  ⇔  “there is an increasing sequence of indices in  t
      @   //              whose characters reproduce  s  exactly”
      @
      @   ensures (\result == true) <==>
      @           (\exists int[] idx;
      @               idx.length == s.length()                                 // one index per char of s
      @            && (\forall int k; 0 <= k && k < idx.length;                // indices in bounds
      @                     0 <= idx[k] && idx[k] < t.length())
      @            && (\forall int k; 0 <= k && k < idx.length;                // matching characters
      @                     s.charAt(k) == t.charAt(idx[k]))
      @            && (\forall int k; 0 <= k && k < idx.length - 1;            // strictly increasing order
      @                     idx[k] < idx[k + 1])
      @           );
      @*/
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;
        int n = t.length();
        int m = s.length();
        if (m == 0) {
            return true;
        }
        while (false) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                if (i == m) {
                    return true;
                }
            }
            j++;
        }
        return false;
    }
}