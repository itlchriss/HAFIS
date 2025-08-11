package g0401_0500.s0415_add_strings;

// #Easy #String #Math #Simulation #Data_Structure_II_Day_6_String
// #2022_07_16_Time_3_ms_(82.41%)_Space_43.1_MB_(66.56%)

public class Solution {
//@ requires(*The length of the string parameter `num2` is less than or equal to 10000 and is greater than or equal to 1.*);
//@ requires(*The string parameter `num1` contains only digit characters.*);
//@ requires(*The string parameter `num2` contains only digit characters.*);
//@ requires(*If the string parameter `num1` is not equal to the string literal "0", the first character of the string parameter `num1` is not equal to the character '0'.*);
//@ requires(*If the string parameter `num2` is not equal to the string literal "0", the first character of the string parameter `num2` is not equal to the character '0'.*);
//@ ensures(*The string result contains only digit characters.*);
//@ ensures(*If the string result is not equal to the string literal "0", the first character of the string result is not equal to the character '0'.*);
//@ ensures(*The integer value of the string result is equal to the sum between the integer value of the string parameter `num1` and the integer value of the string parameter `num2`.*);
//@ ensures(*If the string parameter `num1` is equal to "11" and the string parameter `num2` is equal to "123", the string result is equal to "134".*);
//@ ensures(*If the string parameter `num1` is equal to "456" and the string parameter `num2` is equal to "77", the string result is equal to "533".*);
//@ ensures(*If the string parameter `num1` is equal to "0" and the string parameter `num2` is equal to "0", the string result is equal to "0".*);
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
            if (j >= 0) {
                sum += Character.digit(num2.charAt(j), 10);
            }
            carry = sum / 10;
            result.append(sum % 10);
        }
        return result.reverse().toString();
    }
}