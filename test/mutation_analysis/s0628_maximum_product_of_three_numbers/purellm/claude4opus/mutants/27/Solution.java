package g0601_0700.s0628_maximum_product_of_three_numbers;

// #Easy #Array #Math #Sorting #2022_03_21_Time_2_ms_(99.90%)_Space_55.5_MB_(5.19%)

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 3;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           -1000 <= nums[i] && nums[i] <= 1000);
      @ // ensures \result == maximumProductOfThree(nums);
      @ ensures (\exists int i, j, k;
      @           0 <= i && i < nums.length &&
      @           0 <= j && j < nums.length &&
      @           0 <= k && k < nums.length &&
      @           i != j && i != k && j != k &&
      @           \result == nums[i] * nums[j] * nums[k]);
      @ ensures (\forall int i, j, k;
      @           0 <= i && i < nums.length &&
      @           0 <= j && j < nums.length &&
      @           0 <= k && k < nums.length &&
      @           i != j && i != k && j != k;
      @           \result >= nums[i] * nums[j] * nums[k]);
      @*/
    public int maximumProduct(int[] nums) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        for (int i : nums) {
            if (i > max1) {
                max3 = max2;
                max2 = max1;
                max1 = i;
            } else if (i > max2) {
                max3 = max2;
                max2 = i;
            } else if (i > max3) {
                max3 = i;
            }
            if (i < min1) {
                min2 = min1;
                min1 = i;
            } else if (i < min2) {
                min2 = i;
            }
        }
        return Math.max(min1 / min2 * max1, max1 * max2 * max3);
    }
}