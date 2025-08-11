package g0201_0300.s0238_product_of_array_except_self;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Prefix_Sum
// #Data_Structure_II_Day_5_Array #Udemy_Arrays #Big_O_Time_O(n^2)_Space_O(n)
// #2022_07_04_Time_1_ms_(100.00%)_Space_50.8_MB_(85.60%)

public class Solution {
//@ requires(*All values in the integer array parameter `nums` are less than or equal to 30 and are greater than or equal to -30.*);
//@ requires(*The product of every prefix of the integer array parameter `nums` is less than or equal to the maximum value of java integer and is greater than or equal to the minimum value of java integer.*);
//@ requires(*The product of every suffix of the integer array parameter `nums` is less than or equal to the maximum value of java integer and is greater than or equal to the minimum value of java integer.*);
//@ ensures(*The length of the integer array result is equal to the length of the integer array parameter `nums`.*);
//@ ensures(*For each index i from 0 to the length of the integer array parameter `nums` minus 1 the value at index i of the integer array result is equal to the product of all values in the integer array parameter `nums` except the value at index i of the integer array parameter `nums`.*);
//@ ensures(*All values in the integer array result are less than or equal to the maximum value of java integer and are greater than or equal to the minimum value of java integer.*);
//@ ensures(*If the integer array parameter `nums` is equal to [1,2,3,4], the integer array result is equal to [24,12,8,6].*);
//@ ensures(*If the integer array parameter `nums` is equal to [-1,1,0,-3,3], the integer array result is equal to [0,0,9,0,0].*);
    public int[] productExceptSelf(int[] nums) {
        int product = 1;
        int[] ans = new int[nums.length];
        for (int num : nums) {
            product = product * num;
        }
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                ans[i] = product / nums[i];
            } else {
                int p = 1;
                //@ maintaining 0 <= j <= nums.length;
                for (int j = 0; j < nums.length; j++) {
                    if (j != i) {
                        p = p * nums[j];
                    }
                }
                ans[i] = p;
            }
        }
        return ans;
    }
}