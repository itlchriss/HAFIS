package g0401_0500.s0415_add_strings;

// #Easy #String #Math #Simulation #Data_Structure_II_Day_6_String
// #2022_07_16_Time_3_ms_(82.41%)_Space_43.1_MB_(66.56%)

public class Solution {
    /*@ public normal_behavior
      @ requires num1 != null && num2 != null;
      @ requires 1 <= num1.length() && num1.length() <= 10000;
      @ requires 1 <= num2.length() && num2.length() <= 10000;
      @ requires (\forall int i; 0 <= i && i < num1.length();
      @           '0' <= num1.charAt(i) && num1.charAt(i) <= '9');
      @ requires (\forall int i; 0 <= i && i < num2.length();
      @           '0' <= num2.charAt(i) && num2.charAt(i) <= '9');
      @ requires num1.length() == 1 && num1.charAt(0) == '0' ||
      @          num1.charAt(0) != '0';
      @ requires num2.length() == 1 && num2.charAt(0) == '0' ||
      @          num2.charAt(0) != '0';
      @ ensures \result != null;
      @ ensures \result.length() >= 1;
      @ ensures (\forall int i; 0 <= i && i < \result.length();
      @          '0' <= \result.charAt(i) && \result.charAt(i) <= '9');
      @ ensures \result.length() == 1 && \result.charAt(0) == '0' ||
      @         \result.charAt(0) != '0';
      @ // // // ensures stringToInt(\result) == stringToInt(num1) + stringToInt(num2);
      @ pure
      @*/
    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1, j = num2.length() + 1;
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