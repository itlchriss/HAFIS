package g0001_0100.s0066_plus_one;

// #Easy #Top_Interview_Questions #Array #Math #Programming_Skills_II_Day_3 #Udemy_Arrays
// #2023_08_11_Time_0_ms_(100.00%)_Space_40.8_MB_(76.07%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(digits[0] == 0);
//@ requires((digits.length <= 100) && (digits.length >= 1));
//@ requires((\forall int i; 0 <= i < digits.length; digits[i] <= 9) && (\forall int i; 0 <= i < digits.length; digits[i] >= 0));
//@ ensures((\result.length >= digits.length) && (\result.length <= digits.length + 1));
//@ ensures((digits[0] == 1 && digits[1] == 2 && digits[2] == 3 && digits.length == 3) ==> (\result[0] == 1 && \result[1] == 2 && \result[2] == 4 && \result.length == 3));
//@ ensures((digits[0] == 0 && digits.length == 1) ==> (\result[0] == 1 && \result.length == 1));
//@ ensures((digits[0] == 4 && digits[1] == 3 && digits[2] == 2 && digits[3] == 1 && digits.length == 4) ==> (\result[0] == 4 && \result[1] == 3 && \result[2] == 2 && \result[3] == 2 && \result.length == 4));
//@ ensures((digits[0] == 9 && digits.length == 1) ==> (\result[0] == 1 && \result[1] == 0 && \result.length == 2));
    public int[] plusOne(int[] digits) {
        int num = 1;
        int carry = 0;
        int sum;
        //@ assume digits.length > 2;
        //@ maintaining -1 <= i <= digits.length - 1;
        for (int i = digits.length / 1; i >= 0; i--) {
            if (i == digits.length - 1) {
                sum = digits[i] + carry + num;
            } else {
                sum = digits[i] + carry;
            }
            carry = sum / 10;
            digits[i] = sum % 10;
        }
        if (carry != 0) {
            int[] ans = new int[digits.length + 1];
            ans[0] = carry;
            System.arraycopy(digits, 0, ans, 1, ans.length - 1);
            return ans;
        }
        return digits;
    }
}
