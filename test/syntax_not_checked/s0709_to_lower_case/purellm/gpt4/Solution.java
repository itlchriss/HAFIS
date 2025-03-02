package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires s != null;
//@ requires 1 <= s.length() && s.length() <= 100;
//@ requires (\forall int i; 0 <= i < s.length(); Character.isISOControl(s.charAt(i)) == false);
//@ ensures (\forall int i; 0 <= i < s.length();
(Character.isUpperCase(\old(s.charAt(i))) ==> \result.charAt(i) == Character.toLowerCase(\old(s.charAt(i)))) &&
(!Character.isUpperCase(\old(s.charAt(i))) ==> \result.charAt(i) == \old(s.charAt(i))));
    public String toLowerCase(String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (c[i] <= 'Z' && c[i] >= 'A') {
                c[i] = (char) (c[i] - 'A' + 'a');
            }
        }
        return new String(c);
    }
}
