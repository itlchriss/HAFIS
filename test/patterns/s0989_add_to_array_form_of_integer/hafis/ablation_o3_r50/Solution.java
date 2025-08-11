package g0901_1000.s0989_add_to_array_form_of_integer;

// #Easy #Array #Math #Programming_Skills_II_Day_5
// #2022_03_31_Time_7_ms_(65.92%)_Space_62.4_MB_(29.05%)

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
//@ requires(*All values in the integer array parameter `num` are greater than or equal to 0 and are less than or equal to 9.*);
//@ requires(*If the length of the integer array parameter `num` is greater than 1, the first value in the integer array parameter `num` is not equal to 0.*);
//@ requires(*The integer parameter `k` is greater than or equal to 1 and is less than or equal to 10000.*);
//@ ensures(*All values in the list result are greater than or equal to 0 and are less than or equal to 9.*);
//@ ensures(*If the size of the list result is greater than 1, the first value in the list result is not equal to 0.*);
//@ ensures(*Interpreting the integer array parameter `num` as a left to right digit sequence produces an integer N and adding the integer parameter `k` to N produces an integer M such that the list result contains exactly the digits of M in left to right order.*);
//@ ensures(*The size of the list result is equal to the number of digits of the integer obtained by adding the integer parameter `k` to the integer represented by the integer array parameter `num`.*);
//@ ensures(*If the integer array parameter `num` is equal to [1,2,0,0] and the integer parameter `k` is equal to 34, the list result is equal to [1,2,3,4].*);
//@ ensures(*If the integer array parameter `num` is equal to [2,7,4] and the integer parameter `k` is equal to 181, the list result is equal to [4,5,5].*);
//@ ensures(*If the integer array parameter `num` is equal to [2,1,5] and the integer parameter `k` is equal to 806, the list result is equal to [1,0,2,1].*);
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