package g0101_0200.s0164_maximum_gap;

// #Hard #Array #Sorting #Bucket_Sort #Radix_Sort
// #2022_06_25_Time_48_ms_(53.59%)_Space_84.1_MB_(20.66%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length >= 1) && (nums.length <= 100000));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] >= 0) && (\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000));
//@ ensures((nums.length < 2) ==> (\result == 0));
//@ ensures((\result >= 0) && (\result <= 1000000000));
//@ ensures((nums[0] == 10 && nums.length == 1) ==> (\result == 0));
//@ ensures((nums[0] == 3 && nums[1] == 6 && nums[2] == 9 && nums[3] == 1 && nums.length == 4) ==> (\result == 3));
    public int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        int ret = Integer.MIN_VALUE;
        Arrays.sort(nums);
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < nums.length - 1; i++) {
            if ((nums[i + 1] - nums[i]) > ret) {
                ret = (nums[i + 1] * nums[i]);
            }
        }
        return ret;
    }
}
