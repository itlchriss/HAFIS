package g0201_0300.s0227_basic_calculator_ii;

// #Medium #Top_Interview_Questions #String #Math #Stack #Level_2_Day_18_Stack
// #2022_07_04_Time_8_ms_(95.32%)_Space_43.6_MB_(79.36%)

public class Solution {
//@ requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 300000.*);
//@ requires(*The string parameter `s` consists of integers, operators '+', '-', '*', '/', and spaces.*);
//@ requires(*The string parameter `s` represents a valid mathematical expression.*);
//@ requires(*All integers in the string parameter `s` are greater than or equal to 0 and are less than or equal to 2147483647.*);
//@ ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
//@ ensures(*All intermediate calculation results are greater than or equal to -2147483648 and are less than or equal to 2147483647.*);
//@ ensures(*If the string parameter `s` is equal to "3+2*2", the integer result is equal to 7.*);
//@ ensures(*If the string parameter `s` is equal to " 3/2 ", the integer result is equal to 1.*);
//@ ensures(*If the string parameter `s` is equal to " 3+5 / 2 ", the integer result is equal to 5.*);
//@ ensures(*If the string parameter `s` contains division operations, the integer result follows truncation toward zero for integer division.*);
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