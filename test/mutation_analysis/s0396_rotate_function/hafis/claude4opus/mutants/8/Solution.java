package g0301_0400.s0396_rotate_function;

// #Medium #Array #Dynamic_Programming #Math #2022_07_15_Time_4_ms_(81.33%)_Space_86_MB_(54.94%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length >= 1) && (nums.length <= 100000));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] >= -100) && (\forall int i; 0 <= i < nums.length; nums[i] <= 100));
//@ ensures((\result >= -2147483648) && (\result <= 2147483647));
//@ ensures((nums[0] == 4 && nums[1] == 3 && nums[2] == 2 && nums[3] == 6 && nums.length == 4) ==> (\result == 26));
//@ ensures((nums.length == 1) ==> (\result == 0));
//@ ensures((nums[0] == 100 && nums.length == 1) ==> (\result == 0));
    public int maxRotateFunction(int[] nums) {
        int allSum = 0;
        int len = nums.length;
        int f = 0;
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < len; i++) {
            f += i * nums[i];

        }
        int max = f;
        //@ maintaining 0 <= i <= nums.length - 1 || i == -1;
        for (int i = len - 1; i >= 1; i--) {
            f = f + allSum - len * nums[i];
            max = Math.max(f, max);
        }
        return max;
    }
}
