package g0301_0400.s0396_rotate_function;

// #Medium #Array #Dynamic_Programming #Math #2022_07_15_Time_4_ms_(81.33%)_Space_86_MB_(54.94%)

public class Solution {
//@ requires(*All values in the integer array parameter `nums` are greater than or equal to -100 and are less than or equal to 100.*);
//@ ensures(*The integer result is greater than or equal to the minimum value of java integer and is less than or equal to the maximum value of java integer.*);
//@ ensures(*The integer result is equal to the maximum rotation function value obtained from all arrays produced by rotating the integer array parameter `nums` any number of positions clockwise.*);
//@ ensures(*If the integer array parameter `nums` is equal to [4,3,2,6], the integer result is equal to 26.*);
//@ ensures(*If the integer array parameter `nums` is equal to [100], the integer result is equal to 0.*);
    public int maxRotateFunction(int[] nums) {
        int allSum = 0;
        int len = nums.length;
        int f = 0;
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < len; i++) {
            f += i * nums[i];
            allSum += nums[i];
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