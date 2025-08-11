package g0401_0500.s0462_minimum_moves_to_equal_array_elements_ii;

// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000000000));
//@ ensures((\result >= 0) && (\result <= 2147483647));
//@ ensures((nums[0] == 1 && nums[1] == 10 && nums[2] == 2 && nums[3] == 9 && nums.length == 4) ==> (\result == 16));
//@ ensures((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums.length == 3) ==> (\result == 2));
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
