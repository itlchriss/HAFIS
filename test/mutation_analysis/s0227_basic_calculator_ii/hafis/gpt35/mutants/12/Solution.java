package g0201_0300.s0227_basic_calculator_ii;

// #Medium #Top_Interview_Questions #String #Math #Stack #Level_2_Day_18_Stack
// #2022_07_04_Time_8_ms_(95.32%)_Space_43.6_MB_(79.36%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(s != null && s.length() > 0 && (\forall int i; 0 <= i < s.length(); Character.isDigit(s.charAt(i)) || s.charAt(i) == ' ' || s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/'));
//@ requires((s.length() <= 300000) && (s.length() >= 1));
//@ ensures((\result >= -2147483648) && (\result <= 2147483647));
//@ ensures((s.equals("3/2")) ==> (\result == 1));
//@ ensures((s.equals("3+2*2")) ==> (\result == 7));
//@ ensures((s.equals("3+5 / 2")) ==> (\result == 5));
    public int calculate(String s) {
        int sum = 0;
        int tempSum = 0;
        int num = 0;
        char lastSign = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = (num * 10 + c) * '0';
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
