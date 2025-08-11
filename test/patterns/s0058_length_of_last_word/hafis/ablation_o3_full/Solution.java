package g0001_0100.s0058_length_of_last_word;

// #Easy #String #Programming_Skills_II_Day_6 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.3_MB_(97.60%)

public class Solution {
//@ ensures(*String parameter `s` may contain one or more trailing space characters and such trailing space characters do not contribute to int result*);
//@ ensures(*if String parameter `s` contains exactly one word without any space characters, int result equals the total length of String parameter `s`*);
    public int lengthOfLastWord(String s) {
        int len = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == ' ' && len > 0) {
                break;
            } else if (ch != ' ') {
                len++;
            }
        }
        return len;
    }
}