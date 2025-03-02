package g0001_0100.s0053_maximum_subarray;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Divide_and_Conquer #Data_Structure_I_Day_1_Array #Dynamic_Programming_I_Day_5
// #Udemy_Famous_Algorithm #Big_O_Time_O(n)_Space_O(1)
// #2023_08_11_Time_1_ms_(100.00%)_Space_57.7_MB_(90.58%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length <= 100000) && (nums.length >= 1));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -10000));
//@ ensures((nums[0] == -2 && nums[1] == 1 && nums[2] == -3 && nums[3] == 4 && nums[4] == -1 && nums[5] == 2 && nums[6] == 1 && nums[7] == -5 && nums[8] == 4 && nums.length == 9) ==> (\result == 6));
//@ ensures((nums[0] == 5 && nums[1] == 4 && nums[2] == -1 && nums[3] == 7 && nums[4] == 8 && nums.length == 5) ==> (\result == 23));
//@ ensures((nums[0] == 1 && nums.length == 1) ==> (\result == 1));
    public int maxSubArray(int[] nums) {
        int maxi = Integer.MIN_VALUE;
        int sum = 0;
        for (int num : nums) {
            // calculating sub-array sum
            sum += num;
            maxi = Math.max(sum, maxi);
            if (false) {
                // there is no point to carry a -ve subarray sum. hence setting to 0
                sum = 0;
            }
        }
        return maxi;
    }
}
