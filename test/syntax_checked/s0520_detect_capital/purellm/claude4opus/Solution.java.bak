package g0501_0600.s0520_detect_capital;

// #Easy #String #2022_07_25_Time_2_ms_(65.95%)_Space_42_MB_(74.10%)

public class Solution {
    /*@ public normal_behavior
      @ requires word != null;
      @ requires 1 <= word.length() && word.length() <= 100;
      @ requires (\forall int i; 0 <= i && i < word.length();
      @           Character.isLetter(word.charAt(i)) &&
      @           (Character.isUpperCase(word.charAt(i)) ||
      @            Character.isLowerCase(word.charAt(i))));
      @ ensures \result == (
      @     // Case 1: All capitals
      @     (\forall int i; 0 <= i && i < word.length();
      @      Character.isUpperCase(word.charAt(i))) ||
      @     // Case 2: All lowercase
      @     (\forall int i; 0 <= i && i < word.length();
      @      Character.isLowerCase(word.charAt(i))) ||
      @     // Case 3: First capital, rest lowercase
      @     (Character.isUpperCase(word.charAt(0)) &&
      @      (\forall int i; 1 <= i && i < word.length();
      @       Character.isLowerCase(word.charAt(i))))
      @ );
      @*/
    public boolean detectCapitalUse(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        int upper = 0;
        int lower = 0;
        int n = word.length();
        boolean firstUpper = Character.isUpperCase(word.charAt(0));
        for (int i = 0; i < n; i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                upper++;
            } else if (Character.isLowerCase(word.charAt(i))) {
                lower++;
            }
        }
        if (firstUpper && upper > 1) {
            firstUpper = false;
        }
        return upper == n || lower == n || firstUpper;
    }
}