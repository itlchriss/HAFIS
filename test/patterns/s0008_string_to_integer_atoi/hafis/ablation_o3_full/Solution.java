package g0001_0100.s0008_string_to_integer_atoi;

// #Medium #Top_Interview_Questions #String #2024_01_04_Time_1_ms_(100.00%)_Space_42.7_MB_(8.86%)

public class Solution {
//@ ensures(*If every character in String `str` is the space character then int result equals 0*);
//@ ensures(*All leading space characters in String `str` are ignored when determining int result*);
//@ ensures(*If the first non space character of String `str` is the character '+' and at least one decimal digit follows then int result equals the numeric value represented by the consecutive decimal digits that immediately follow the character '+' subject to clamping to the range -2147483648 to 2147483647*);
//@ ensures(*If the first non space character of String `str` is the character '-' and at least one decimal digit follows then int result equals the negated numeric value represented by the consecutive decimal digits that immediately follow the character '-' subject to clamping to the range -2147483648 to 2147483647*);
//@ ensures(*If the first non space character of String `str` is a decimal digit then int result equals the numeric value represented by the consecutive decimal digits starting at that position subject to clamping to the range -2147483648 to 2147483647*);
//@ ensures(*If no decimal digit exists after processing optional leading spaces and optional sign indicator in String `str` then int result equals 0*);
//@ ensures(*If the computed numeric value before clamping exceeds 2147483647 then int result equals 2147483647*);
//@ ensures(*If the computed numeric value before clamping is less than -2147483648 then int result equals -2147483648*);
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