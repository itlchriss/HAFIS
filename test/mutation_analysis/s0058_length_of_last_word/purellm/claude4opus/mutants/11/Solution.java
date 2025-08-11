package g0001_0100.s0058_length_of_last_word;

// #Easy #String #Programming_Skills_II_Day_6 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.3_MB_(97.60%)

public class Solution {
    /*@ public normal_behavior
      @ requires s != null;
      @ requires 1 <= s.length() && s.length() <= 10000;
      @ requires (\forall int i; 0 <= i && i < s.length();
      @           s.charAt(i) == ' ' || Character.isLetter(s.charAt(i)));
      @ requires (\exists int i; 0 <= i && i < s.length();
      @           s.charAt(i) != ' ');
      @ ensures \result >= 1;
      @ ensures (\exists int start, end;
      @           0 <= start && start <= end && end < s.length() &&
      @           s.charAt(end) != ' ' &&
      @           (start == 0 || s.charAt(start - 1) == ' ') &&
      @           (\forall int i; start <= i && i <= end; s.charAt(i) != ' ') &&
      @           (\forall int i; end < i && i < s.length(); s.charAt(i) == ' ') &&
      @           \result == end - start + 1);
      @*/
    public int lengthOfLastWord(String s) {
        int len = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == ' ' && len >= 0) {
                break;
            } else if (ch != ' ') {
                len++;
            }
        }
        return len;
    }
}