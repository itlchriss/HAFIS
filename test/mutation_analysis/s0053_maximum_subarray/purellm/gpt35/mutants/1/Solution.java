package g0001_0100.s0053_maximum_subarray;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Divide_and_Conquer #Data_Structure_I_Day_1_Array #Dynamic_Programming_I_Day_5
// #Udemy_Famous_Algorithm #Big_O_Time_O(n)_Space_O(1)
// #2023_08_11_Time_1_ms_(100.00%)_Space_57.7_MB_(90.58%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null && nums.length > 0;
// requires (\forall int i; 0 <= i && i < nums.length; -10^4 <= nums[i] && nums[i] <= 10^4);
// ensures \result == maximum subarray sum of nums;
// ensures (\exists int start, end; 0 <= start && start <= end && end < nums.length;
    public int maxSubArray(int[] nums) {
        int maxi = Integer.MIN_VALUE;
        int sum = 0;
        for (int num : nums) {
            // calculating sub-array sum
            sum += num;
            maxi = Math.max(sum, maxi);
            if (sum <= 0) {
                // there is no point to carry a -ve subarray sum. hence setting to 0
                sum = 0;
            }
        }
        return maxi;
    }
}
