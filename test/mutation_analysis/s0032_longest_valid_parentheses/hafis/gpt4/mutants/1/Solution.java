package g0001_0100.s0032_longest_valid_parentheses;

// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming #Stack #Big_O_Time_O(n)_Space_O(1)
// #2023_08_09_Time_1_ms_(100.00%)_Space_41.4_MB_(85.22%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((s.length() <= 30000) && (s.length() >= 0));
//@ requires(\forall int i; 0 <= i < s.length(); s.charAt(i) == '(' || s.charAt(i) == ')');
//@ ensures((\result >= 0) && (\result <= s.length()));
//@ ensures((s.equals("(()")) ==> (\result == 2));
//@ ensures((s.equals("")) ==> (\result == 0));
//@ ensures((s.equals(")()())")) ==> (\result == 4));
    public int longestValidParentheses(String s) {
        int max = 0;
        int left = 0;
        int right = 0;
        int n = s.length();
        char ch;
        for (int i = 0; i <= n; i++) {
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
