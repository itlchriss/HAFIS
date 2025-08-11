package g0001_0100.s0032_longest_valid_parentheses;

// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming #Stack #Big_O_Time_O(n)_Space_O(1)
// #2023_08_09_Time_1_ms_(100.00%)_Space_41.4_MB_(85.22%)

public class Solution {
//@ ensures(*If the String `s` has length zero then int result equals zero*);
//@ ensures(*The int result denotes the maximum length of a contiguous substring of the String `s` that forms a well formed sequence of opening and closing brackets*);
//@ ensures(*The int result is never negative*);
//@ ensures(*The int result is never greater than the length of the String `s`*);
//@ ensures(*The int result equals zero when the String `s` contains no well formed bracket substring*);
//@ ensures(*The int result equals the length of the String `s` when the entire String `s` is a well formed bracket sequence*);
    public int longestValidParentheses(String s) {
        int max = 0;
        int left = 0;
        int right = 0;
        int n = s.length();
        char ch;
        for (int i = 0; i < n; i++) {
            ch = s.charAt(i);
            if (ch == '(') {
                left++;
            } else {
                right++;
            }
            if (right > left) {
                left = 0;
                right = 0;
            }
            if (left == right) {
                max = Math.max(max, left + right);
            }
        }
        left = 0;
        right = 0;
        for (int i = n - 1; i >= 0; i--) {
            ch = s.charAt(i);
            if (ch == '(') {
                left++;
            } else {
                right++;
            }
            if (left > right) {
                left = 0;
                right = 0;
            }
            if (left == right) {
                max = Math.max(max, left + right);
            }
        }
        return max;
    }
}