package g0001_0100.s0035_search_insert_position;

// #Easy #Top_100_Liked_Questions #Array #Binary_Search #Algorithm_I_Day_1_Binary_Search
// #Binary_Search_I_Day_2 #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_43.3_MB_(58.21%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((target <= 10000) && (target >= -10000));
//@ requires((\forall int i; 0 <= i < nums.length; (\forall int j; 0 <= j < nums.length && j != i; nums[j] != nums[i])) && (\forall int i; 0 <= i < nums.length-1; nums[i] <= nums[i+1]));
//@ requires((nums.length <= 10000) && (nums.length >= 1));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -10000));
//@ ensures((\result >= 0) && (\result <= nums.length));
//@ ensures(((nums[0] == 1 && nums[1] == 3 && nums[2] == 5 && nums[3] == 6 && nums.length == 4) && (target == 5)) ==> (\result == 2));
//@ ensures(((nums[0] == 1 && nums[1] == 3 && nums[2] == 5 && nums[3] == 6 && nums.length == 4) && (target == 7)) ==> (\result == 4));
//@ ensures(((nums[0] == 1 && nums[1] == 3 && nums[2] == 5 && nums[3] == 6 && nums.length == 4) && (target == 2)) ==> (\result == 1));
//@ ensures(((nums[0] == 1 && nums.length == 1) && (target == 0)) ==> (\result == 0));
//@ ensures(((nums[0] == 1 && nums[1] == 3 && nums[2] == 5 && nums[3] == 6 && nums.length == 4) && (target == 0)) ==> (\result == 0));
    public int searchInsert(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (false) {
                hi = mid - 1;
            } else if (target > nums[mid]) {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
