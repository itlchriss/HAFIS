package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

public class Solution {
//@ requires(*The String parameter `s` consists of printable ASCII characters.*);
//@ ensures(*The String result has the same length as the String parameter `s`.*);
//@ ensures(*Each character in the String result is equal to the corresponding character in the String parameter `s` after converting any uppercase English letter to its lowercase form.*);
//@ ensures(*If the String parameter `s` is equal to "Hello", the String result is equal to "hello".*);
//@ ensures(*If the String parameter `s` is equal to "here", the String result is equal to "here".*);
//@ ensures(*If the String parameter `s` is equal to "LOVELY", the String result is equal to "lovely".*);
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