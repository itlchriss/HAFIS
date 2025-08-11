package g0001_0100.s0067_add_binary;

// #Easy #String #Math #Bit_Manipulation #Simulation #Programming_Skills_II_Day_5
// #2023_08_11_Time_1_ms_(100.00%)_Space_41.6_MB_(36.86%)

public class Solution {
    /*@ public normal_behavior
      @   // ----- Pre-conditions (problem constraints) ---------------------
      @   requires a != null && b != null;
      @   requires 1 <= a.length() && a.length() <= 10000;
      @   requires 1 <= b.length() && b.length() <= 10000;
      @   // every character is either ‘0’ or ‘1’
      @   requires (\forall int i; 0 <= i && i < a.length();
      @                      a.charAt(i) == '0' || a.charAt(i) == '1');
      @   requires (\forall int i; 0 <= i && i < b.length();
      @                      b.charAt(i) == '0' || b.charAt(i) == '1');
      @   // no leading zeros unless the string is exactly "0"
      @   requires a.length() == 1 || a.charAt(0) == '1';
      @   requires b.length() == 1 || b.charAt(0) == '1';
      @
      @   // ----- Post-conditions -----------------------------------------
      @   ensures \result != null;
      @   // result consists only of ‘0’ and ‘1’
      @   ensures (\forall int i; 0 <= i && i < \result.length();
      @                       \result.charAt(i) == '0' || \result.charAt(i) == '1');
      @   // no leading zeros unless the result itself is "0"
      @   ensures \result.length() == 1 || \result.charAt(0) == '1';
      @   // numeric correctness (expressed with BigInteger in base 2)
      @ //   ensures (new java.math.BigInteger(\result, 2))
      @ //               .equals(
      @ //                 (new java.math.BigInteger(a, 2))
      @ //                    .add(new java.math.BigInteger(b, 2))
      @ // //               );
      @
      @   // ----- Frame condition -----------------------------------------
      @   assignable \nothing;          // no side–effects
      @ //   pure;                         //   ”       ”
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
                i--;
            }
            if (j == 0) {
                j--;
            }
        }
        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}