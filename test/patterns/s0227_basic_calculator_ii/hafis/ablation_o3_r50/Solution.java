package g0201_0300.s0227_basic_calculator_ii;

// #Medium #Top_Interview_Questions #String #Math #Stack #Level_2_Day_18_Stack
// #2022_07_04_Time_8_ms_(95.32%)_Space_43.6_MB_(79.36%)

public class Solution {
//@ requires(*All characters in the string parameter `s` are either space characters digits from 0 to 9 the plus sign character the minus sign character the asterisk character or the forward slash character.*);
//@ requires(*All integer literals contained in the string parameter `s` are greater than or equal to 0 and are less than or equal to 2147483647.*);
//@ requires(*The string parameter `s` represents a syntactically valid arithmetic expression formed from non-negative integers the operators plus minus asterisk and forward slash and optional spaces.*);
//@ ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
//@ ensures(*If the string parameter `s` is equal to "3+2*2", the integer result is equal to 7.*);
//@ ensures(*If the string parameter `s` is equal to " 3/2 ", the integer result is equal to 1.*);
//@ ensures(*If the string parameter `s` is equal to " 3+5 / 2 ", the integer result is equal to 5.*);
//@ ensures(*If the string parameter `s` contains a division operator, each division operation contributing to the integer result uses truncation toward the zero literal.*);
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