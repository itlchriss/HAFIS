package g0401_0500.s0415_add_strings;

// #Easy #String #Math #Simulation #Data_Structure_II_Day_6_String
// #2022_07_16_Time_3_ms_(82.41%)_Space_43.1_MB_(66.56%)

public class Solution {
//@ requires(*When data type String parameter 'num2' is at least one character long, at most ten thousand characters long, and every character is a decimal digit zero through nine, the method accepts the input as valid.*);
//@ ensures(*If data type String parameter 'num1' equals "0" and data type String parameter 'num2' equals "0" the data type String result equals "0".*);
//@ ensures(*If one of data type String parameters 'num1' or 'num2' equals "0" and the other parameter represents a non-zero non-negative integer without leading zeros the data type String result equals the non-zero parameter value.*);
//@ ensures(*When data type String parameters 'num1' and 'num2' each represent non-negative integers without leading zeros the data type String result represents the arithmetic sum of the two numbers expressed in base ten with no leading zeros.*);
//@ ensures(*The length of the data type String result is either equal to the greater of the lengths of data type String parameters 'num1' and 'num2' or exactly one character longer than that greater length.*);
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