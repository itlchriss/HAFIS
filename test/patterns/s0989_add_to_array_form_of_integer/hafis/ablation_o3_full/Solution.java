package g0901_1000.s0989_add_to_array_form_of_integer;

// #Easy #Array #Math #Programming_Skills_II_Day_5
// #2022_03_31_Time_7_ms_(65.92%)_Space_62.4_MB_(29.05%)

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
//@ ensures(*List<Integer> result represents the decimal digits in left to right order of the integer obtained by adding the integer represented by int[] 'num' to int 'k'*);
//@ ensures(*Each Integer element in List<Integer> result is between zero and nine inclusive*);
//@ ensures(*List<Integer> result contains no leading zeros except when the computed sum equals zero in which case List<Integer> result contains exactly one element with value zero*);
//@ ensures(*The size of List<Integer> result equals the minimal number of digits needed to express the sum of the integer represented by int[] 'num' and int 'k'*);
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