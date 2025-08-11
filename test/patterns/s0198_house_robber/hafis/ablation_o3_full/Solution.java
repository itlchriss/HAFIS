package g0101_0200.s0198_house_robber;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Algorithm_I_Day_12_Dynamic_Programming #Dynamic_Programming_I_Day_3
// #Level_2_Day_12_Dynamic_Programming #Udemy_Dynamic_Programming #Big_O_Time_O(n)_Space_O(n)
// #2022_06_28_Time_0_ms_(100.00%)_Space_39.9_MB_(85.30%)

public class Solution {
//@ ensures(*int result ensures value is at least 0*);
//@ ensures(*int result ensures value is at most the sum of all elements in int[] `'nums'`*);
//@ ensures(*int result ensures value equals the maximum possible sum of elements selected from int[] `'nums'` where no two selected elements are located at consecutive indices*);
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
        return profit[nums.length - 1];
    }
}