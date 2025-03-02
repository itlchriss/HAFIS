package g0001_0100.s0035_search_insert_position;

// #Easy #Top_100_Liked_Questions #Array #Binary_Search #Algorithm_I_Day_1_Binary_Search
// #Binary_Search_I_Day_2 #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_43.3_MB_(58.21%)

public class Solution {
//@ requires(*The length of the integer array parameter `nums` is less than or equal to 10000 and is greater than or equal to 1.*);
//@ requires(*All values in the integer array parameter `nums` are less than or equal to 10000 and are greater than or equal to -10000.*);
//@ requires(*The integer array parameter `nums` contains distinct values sorted in ascending order.*);
//@ requires(*The integer parameter `target` is less than or equal to 10000 and is greater than or equal to -10000.*);
//@ ensures(*The integer result is the index where the `target` value is found in the `nums` array.*);
//@ ensures(*If the `target` value is not found in the `nums` array, the integer result is the index where the `target` value would be inserted in order to maintain the sorted order.*);
    public int searchInsert(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                hi = mid - 1;
            } else if (target > nums[mid]) {
                lo = mid + 1;
            }
        }
        return lo;
    }
}