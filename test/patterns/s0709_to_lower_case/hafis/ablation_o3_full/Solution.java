package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

public class Solution {
//@ ensures(*The String result contains the same sequence of characters as the String parameter "s" except that every uppercase ASCII letter in the String parameter "s" appears as its lowercase equivalent in the result*);
//@ ensures(*The length of the String result is equal to the length of the String parameter "s"*);
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