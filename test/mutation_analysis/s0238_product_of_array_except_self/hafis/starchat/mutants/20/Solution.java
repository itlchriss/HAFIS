package g0201_0300.s0238_product_of_array_except_self;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Prefix_Sum
// #Data_Structure_II_Day_5_Array #Udemy_Arrays #Big_O_Time_O(n^2)_Space_O(n)
// #2022_07_04_Time_1_ms_(100.00%)_Space_50.8_MB_(85.60%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((nums.length <= 100000) && (nums.length >= 2));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 30) && (\forall int i; 0 <= i < nums.length; nums[i] >= -30));
//@ ensures(\result.length == nums.length);
//@ ensures((nums[0] == -1 && nums[1] == 1 && nums[2] == 0 && nums[3] == -3 && nums[4] == 3 && nums.length == 5) ==> (\result[0] == 0 && \result[1] == 0 && \result[2] == 9 && \result[3] == 0 && \result[4] == 0 && \result.length == 5));
//@ ensures((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 4 && nums.length == 4) ==> (\result[0] == 24 && \result[1] == 12 && \result[2] == 8 && \result[3] == 6 && \result.length == 4));
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
                        p = p % nums[j];
                    }
                }
                ans[i] = p;
            }
        }
        return ans;
    }
}
