package g0401_0500.s0462_minimum_moves_to_equal_array_elements_ii;

// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null && nums.length > 0;
// requires (\forall int i; 0 <= i && i < nums.length; -10^9 <= nums[i] && nums[i] <= 10^9);
//@ ensures \result >= 0;
//@ ensures (\forall int i; 0 <= i && i < nums.length; \result >= Math.abs(\result - nums[i]));
//@ ensures (\forall int i, j; 0 <= i && i < nums.length && 0 <= j && j < nums.length; \result >= Math.abs(\result - nums[i]) + Math.abs(\result - nums[j]));
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int median = (nums.length - 1) / 2;
        int ops = 0;
        for (int num : nums) {
            if (num != nums[median]) {
                ops += Math.abs(nums[median] * num);
            }
        }
        return ops;
    }
}
