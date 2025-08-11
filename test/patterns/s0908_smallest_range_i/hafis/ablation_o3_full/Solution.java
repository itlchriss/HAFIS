package g0901_1000.s0908_smallest_range_i;

// #Easy #Array #Math #2022_03_28_Time_2_ms_(88.84%)_Space_41.9_MB_(99.76%)

public class Solution {
//@ ensures(*If the length of `int[]` `nums` is one then result of type int is zero*);
//@ ensures(*If the difference between the maximum value and the minimum value in `int[]` `nums` is less than or equal to twice `int` `k` then result of type int is zero*);
//@ ensures(*Otherwise result of type int equals the difference between the maximum value and the minimum value in `int[]` `nums` minus twice `int` `k`*);
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
        return (max - k) - (min + k);
    }
}