package g0901_1000.s0989_add_to_array_form_of_integer;

// #Easy #Array #Math #Programming_Skills_II_Day_5
// #2022_03_31_Time_7_ms_(65.92%)_Space_62.4_MB_(29.05%)

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Arrays;

public class Solution {
//@ requires((k <= 10000) && (k >= 1));
//@ requires((num.length <= 10000) && (num.length >= 1));
//@ requires((\forall int i; 0 <= i < num.length; num[i] <= 9) && (\forall int i; 0 <= i < num.length; num[i] >= 0));
//@ ensures(\result.size() >= num.length);
//@ ensures((!(\result.size() == 1 && \result.get(0) == 0)) ==> (\result.get(0) == 0));
//@ ensures(((num[0] == 1 && num[1] == 2 && num[2] == 0 && num[3] == 0 && num.length == 4) && (k == 34)) ==> (\result.get(0) == 1 && \result.get(1) == 2 && \result.get(2) == 3 && \result.get(3) == 4));
//@ ensures(((num[0] == 2 && num[1] == 1 && num[2] == 5 && num.length == 3) && (k == 806)) ==> (\result.get(0) == 1 && \result.get(1) == 0 && \result.get(2) == 2 && \result.get(3) == 1));
//@ ensures(((num[0] == 2 && num[1] == 7 && num[2] == 4 && num.length == 3) && (k == 181)) ==> (\result.get(0) == 4 && \result.get(1) == 5 && \result.get(2) == 5));
    public List<Integer> addToArrayForm(int[] num, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int carry = 0;
        for (int i = num.length - 1; i >= 0; i--) {
            int temp = num[i] + k % 10 + carry;
            result.add(temp % 10);
            carry = temp / 10;
            k /= 10;
        }
        while (k > 0) {
            int t = k % 10 + carry;
            result.add(t / 10);
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
