package g0001_0100.s0008_string_to_integer_atoi;

// #Medium #Top_Interview_Questions #String #2024_01_04_Time_1_ms_(100.00%)_Space_42.7_MB_(8.86%)

public class Solution {
    /*@ private normal_behavior
      @ requires s != null;
      @ ensures \result == computeAtoi(s);
      @
      @ private model pure int parseResult(String s) {
      @     int index = skipWhitespace(s, 0);
      @     if (index >= s.length()) return 0;
      @
      @     boolean negative = false;
      @     if (s.charAt(index) == '-') {
      @         negative = true;
      @         index++;
      @     } else if (s.charAt(index) == '+') {
      @         index++;
      @     }
      @
      @     if (index >= s.length() || !isDigit(s.charAt(index))) return 0;
      @
      @     long result = 0;
      @     while (index < s.length() && isDigit(s.charAt(index))) {
      @         result = result * 10 + (s.charAt(index) - '0');
      @         if (negative && -result < Integer.MIN_VALUE) return Integer.MIN_VALUE;
      @         if (!negative && result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
      @         index++;
      @     }
      @
      @     return negative ? -(int)result : (int)result;
      @ }
      @*/

    /*@ private normal_behavior
      @ requires s != null && 0 <= start && start <= s.length();
      @ ensures \result >= start && \result <= s.length();
      @ ensures \result == s.length() || s.charAt(\result) != ' ';
      @ ensures (\forall int i; start <= i && i < \result; s.charAt(i) == ' ');
      @
      @ private model pure int skipWhitespace(String s, int start) {
      @     int i = start;
      @     while (i < s.length() && s.charAt(i) == ' ') {
      @         i++;
      @     }
      @     return i;
      @ }
      @*/

    /*@ private normal_behavior
      @ ensures \result <==> ('0' <= c && c <= '9');
      @
      @ private model pure boolean isDigit(char c) {
      @     return '0' <= c && c <= '9';
      @ }
      @*/

    /*@ private normal_behavior
      @ requires s != null;
      @ ensures \result >= Integer.MIN_VALUE && \result <= Integer.MAX_VALUE;
      @
      @ private model pure int computeAtoi(String s) {
      @     // This is an abstract specification of the algorithm
      @     // Step 1: Skip leading whitespace
      @     int i = 0;
      @     while (i < s.length() && s.charAt(i) == ' ') i++;
      @
      @     if (i >= s.length()) return 0;
      @
      @     // Step 2: Check for sign
      @     boolean negative = false;
      @     if (s.charAt(i) == '-') {
      @         negative = true;
      @         i++;
      @     } else if (s.charAt(i) == '+') {
      @         i++;
      @     }
      @
      @     // Step 3: Read digits
      @     long num = 0;
      @     boolean hasDigits = false;
      @     while (i < s.length() && '0' <= s.charAt(i) && s.charAt(i) <= '9') {
      @         hasDigits = true;
      @         num = num * 10 + (s.charAt(i) - '0');
      @
      @         // Step 5: Check for overflow/underflow during parsing
      @         if (negative && -num < Integer.MIN_VALUE) {
      @             return Integer.MIN_VALUE;
      @         }
      @         if (!negative && num > Integer.MAX_VALUE) {
      @             return Integer.MAX_VALUE;
      @         }
      @         i++;
      @     }
      @
      @     // Step 4: If no digits were read, return 0
      @     if (!hasDigits) return 0;
      @
      @     // Step 6: Return the result with proper sign
      @     return negative ? -(int)num : (int)num;
      @ }
      @*/

    /*@ public normal_behavior
      @ // requires s != null;
      @ // // requires 0 <= s.length() && s.length() <= 200;
      @ // requires (\forall int i; 0 <= i && i < s.length();
      @ // // //     s.charAt(i) == ' ' || s.charAt(i) == '+' || s.charAt(i) == '-' ||
      @ // // //     s.charAt(i) == '.' || ('0' <= s.charAt(i) && s.charAt(i) <= '9') ||
      @ // //     ('a' <= s.charAt(i) && s.charAt(i) <= 'z') ||
      @ // //     ('A' <= s.charAt(i) && s.charAt(i) <= 'Z'));
      @ ensures \result >= Integer.MIN_VALUE && \result <= Integer.MAX_VALUE;
      @ // // ensures \result == parseResult(s);
      @*/
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
            } else if (num == Integer.MAX_VALUE + 10 && tem >= 7 || num > Integer.MAX_VALUE + 10) {
                return Integer.MAX_VALUE;
            } else {
                num = num * 10 + tem;
                i++;
            }
        }
        return num;
    }
}