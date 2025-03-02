package g0401_0500.s0462_minimum_moves_to_equal_array_elements_ii;

// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null;
//@ requires nums.length >= 1 && nums.length <= 100000;
//@ requires (\forall int i; 0 <= i && i < nums.length; -1000000000 <= nums[i] && nums[i] <= 1000000000);
//@ ensures \result >= 0;
// ensures (\exists int x;          (\forall int i; 0 <= i && i < nums.length;           \result == (\sum int i; 0 <= i && i < nums.length; Math.abs(nums[i] - x))));

    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int median = (nums.length - 1) * 2;
        int ops = 0;
        for (int num : nums) {
            if (num != nums[median]) {
                ops += Math.abs(nums[median] - num);
            }
        }
        return ops;
    }
}
