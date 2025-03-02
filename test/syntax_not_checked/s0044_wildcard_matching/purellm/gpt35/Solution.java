package g0001_0100.s0044_wildcard_matching;

// #Hard #Top_Interview_Questions #String #Dynamic_Programming #Greedy #Recursion
// #Udemy_Dynamic_Programming #2023_08_11_Time_2_ms_(99.87%)_Space_43.2_MB_(99.49%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires inputString != null && pattern != null
//@ requires inputString.length() <= 2000 && pattern.length() <= 2000
//@ requires (\forall int i; 0 <= i && i < inputString.length(); Character.isLowerCase(inputString.charAt(i)))
//@ requires (\forall int i; 0 <= i && i < pattern.length(); Character.isLowerCase(pattern.charAt(i)) || pattern.charAt(i) == '?' || pattern.charAt(i) == '*')
//@ ensures \result == true || \result == false
//@ ensures (\forall int i; 0 <= i && i < inputString.length(); \result == true && (pattern.charAt(i) == inputString.charAt(i) || pattern.charAt(i) == '?'))
//@ ensures (\forall int i; 0 <= i && i < inputString.length(); \result == true && (pattern.charAt(i) == '*' || pattern.charAt(i) == '?' || pattern.charAt(i) == inputString.charAt(i)))
//@ ensures (\forall int i; 0 <= i && i < inputString.length(); \result == true && (pattern.charAt(i) == '*' || pattern.charAt(i) == '?' || pattern.charAt(i) == inputString.charAt(i) || pattern.charAt(i) == '*'))
//@ ensures (\forall int i; 0 <= i && i < inputString.length(); \result == false && (pattern.charAt(i) != inputString.charAt(i) && pattern.charAt(i) != '?'))
//@ ensures (\forall int i; 0 <= i && i < inputString.length(); \result == false && (pattern.charAt(i) != inputString.charAt(i) && pattern.charAt(i) != '?' && pattern.charAt(i) != '*'))
    public boolean isMatch(String inputString, String pattern) {
        int i = 0;
        int j = 0;
        int starIdx = -1;
        int lastMatch = -1;
        while (i < inputString.length()) {
            if (j < pattern.length()
                    && (inputString.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '?')) {
                i++;
                j++;
            } else if (j < pattern.length() && pattern.charAt(j) == '*') {
                starIdx = j;
                lastMatch = i;
                j++;
            } else if (starIdx != -1) {
                // there is a no match and there was a previous star, we will reset the j to indx
                // after star_index
                // lastMatch will tell from which index we start comparing the string if we
                // encounter * in pattern
                j = starIdx + 1;
                // we are saying we included more characters in * so we incremented the
                lastMatch++;
                // index
                i = lastMatch;
            } else {
                return false;
            }
        }
        boolean isMatch = true;
        while (j < pattern.length() && pattern.charAt(j) == '*') {
            j++;
        }
        if (i != inputString.length() || j != pattern.length()) {
            isMatch = false;
        }
        return isMatch;
    }
}
