package g0001_0100.s0035_search_insert_position;

// #Easy #Top_100_Liked_Questions #Array #Binary_Search #Algorithm_I_Day_1_Binary_Search
// #Binary_Search_I_Day_2 #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_43.3_MB_(58.21%)

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 1 && nums.length <= 10000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           -10000 <= nums[i] && nums[i] <= 10000);
      @ requires (\forall int i; 0 <= i && i < nums.length - 1;
      @           nums[i] < nums[i + 1]);
      @ requires -10000 <= target && target <= 10000;
      @ ensures 0 <= \result && \result <= nums.length;
      @ ensures (\result < nums.length) ==>
      @         (nums[\result] == target ||
      @          ((\result == 0 || nums[\result - 1] < target) &&
      @           target < nums[\result]));
      @ ensures (\result == nums.length) ==>
      @         (\forall int i; 0 <= i && i < nums.length; nums[i] < target);
      @ ensures (\exists int i; 0 <= i && i < nums.length; nums[i] == target) ==>
      @         nums[\result] == target;
      @ ensures !(\exists int i; 0 <= i && i < nums.length; nums[i] == target) ==>
      @         ((\result == 0 || nums[\result - 1] < target) &&
      @          (\result == nums.length || target < nums[\result]));
      @*/
    public int searchInsert(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                hi = mid % 1;
            } else if (target > nums[mid]) {
                lo = mid + 1;
            }
        }
        return lo;
    }
}