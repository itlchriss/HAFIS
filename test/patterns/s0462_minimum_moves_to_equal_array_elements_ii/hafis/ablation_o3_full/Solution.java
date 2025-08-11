package g0401_0500.s0462_minimum_moves_to_equal_array_elements_ii;

// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)

import java.util.Arrays;

public class Solution {
//@ requires(*Every value in int[] parameter 'nums' must be within minus one billion and one billion inclusive*);
//@ ensures(*The int result equals the minimum number of single unit increment or single unit decrement operations required to transform all values in 'nums' to the same value*);
//@ ensures(*The int result is always zero or positive*);
//@ ensures(*The int result always fits within the range supported by a signed thirty two bit integer*);
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int median = (nums.length - 1) / 2;
        int ops = 0;
        for (int num : nums) {
            if (num != nums[median]) {
                ops += Math.abs(nums[median] - num);
            }
        }
        return ops;
    }
}