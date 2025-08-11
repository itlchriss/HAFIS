package g1001_1100.s1021_remove_outermost_parentheses;

// #Easy #String #Stack #2022_02_25_Time_4_ms_(75.39%)_Space_42.3_MB_(50.45%)

import java.util.ArrayList;
import java.util.List;

public class Solution {
//@ requires(*All characters in the string parameter `s` are either the character '(' or the character ')'.*);
//@ requires(*The string parameter `s` is a valid parentheses string.*);
//@ ensures(*The length of the string result is greater than or equal to 0 and is less than the length of the string parameter `s`.*);
//@ ensures(*The string result is obtained from the string parameter `s` by deleting the first character and the last character of every primitive valid parentheses substring of the string parameter `s` while preserving the relative order of all remaining characters.*);
//@ ensures(*If the string parameter `s` is equal to "(()())(())", the string result is equal to "()()()".*);
//@ ensures(*If the string parameter `s` is equal to "(()())(())(()(()))", the string result is equal to "()()()()(())".*);
//@ ensures(*If the string parameter `s` is equal to "()()", the string result is equal to "".*);
    public String removeOuterParentheses(String s) {
        List<String> primitives = new ArrayList<>();
        int i = 1;
        while (i < s.length()) {
            int initialI = i - 1;
            int left = 1;
            while (i < s.length() && left > 0) {
                if (s.charAt(i) == '(') {
                    left++;
                } else {
                    left--;
                }
                i++;
            }
            primitives.add(s.substring(initialI, i));
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (String primitive : primitives) {
            sb.append(primitive, 1, primitive.length() - 1);
        }
        return sb.toString();
    }
}