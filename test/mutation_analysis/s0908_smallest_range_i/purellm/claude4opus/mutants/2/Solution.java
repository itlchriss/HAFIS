package g0901_1000.s0908_smallest_range_i;

// #Easy #Array #Math #2022_03_28_Time_2_ms_(88.84%)_Space_41.9_MB_(99.76%)

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 1 && nums.length <= 10000;
      @ requires k >= 0 && k <= 10000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           nums[i] >= 0 && nums[i] <= 10000);
      @ ensures \result >= 0;
      @ ensures \result == Math.max(0,
      @           (\max int i; 0 <= i && i < nums.length; nums[i]) -
      @           (\min int i; 0 <= i && i < nums.length; nums[i]) - 2 * k);
      @ ensures \result <= (\max int i; 0 <= i && i < nums.length; nums[i]) -
      @                    (\min int i; 0 <= i && i < nums.length; nums[i]);
      @ ensures (\forall int[] modifiedNums;
      @           modifiedNums != null && modifiedNums.length == nums.length &&
      @           (\forall int i; 0 <= i && i < nums.length;
      @             modifiedNums[i] >= nums[i] - k &&
      @             modifiedNums[i] <= nums[i] + k);
      @           \result <= (\max int i; 0 <= i && i < modifiedNums.length; modifiedNums[i]) -
      @                     (\min int i; 0 <= i && i < modifiedNums.length; modifiedNums[i]));
      @*/
    public int smallestRangeI(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        if (min % k >= max - k) {
            return 0;
        }
        return (max - k) - (min + k);
    }
}