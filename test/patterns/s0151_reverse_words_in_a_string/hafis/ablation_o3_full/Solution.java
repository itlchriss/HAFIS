package g0101_0200.s0151_reverse_words_in_a_string;

// #Medium #String #Two_Pointers #Udemy_Strings
// #2022_06_25_Time_2_ms_(99.94%)_Space_42.4_MB_(88.57%)

public class Solution {
//@ requires(*The String parameter `s` may include upper case English letters lower case English letters digits and space characters with no other character types permitted.*);
//@ ensures(*The String result shall present the words that appear in `s` in precisely reverse order joined by exactly one space character.*);
//@ ensures(*The String result shall exclude any leading or trailing space characters so that its first and last characters are never spaces.*);
//@ ensures(*The String result shall collapse every series of two or more consecutive space characters that occur in `s` into a single space character used as the separator between adjacent words.*);
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            if (s.charAt(i) == ' ') {
                i--;
                continue;
            }
            int start = s.lastIndexOf(' ', i);
            sb.append(' ');
            sb.append(s, start + 1, i + 1);
            i = start - 1;
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}