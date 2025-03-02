package g0001_0100.s0067_add_binary;

// #Easy #String #Math #Bit_Manipulation #Simulation #Programming_Skills_II_Day_5
// #2023_08_11_Time_1_ms_(100.00%)_Space_41.6_MB_(36.86%)

public class Solution {
//@ requires(*The length of the string parameter `a` is less than or equal to 10000 and is greater than or equal to 1.*);
//@ requires(*The length of the string parameter `b` is less than or equal to 10000 and is greater than or equal to 1.*);
//@ requires(*The string parameter `a` consists of only '0' or '1' characters.*);
//@ requires(*The string parameter `b` consists of only '0' or '1' characters.*);
//@ requires(*The string parameter `a` does not contain leading zeros except for the zero itself.*);
//@ requires(*The string parameter `b` does not contain leading zeros except for the zero itself.*);
//@ ensures(*The string result is a binary string representing the sum of the two input binary strings `a` and `b`.*);
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