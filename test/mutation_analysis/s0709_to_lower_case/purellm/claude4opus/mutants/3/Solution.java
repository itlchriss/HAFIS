package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

public class Solution {
    /*@ public normal_behavior
      @ requires s != null;
      @ requires 1 <= s.length() && s.length() <= 100;
      @ requires (\forall int i; 0 <= i && i < s.length();
      @           32 <= s.charAt(i) && s.charAt(i) <= 126);
      @ ensures \result != null;
      @ ensures \result.length() == s.length();
      @ ensures (\forall int i; 0 <= i && i < s.length();
      @           (65 <= s.charAt(i) && s.charAt(i) <= 90) ==>
      @               \result.charAt(i) == (char)(s.charAt(i) + 32));
      @ ensures (\forall int i; 0 <= i && i < s.length();
      @           !(65 <= s.charAt(i) && s.charAt(i) <= 90) ==>
      @               \result.charAt(i) == s.charAt(i));
      @ assignable \nothing;
      @ also
      @ public exceptional_behavior
      @ requires s == null;
      @ signals_only NullPointerException;
      @*/
    public String toLowerCase(String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (c[i] < 'Z' && c[i] >= 'A') {
                c[i] = (char) (c[i] - 'A' + 'a');
            }
        }
        return new String(c);
    }
}