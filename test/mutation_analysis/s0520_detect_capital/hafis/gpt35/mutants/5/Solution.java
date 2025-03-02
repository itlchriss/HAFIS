package g0501_0600.s0520_detect_capital;

// #Easy #String #2022_07_25_Time_2_ms_(65.95%)_Space_42_MB_(74.10%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((word.length() <= 100) && (word.length() >= 1));
//@ requires(\exists int i; 0 <= i < word.length(); Character.isLetter(word.charAt(i)));
//@ ensures((word != null && word.length() > 0 && (\forall int i; 0 <= i < word.length(); Character.isUpperCase(word.charAt(i)))) ==> (\result == true));
//@ ensures((word != null && word.length() > 0 && (\forall int i; 0 <= i < word.length(); Character.isLowerCase(word.charAt(i)))) ==> (\result == true));
//@ ensures((Character.isUpperCase(word.charAt(0))) ==> (\result == true));
    public boolean detectCapitalUse(String word) {
        if (word == null) {
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
