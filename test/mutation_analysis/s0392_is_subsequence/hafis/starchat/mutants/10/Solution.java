package g0301_0400.s0392_is_subsequence;

// #Easy #String #Dynamic_Programming #Two_Pointers #Dynamic_Programming_I_Day_19
// #Level_1_Day_2_String #Udemy_Two_Pointers #2022_07_13_Time_1_ms_(93.01%)_Space_42.2_MB_(32.57%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ ensures(((s.equals("xyz")) && (t.equals("abc"))) ==> (\result == false));
//@ ensures(((s.equals("abc")) && (t.equals("abcdefg"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ requires(\forall int i; 0 <= i < t.length(); Character.isLowerCase(t.charAt(i)));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ requires(\forall int i; 0 <= i < s.length(); Character.isLowerCase(s.charAt(i)));
//@ requires((s.length() <= 100) && (s.length() >= 0));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("abcabcabcabcabcabcabcabcabcabcabcabcabcabc"))) ==> (\result == true));
//@ requires((t.length() <= 10000) && (t.length() >= 0));
//@ ensures((t.contains(s)) ==> (\result == true));
//@ ensures(((s.equals("abc")) && (t.equals("ahbgdc"))) ==> (\result == true));
//@ ensures(((s.equals("a")) && (t.equals("ab"))) ==> (\result == true));
//@ ensures(((s.equals("axc")) && (t.equals("ahbgdc"))) ==> (\result == false));
//@ ensures(((s.equals("abc")) && (t.equals("abc"))) ==> (\result == true));
//@ ensures(((s.equals("abcd")) && (t.equals("abcde"))) ==> (\result == false));
//@ ensures(((s.equals("ab")) && (t.equals("ab"))) ==> (\result == true));
//@ ensures(((s.equals("b")) && (t.equals("ab"))) ==> (\result == false));
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

                if (i == m) {
                    return true;
                }
            }
            j++;
        }
        return false;
    }
}
