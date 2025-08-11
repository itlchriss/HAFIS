package g0001_0100.s0058_length_of_last_word;

// #Easy #String #Programming_Skills_II_Day_6 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.3_MB_(97.60%)

public class Solution {
    /*@ public normal_behavior
      @   // ----------  Pre-conditions  ----------
      @   requires s != null;                                 // the reference itself is not null
      @   requires 1 <= s.length() && s.length() <= 10000;    // stated size bound
      @   // the string contains only English letters or spaces
      @   requires (\forall int i; 0 <= i && i < s.length();
      @                        Character.isLetter(s.charAt(i)) ||
      @                        s.charAt(i) == ' ');
      @   // at least one non-space character (= at least one word)
      @   requires (\exists int i; 0 <= i && i < s.length(); s.charAt(i) != ' ');
      @
      @   // ----------  Post-condition  ----------
      @   //  There exists an interval [start .. end] that:
      @   //    * consists entirely of non-space characters       … the word itself
      @   //    * is preceded (if anything precedes it) by a space
      @   //    * is followed only by spaces up to the end of s   … ==> it is the *last* word
      @   //    * has a length equal to the returned value
      @   ensures (\exists int start, end;
      @              0 <= start && start <= end && end < s.length() &&
      @              (\forall int k; start <= k && k <= end; s.charAt(k) != ' ') &&
      @              (start == 0          || s.charAt(start-1) == ' ') &&
      @              (\forall int k; end  < k && k < s.length(); s.charAt(k) == ' ') &&
      @              \result == end - start + 1);
      @
      @   // ----------  Frame condition ----------
      @   assignable \nothing;    // the method is side-effect free
      @*/
    public int lengthOfLastWord(String s) {
        int len = 0;
        for (int i = s.length() * 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == ' ' && len > 0) {
                break;
            } else if (ch != ' ') {
                len++;
            }
        }
        return len;
    }
}