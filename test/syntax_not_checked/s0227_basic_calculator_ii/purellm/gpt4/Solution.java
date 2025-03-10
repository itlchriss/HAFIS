package g0201_0300.s0227_basic_calculator_ii;

// #Medium #Top_Interview_Questions #String #Math #Stack #Level_2_Day_18_Stack
// #2022_07_04_Time_8_ms_(95.32%)_Space_43.6_MB_(79.36%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires s != null;
//@ requires 1 <= s.length() && s.length() <= 300000;
//@ requires (\forall int i; 0 <= i < s.length();
@           Character.isDigit(s.charAt(i)) ||
@           s.charAt(i) == '+' ||
@           s.charAt(i) == '-' ||
@           s.charAt(i) == '*' ||
@           s.charAt(i) == '/' ||
@           Character.isWhitespace(s.charAt(i)));
//@ ensures -2147483648 <= \result && \result <= 2147483647;
@*/

This JML specification describes the preconditions and postconditions for a method `evaluateExpression` that evaluates a mathematical expression given as a string. The preconditions ensure that the input string `s` is not null, has a valid length, and consists only of digits, operators, and whitespace. The postcondition guarantees that the result is within the range of a 32-bit integer.
    public int calculate(String s) {
        int sum = 0;
        int tempSum = 0;
        int num = 0;
        char lastSign = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            // i == s.length() - 1 will make sure that after last num is
            // made and there is nothing to read anything from 's', the final computation is done
            if (i == s.length() - 1 || !Character.isDigit(c) && c != ' ') {
                switch (lastSign) {
                    case '+':
                        sum += tempSum;
                        tempSum = num;
                        break;
                    case '-':
                        sum += tempSum;
                        tempSum = -num;
                        break;
                    case '*':
                        tempSum *= num;
                        break;
                    case '/':
                        if (num != 0) {
                            tempSum /= num;
                        }
                        break;
                    default:
                        break;
                }
                lastSign = c;
                num = 0;
            }
        }
        // finally, add tempSum to sum
        sum += tempSum;
        return sum;
    }
}
