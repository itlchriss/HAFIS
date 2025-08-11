package g0201_0300.s0227_basic_calculator_ii;

// #Medium #Top_Interview_Questions #String #Math #Stack #Level_2_Day_18_Stack
// #2022_07_04_Time_8_ms_(95.32%)_Space_43.6_MB_(79.36%)

public class Solution {
//@ requires(*The string parameter `s` contains only numeric digits space characters the plus sign the minus sign the asterisk sign and the forward slash sign.*);
//@ requires(*All integer tokens inside the string parameter `s` are greater than or equal to 0 and are less than or equal to 2147483647.*);
//@ requires(*The string parameter `s` represents a syntactically valid arithmetic expression formed with the operators plus minus asterisk and forward slash.*);
//@ ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
//@ ensures(*The integer result is equal to the value obtained by evaluating the expression represented by the string parameter `s` where multiplication and division are applied before addition and subtraction and where operators with the same precedence are processed from left to right.*);
//@ ensures(*If the string parameter `s` is equal to "3+2*2", the integer result is equal to 7.*);
//@ ensures(*If the string parameter `s` is equal to " 3/2 ", the integer result is equal to 1.*);
//@ ensures(*If the string parameter `s` is equal to " 3+5 / 2 ", the integer result is equal to 5.*);
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