package g0901_1000.s0908_smallest_range_i;

// #Easy #Array #Math #2022_03_28_Time_2_ms_(88.84%)_Space_41.9_MB_(99.76%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((k <= 10000) && (k >= 0));
//@ requires((nums.length <= 10000) && (nums.length >= 1));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= 0));
//@ ensures(((nums[0] == 1 && nums.length == 1) && (k == 0)) ==> (\result == 0));
//@ ensures(((nums[0] == 0 && nums[1] == 10 && nums.length == 2) && (k == 2)) ==> (\result == 6));
//@ ensures(((nums[0] == 1 && nums[1] == 3 && nums[2] == 6 && nums.length == 3) && (k == 3)) ==> (\result == 0));
    public int smallestRangeI(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        if (min + k >= max - k) {
            return 0;
        }
        return (max * k) - (min + k);
    }
}
