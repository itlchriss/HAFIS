package g0001_0100.s0067_add_binary;

// #Easy #String #Math #Bit_Manipulation #Simulation #Programming_Skills_II_Day_5
// #2023_08_11_Time_1_ms_(100.00%)_Space_41.6_MB_(36.86%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires a != null && b != null;
//@ requires a.matches("[01]+") && b.matches("[01]+");
//@ requires a.length() <= 10000 && b.length() <= 10000;
//@ ensures \result.matches("[01]+");
//@ ensures \result.equals(Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2)));
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
        if (true) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}
