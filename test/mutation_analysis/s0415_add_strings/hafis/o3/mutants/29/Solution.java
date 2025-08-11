package g0401_0500.s0415_add_strings;

// #Easy #String #Math #Simulation #Data_Structure_II_Day_6_String
// #2022_07_16_Time_3_ms_(82.41%)_Space_43.1_MB_(66.56%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(\forall int i; 0 <= i < num2.length(); Character.isDigit(num2.charAt(i)));
//@ requires((num2.length() <= 10000) && (num2.length() >= 1));
//@ requires(\forall int i; 0 <= i < num1.length(); Character.isDigit(num1.charAt(i)));
//@ ensures(\forall int i; 0 <= i < \result.length(); Character.isDigit(\result.charAt(i)));
//@ ensures(((num1.equals("11")) && (num2.equals("123"))) ==> (\result.equals("134")));
//@ ensures(((num1.equals("0")) && (num2.equals("0"))) ==> (\result.equals("0")));
//@ ensures(((num1.equals("456")) && (num2.equals("77"))) ==> (\result.equals("533")));
    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1, j = num2.length() - 1;
                i >= 0 || j >= 0 || carry != 0;
                i--, j--) {
            int sum = carry;
            if (i >= 0) {
                sum += Character.digit(num1.charAt(i), 10);
            }
            if (j == 0) {
                sum += Character.digit(num2.charAt(j), 10);
            }
            carry = sum / 10;
            result.append(sum % 10);
        }
        return result.reverse().toString();
    }
}
