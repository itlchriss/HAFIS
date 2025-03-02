package g0001_0100.s0008_string_to_integer_atoi;

// #Medium #Top_Interview_Questions #String #2024_01_04_Time_1_ms_(100.00%)_Space_42.7_MB_(8.86%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ @ requires s != null; // The input string should not be null
//@ @ requires s.length() <= 200; // The length of the string should be within the constraints
//@ @ ensures \result >= Integer.MIN_VALUE && \result <= Integer.MAX_VALUE; // The result should be within the 32-bit signed integer range
//@ @ ensures (\forall int i; 0 <= i && i < s.length(); Character.isWhitespace(s.charAt(i)) ==> i < \old(s.indexOf(\result + "")) || i > \old(s.indexOf(\result + "") + (\result + "").length() - 1)); // Leading whitespace is ignored
//@ @ ensures (\forall int i; 0 <= i && i < s.length(); (s.charAt(i) == '-' || s.charAt(i) == '+') ==> i == \old(s.indexOf(\result + "")) - 1); // Sign character is correctly interpreted
//@ @ ensures (\forall int i; 0 <= i && i < s.length(); Character.isDigit(s.charAt(i)) ==> i >= \old(s.indexOf(\result + "")) && i < \old(s.indexOf(\result + "") + (\result + "").length())); // Digits are correctly parsed
//@ @ ensures (\result == 0 ==> (\forall int i; 0 <= i && i < s.length(); !Character.isDigit(s.charAt(i)))); // Result is 0 if no digits are read
//@ @ ensures (\result == Integer.MIN_VALUE || \result == Integer.MAX_VALUE ==> (\old(s.indexOf(\result + "")) == -1)); // Clamping occurs if out of range
//@ @*/
//@ public int myAtoi(String s) {
//@ // Implementation of the function
//@ }
//@ ```
//@ 
//@ ### Explanation:
//@ 
//@ - **Preconditions (`requires`)**:
//@ - The input string `s` should not be null.
//@ - The length of the string should be within the specified constraints (0 to 200).
//@ 
//@ - **Postconditions (`ensures`)**:
//@ - The result should be within the 32-bit signed integer range.
//@ - Leading whitespace is ignored, and the position of the parsed integer should not include whitespace.
//@ - If a sign character is present, it should be immediately before the parsed integer.
//@ - Digits are correctly parsed, and their positions should match the parsed integer.
//@ - If the result is 0, it indicates that no digits were read.
//@ - If the result is clamped to `Integer.MIN_VALUE` or `Integer.MAX_VALUE`, it means the parsed integer was out of range.
//@ 
//@ This JML specification captures the essential behavior of the `myAtoi` function as described in the problem statement. Note that JML is a formal specification language, and the actual implementation of the function would need to adhere to these specifications.
    public int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int i = 0;
        boolean negetiveSign = false;
        char[] input = str.toCharArray();        
        //@ loop_invariant 0 <= i <= input.length;
        while (i < input.length && input[i] == ' ') {
            i++;
        }
        if (i == input.length) {
            return 0;
        } else if (input[i] == '+') {
            i++;
        } else if (input[i] == '-') {
            i++;
            negetiveSign = true;
        }
        int num = 0;
        //@ loop_invariant 0 <= i <= input.length;
        //@ decreases input.length - i;
        while (i < input.length && input[i] <= '9' && input[i] >= '0') {
            // current char
            int tem = input[i] - '0';
            tem = negetiveSign ? -tem : tem;
            // avoid invalid number like 038
            if (num == 0 && tem == '0') {
                i++;
            } else if (num == Integer.MIN_VALUE / 10 && tem <= -8 || num < Integer.MIN_VALUE / 10) {
                return Integer.MIN_VALUE;
            } else if (num == Integer.MAX_VALUE / 10 && tem >= 7 || num > Integer.MAX_VALUE / 10) {
                return Integer.MAX_VALUE;
            } else {
                num = num * 10 + tem;
                i++;
            }
        }
        return num;
    }
}
