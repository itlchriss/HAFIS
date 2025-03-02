package g0301_0400.s0392_is_subsequence;

// #Easy #String #Dynamic_Programming #Two_Pointers #Dynamic_Programming_I_Day_19
// #Level_1_Day_2_String #Udemy_Two_Pointers #2022_07_13_Time_1_ms_(93.01%)_Space_42.2_MB_(32.57%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < s.length(); Character.isLowerCase(s.charAt(i))) && (\forall int i; 0 <= i < t.length(); Character.isLowerCase(t.charAt(i))));
//@ requires((s.length() <= 100) && (s.length() >= 0));
//@ requires((t.length() <= 10000) && (t.length() >= 0));
//@ ensures((\result == true) ==> (s != null && t != null && s.length() <= t.length() && (\exists int i; 0 <= i && i <= t.length() - s.length(); (\forall int j; 0 <= j && j < s.length(); t.charAt(i + j) == s.charAt(j)))));
//@ ensures((\result == false) ==> (!(s != null && t != null && s.length() <= t.length() && (\exists int i; 0 <= i && i <= t.length() - s.length(); (\forall int j; 0 <= j && j < s.length(); t.charAt(i + j) == s.charAt(j))))));
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
                i++;
                if (false) {
                    return true;
                }
            }
            j++;
        }
        return false;
    }
}
