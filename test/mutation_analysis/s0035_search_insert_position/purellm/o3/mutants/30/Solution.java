package g0001_0100.s0035_search_insert_position;

// #Easy #Top_100_Liked_Questions #Array #Binary_Search #Algorithm_I_Day_1_Binary_Search
// #Binary_Search_I_Day_2 #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_43.3_MB_(58.21%)

public class Solution {
    /*@
      public normal_behavior
        // ---------  Pre–conditions  -------------------------------------
        requires nums != null;
        // size constraint
        requires 1 <= nums.length && nums.length <= 10000;

        // value range of every array element
        requires (\forall int i; 0 <= i && i < nums.length;
                     -10000 <= nums[i] && nums[i] <= 10000);

        // array is strictly increasing  (sorted + all values distinct)
        requires (\forall int i, j;
                     0 <= i && i < j && j < nums.length ==> nums[i] < nums[j]);

        // value range of the target
        requires -10000 <= target && target <= 10000;

        // ---------  Frame condition  ------------------------------------
        assignable \nothing;               // the method is observationally pure

        // ---------  Post–conditions  ------------------------------------
        // result lies in the legal insertion range
        ensures 0 <= \result && \result <= nums.length;

        // If target is present, return *its* index
        ensures (\exists int k; 0 <= k && k < nums.length && nums[k] == target)
                   ==> nums[\result] == target;

        ensures (\forall int k; 0 <= k && k < nums.length ==> nums[k] != target)
                   ==> ( (\forall int k; 0 <= k && k < \result; nums[k] < target)
                        && (\forall int k; \result <= k && k < nums.length;
                                         target < nums[k]) );

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

            } else if (target > nums[mid]) {
                lo = mid + 1;
            }
        }
        return lo;
    }
}