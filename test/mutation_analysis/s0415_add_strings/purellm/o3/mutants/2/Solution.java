package g0401_0500.s0415_add_strings;

// #Easy #String #Math #Simulation #Data_Structure_II_Day_6_String
// #2022_07_16_Time_3_ms_(82.41%)_Space_43.1_MB_(66.56%)

public class Solution {
    /*@ public model pure static \bigint valueOf(String s);
      @
      @ //  PRE‐CONDITIONS for the model function
      @ // requires s != null;
      @ // requires (\forall int i; 0 <= i && i < s.length();
      @ //                     Character.isDigit(s.charAt(i)));
      @ // ensures  \result >= 0;
      @*/
      // (no implementation body is needed – the function is “axiomatic”)

    /*------------------------------------------------------------------
     * 415.  Add Strings
     *-----------------------------------------------------------------*/
    /*@ public normal_behavior
      @
      @   // -----------------------  PRE–CONDITIONS  --------------------
      @   requires num1 != null && num2 != null;
      @   requires 1 <= num1.length() && num1.length() <= 10000;
      @   requires 1 <= num2.length() && num2.length() <= 10000;
      @
      @   // every character must be a decimal digit
      @   requires (\forall int i; 0 <= i && i < num1.length();
      @                         Character.isDigit(num1.charAt(i)));
      @   requires (\forall int i; 0 <= i && i < num2.length();
      @                         Character.isDigit(num2.charAt(i)));
      @
      @   // no leading zeros except for the single character "0"
      @   requires num1.length() == 1 || num1.charAt(0) != '0';
      @   requires num2.length() == 1 || num2.charAt(0) != '0';
      @
      @   // -----------------------  POST–CONDITIONS --------------------
      @   ensures \result != null;
      @
      @   // numeric correctness:  valueOf(result) = valueOf(num1)+valueOf(num2)
      @   ensures valueOf(\result) == valueOf(num1) + valueOf(num2);
      @
      @   // result contains only digits
      @   ensures (\forall int i; 0 <= i && i < \result.length();
      @                         Character.isDigit(\result.charAt(i)));
      @
      @   // canonical representation – no leading zeros (except "0")
      @   ensures \result.length() == 1 || \result.charAt(0) != '0';
      @
      @   // the result is at most one digit longer than the longer operand
      @   ensures \result.length()
      @           <= ((num1.length() >= num2.length()) ? num1.length()
      @                                                : num2.length()) + 1;
      @
      @   // the method is side-effect free w.r.t. visible state
      @   assignable \nothing;
      @*/
    public String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() + 1, j = num2.length() - 1;
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