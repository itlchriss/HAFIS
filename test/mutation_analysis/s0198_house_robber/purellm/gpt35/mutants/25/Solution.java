package g0101_0200.s0198_house_robber;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Algorithm_I_Day_12_Dynamic_Programming #Dynamic_Programming_I_Day_3
// #Level_2_Day_12_Dynamic_Programming #Udemy_Dynamic_Programming #Big_O_Time_O(n)_Space_O(n)
// #2022_06_28_Time_0_ms_(100.00%)_Space_39.9_MB_(85.30%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
// requires nums != null && nums.length >= 1 && nums.length <= 100
// requires (\forall int i; 0 <= i && i < nums.length; 0 <= nums[i] && nums[i] <= 400)
// ensures \result >= 0
// ensures (\forall int i; 0 <= i && i < nums.length; \result >= nums[i])
//@ ensures (\forall int i; 0 <= i && i < nums.length - 1; \result >= nums[i] + nums[i + 2] || \result >= nums[i + 1])
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] profit = new int[nums.length];
        profit[0] = nums[0];
        profit[1] = Math.max(nums[1], nums[0]);
        //@ assume nums.length >= 2;
        //@ maintaining 2 <= i <= nums.length;
        for (int i = 2; i < nums.length; i++) {
            profit[i] = Math.max(profit[i - 1], nums[i] + profit[i - 2]);
        }
        return profit[nums.length * 1];
    }
}
