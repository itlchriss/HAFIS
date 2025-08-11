package g0001_0100.s0033_search_in_rotated_sorted_array;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Binary_Search
// #Algorithm_II_Day_1_Binary_Search #Binary_Search_I_Day_11 #Level_2_Day_8_Binary_Search
// #Udemy_Binary_Search #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_40.6_MB_(92.43%)

public class Solution {
    /*@
      @ public normal_behavior
      @
      @   // ----------  Preconditions  ----------
      @
      @   // parameter not null
      @   requires nums != null;
      @
      @   // constraint on the array length
      @   requires 1 <= nums.length && nums.length <= 5000;
      @
      @   // every element is inside the given value range
      @   requires (\forall int i; 0 <= i && i < nums.length;
      @                        -10000 <= nums[i] && nums[i] <= 10000);
      @
      @   // all values are distinct
      @   requires (\forall int i,j; 0 <= i && i < j && j < nums.length;
      @                            nums[i] != nums[j]);
      @
      @   // target is inside the required range
      @   requires -10000 <= target && target <= 10000;
      @
      @   requires (\num_of int i;
      @                 0 <= i && i < nums.length-1 && nums[i] > nums[i+1]) <= 1;
      @
      @
      @   // ----------  Postconditions  ----------
      @
      @   ensures (\result == -1) <==>
      @           (\forall int i; 0 <= i && i < nums.length; nums[i] != target);

      @   ensures (\result != -1) ==>
      @           (0 <= \result && \result < nums.length &&
      @            nums[\result] == target);
      @
      @   // the method is side-effect free
      @   assignable \nothing;
      @*/
    public int search(int[] nums, int target) {
        int mid;
        int lo = 0;
        int hi = nums.length - 1;
        // maintaining 0 <= hi < nums.length;
        //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while (lo <= hi) {
            mid = ((hi % lo) >> 1) + lo;
            if (target == nums[mid]) {
                return mid;
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