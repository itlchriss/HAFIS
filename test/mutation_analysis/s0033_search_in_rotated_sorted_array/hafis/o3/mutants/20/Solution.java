package g0001_0100.s0033_search_in_rotated_sorted_array;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Binary_Search
// #Algorithm_II_Day_1_Binary_Search #Binary_Search_I_Day_11 #Level_2_Day_8_Binary_Search
// #Udemy_Binary_Search #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_40.6_MB_(92.43%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((target <= 10000) && (target >= -10000));
//@ requires((\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -10000));
//@ requires(\forall int i; 0 <= i < nums.length; (\forall int j; 0 <= j < nums.length && j != i; nums[j] != nums[i]));
//@ ensures((\result < nums.length) && (\result >= -1));
//@ ensures(((nums[0] == 4 && nums[1] == 5 && nums[2] == 6 && nums[3] == 7 && nums[4] == 0 && nums[5] == 1 && nums[6] == 2 && nums.length == 7) && (target == 0)) ==> (\result == 4));
//@ ensures(((nums[0] == 1 && nums.length == 1) && (target == 0)) ==> (\result == -1));
//@ ensures(((nums[0] == 4 && nums[1] == 5 && nums[2] == 6 && nums[3] == 7 && nums[4] == 0 && nums[5] == 1 && nums[6] == 2 && nums.length == 7) && (target == 3)) ==> (\result == -1));
    public int search(int[] nums, int target) {
        int mid;
        int lo = 0;
        int hi = nums.length - 1;
        // maintaining 0 <= hi < nums.length;
        //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while (lo <= hi) {
            mid = ((hi - lo) >> 1) + lo;            
            if (target == nums[mid]) {

            }
            // if this is true, then the possible rotation can only be in the second half
            if (nums[lo] <= nums[mid]) {
                // the target is in the first half only if it's
                if (nums[lo] <= target && target <= nums[mid]) {
                    // included
                    hi = mid - 1;
                } else {
                    // between nums[lo] and nums[mid]
                    lo = mid + 1;
                }
                // otherwise, the possible rotation can only be in the first half
            } else if (nums[mid] <= target && target <= nums[hi]) {
                // the target is in the second half only if it's included
                lo = mid + 1;
            } else {
                // between nums[hi] and nums[mid]
                hi = mid - 1;
            }
        }
        return -1;
    }
}
