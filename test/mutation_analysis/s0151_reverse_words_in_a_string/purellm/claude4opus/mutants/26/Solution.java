package g0101_0200.s0151_reverse_words_in_a_string;

// #Medium #String #Two_Pointers #Udemy_Strings
// #2022_06_25_Time_2_ms_(99.94%)_Space_42.4_MB_(88.57%)

public class Solution {
    /*@ private normal_behavior
      @*/

    /*@ private normal_behavior
      @ requires s != null;
      @*/

    /*@ private normal_behavior
      @*/

    /*@ private normal_behavior
      @ ensures \result != null && !\result.isEmpty();
      @ ensures (\forall int i; 0 <= i && i < \result.length(); \result.charAt(i) != ' ');
      @*/

    /*@ public normal_behavior
      @ requires s != null;
      @ requires 1 <= s.length() && s.length() <= 10000;
      @ requires (\exists int i; 0 <= i && i < s.length(); s.charAt(i) != ' ');
      @ requires (\forall int i; 0 <= i && i < s.length();
      @           s.charAt(i) == ' ' || Character.isLetter(s.charAt(i)) || Character.isDigit(s.charAt(i)));
      @ ensures \result != null;
      @ ensures !\result.isEmpty();
      @ ensures \result.length() > 0 && \result.charAt(0) != ' ' && \result.charAt(\result.length() - 1) != ' ';
      @ ensures !(\exists int i; 0 <= i && i < \result.length() - 1;
      @          \result.charAt(i) == ' ' && \result.charAt(i + 1) == ' ');
      @ pure
      @*/
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            if (s.charAt(i) == ' ') {
                i--;
                continue;
            }
            int start = s.lastIndexOf(' ', i);
            sb.append(' ');
            sb.append(s, start + 1, i + 1);

        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}