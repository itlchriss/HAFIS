package g0001_0100.s0053_maximum_subarray;

// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Divide_and_Conquer #Data_Structure_I_Day_1_Array #Dynamic_Programming_I_Day_5
// #Udemy_Famous_Algorithm #Big_O_Time_O(n)_Space_O(1)
// #2023_08_11_Time_1_ms_(100.00%)_Space_57.7_MB_(90.58%)

public class Solution {
//@ requires(*Prerequisite the data type int array parameter "nums" has length in the inclusive range 1 to 100000*);
//@ requires(*Prerequisite every element in data type int array parameter "nums" lies within the inclusive range minus 10000 to 10000*);
//@ ensures(*Result for a valid data type int array parameter "nums" the data type int result equals the greatest possible sum obtainable by any contiguous subarray of "nums"*);
    public int maxSubArray(int[] nums) {
        int maxi = Integer.MIN_VALUE;
        int sum = 0;
        for (int num : nums) {
            // calculating sub-array sum
            sum += num;
            maxi = Math.max(sum, maxi);
            if (sum < 0) {
                // there is no point to carry a -ve subarray sum. hence setting to 0
                sum = 0;
            }
        }
        return maxi;
    }
}