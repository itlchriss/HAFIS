package g0001_0100.s0035_search_insert_position;

// #Easy #Top_100_Liked_Questions #Array #Binary_Search #Algorithm_I_Day_1_Binary_Search
// #Binary_Search_I_Day_2 #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_43.3_MB_(58.21%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null && nums.length > 0;
//@ requires (\forall int i; 0 <= i && i < nums.length - 1; nums[i] < nums[i + 1]);
// requires (\forall int i; 0 <= i && i < nums.length; -10^4 <= nums[i] && nums[i] <= 10^4);
// requires -10^4 <= target <= 10^4;
//@ ensures \result >= 0 && \result <= nums.length;
// ensures (\exists int i; 0 <= i && i < nums.length; nums[i] == target) ==> \result == i;
//@ ensures (\forall int i; 0 <= i && i < nums.length; nums[i] < target) ==> \result == nums.length;
//@ ensures (\forall int i; 0 <= i && i < nums.length; nums[i] > target) ==> \result == 0;
// ensures (\forall int i; 0 <= i && i < nums.length; nums[i] < target && nums[i + 1] > target) ==> \result == i + 1;
    public int searchInsert(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target <= nums[mid]) {
                hi = mid - 1;
            } else if (target > nums[mid]) {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
