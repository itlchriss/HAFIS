package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

public class Solution {
//@ requires(*All characters in the string parameter `s` are printable ASCII characters.*);
//@ ensures(*The length of the string result is equal to the length of the string parameter `s`.*);
//@ ensures(*All characters in the string result are printable ASCII characters.*);
//@ ensures(*For every position where the character in the string parameter `s` is an uppercase English letter, the character at the same position in the string result is equal to the lowercase version of that uppercase English letter.*);
//@ ensures(*For every position where the character in the string parameter `s` is not an uppercase English letter, the character at the same position in the string result is equal to the character at the same position in the string parameter `s`.*);
//@ ensures(*If the string parameter `s` is equal to "Hello", the string result is equal to "hello".*);
//@ ensures(*If the string parameter `s` is equal to "here", the string result is equal to "here".*);
//@ ensures(*If the string parameter `s` is equal to "LOVELY", the string result is equal to "lovely".*);
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