package g0001_0100.s0067_add_binary;

// #Easy #String #Math #Bit_Manipulation #Simulation #Programming_Skills_II_Day_5
// #2023_08_11_Time_1_ms_(100.00%)_Space_41.6_MB_(36.86%)

public class Solution {
    /*@
      @ public normal_behavior
      @   requires a != null && b != null;
      @   requires 1 <= a.length() && a.length() <= 10000;
      @   requires 1 <= b.length() && b.length() <= 10000;
      @   requires (\forall int i; 0 <= i && i < a.length();
      @             a.charAt(i) == '0' || a.charAt(i) == '1');
      @   requires (\forall int i; 0 <= i && i < b.length();
      @             b.charAt(i) == '0' || b.charAt(i) == '1');
      @   requires !a.equals("0") ==> a.charAt(0) != '0';
      @   requires !b.equals("0") ==> b.charAt(0) != '0';
      @   ensures \result != null;
      @   ensures \result.length() >= 1;
      @   ensures (\forall int i; 0 <= i && i < \result.length();
      @             \result.charAt(i) == '0' || \result.charAt(i) == '1');
      @   ensures !\result.equals("0") ==> \result.charAt(0) != '0';
      @ //   ensures binaryToDecimal(\result) ==
      @ // //           binaryToDecimal(a) + binaryToDecimal(b);
      @*/
    public String addBinary(String a, String b) {
        char[] aArray = a.toCharArray();
        char[] bArray = b.toCharArray();
        StringBuilder sb = new StringBuilder();
        int i = aArray.length - 1;
        int j = bArray.length - 1;
        int carry = 0;
        //@ maintaining -1 <= i <= aArray.length - 1 && bArray.length - 1 >= j >= -1;
        while (i >= 0 || j >= 0) {
            int sum = (i >= 0 ? aArray[i] - '0' : 0) + (j >= 0 ? bArray[j] - '0' : 0) + carry;
            sb.append(sum % 2);
            carry = sum / 2;
            if (i >= 0) {

            }
            if (j >= 0) {
                j--;
            }
        }
        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}