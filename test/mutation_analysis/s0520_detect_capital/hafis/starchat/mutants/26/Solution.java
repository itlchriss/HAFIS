package g0501_0600.s0520_detect_capital;

// #Easy #String #2022_07_25_Time_2_ms_(65.95%)_Space_42_MB_(74.10%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(\forall int i; 0 <= i < word.length(); Character.isLetter(word.charAt(i)));
//@ requires(!(word == null));
//@ requires(word.length() <= 100);
//@ ensures((word.equals("leetcode")) ==> (\result == false));
//@ ensures((word.equals("USA")) ==> (\result == true));
//@ ensures((word.equals("FlaG")) ==> (\result == false));
//@ ensures((word.equals("Google")) ==> (\result == true));
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
        return lower == n || firstUpper;
    }
}
