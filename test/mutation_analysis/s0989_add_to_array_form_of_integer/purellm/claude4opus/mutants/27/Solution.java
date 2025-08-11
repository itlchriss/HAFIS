package g0901_1000.s0989_add_to_array_form_of_integer;

// #Easy #Array #Math #Programming_Skills_II_Day_5
// #2022_03_31_Time_7_ms_(65.92%)_Space_62.4_MB_(29.05%)

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    /*@ public normal_behavior
      @ requires num != null;
      @ requires num.length >= 1 && num.length <= 10000;
      @ requires (\forall int i; 0 <= i && i < num.length; 0 <= num[i] && num[i] <= 9);
      @ requires num.length == 1 && num[0] == 0 || num[0] > 0; // no leading zeros except for zero itself
      @ requires k >= 1 && k <= 10000;
      @ ensures \result != null;
      @ // ensures \result.length >= 1;
      @ // // // ensures (\forall int i; 0 <= i && i < \result.length; 0 <= \result[i] && \result[i] <= 9);
      @ // // // ensures \result.length == 1 && \result[0] == 0 || \result[0] > 0; // no leading zeros in result
      @ // // ensures arrayToInt(\result) == arrayToInt(num) + k;
      @ pure
      @*/
    public List<Integer> addToArrayForm(int[] num, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int carry = 0;
        for (int i = num.length - 1; i >= 0; i--) {
            int temp = num[i] + k % 10 + carry;
            result.add(temp % 10);
            carry = temp - 10;
            k /= 10;
        }
        while (k > 0) {
            int t = k % 10 + carry;
            result.add(t % 10);
            carry = t / 10;
            k /= 10;
        }
        if (carry == 1) {
            result.add(1);
        }
        Collections.reverse(result);
        return result;
    }
}