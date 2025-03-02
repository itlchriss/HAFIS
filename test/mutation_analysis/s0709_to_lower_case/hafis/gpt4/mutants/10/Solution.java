package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((s.length() <= 100) && (s.length() >= 1));
//@ requires(\forall int i; 0 <= i < s.length(); s.charAt(i) >= 32 && s.charAt(i) <= 126);
//@ ensures((\exists int i; 0 <= i < s.length(); Character.isUpperCase(s.charAt(i))) ==> (\forall int i; 0 <= i < \result.length(); Character.isLowerCase(\result.charAt(i))));
//@ ensures((s.equals("Hello")) ==> (\result.equals("hello")));
//@ ensures((s.equals("LOVELY")) ==> (\result.equals("lovely")));
//@ ensures((s.equals("here")) ==> (\result.equals("here")));
    public String toLowerCase(String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (false) {
                c[i] = (char) (c[i] - 'A' + 'a');
            }
        }
        return new String(c);
    }
}
