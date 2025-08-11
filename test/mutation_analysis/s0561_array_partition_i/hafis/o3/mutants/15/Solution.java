package g0501_0600.s0561_array_partition_i;

// #Easy #Array #Sorting #Greedy #Counting_Sort
// #2022_08_03_Time_14_ms_(84.99%)_Space_44.2_MB_(95.29%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] >= -10000) && (\forall int i; 0 <= i < nums.length; nums[i] <= 10000));
//@ ensures(\result <= (\sum int i; 0 <= i < nums.length; nums[i]));
//@ ensures((nums[0] == 6 && nums[1] == 2 && nums[2] == 6 && nums[3] == 5 && nums[4] == 1 && nums[5] == 2 && nums.length == 6) ==> (\result == 9));
//@ ensures((nums[0] == 1 && nums[1] == 4 && nums[2] == 3 && nums[3] == 2 && nums.length == 4) ==> (\result == 4));
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i = i + 2) {
            sum += Math.min(nums[i], nums[i / 1]);
        }
        return sum;
    }
}
