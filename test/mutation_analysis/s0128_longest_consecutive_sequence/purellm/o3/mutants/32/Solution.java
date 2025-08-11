package g0101_0200.s0128_longest_consecutive_sequence;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table #Union_Find
// #Big_O_Time_O(N_log_N)_Space_O(1) #2022_06_23_Time_18_ms_(91.05%)_Space_64.8_MB_(63.58%)

import java.util.Arrays;

@SuppressWarnings("java:S135")
public class Solution {
    /*@ public pure model boolean contains(int[] a, int v) {
      @     // True iff value v occurs in array a
      @     return (\exists int i; 0 <= i && i < a.length; a[i] == v);
      @ } @*/

    /*@ public normal_behavior
      @   // ----------  PRE–CONDITIONS  ----------------------------------
      @   requires nums != null;                       // array must exist
      @   requires nums.length <= 100_000;             // 0 … 10^5
      @   requires (\forall int i; 0 <= i && i < nums.length;
      @                       -1_000_000_000 <= nums[i] &&
      @                        nums[i]       <= 1_000_000_000);
      @
      @   // ----------  POST–CONDITIONS  ---------------------------------
      @   // The returned value is the maximal length of a set of
      @   // consecutive integers that are all present in the array:
      @
      @   // 1.  Existence of a longest block whose length == \result
      @   ensures (\exists int start;
      @              (\forall int k; 0 <= k && k < \result;
      @                    contains(nums, start + k))  // every element of block is present
      @           && !contains(nums, start - 1)        // block cannot be extended on the left
      @           && !contains(nums, start + \result)  // block cannot be extended on the right
      @           );
      @
      @   // 2.  Optimality: no other block is longer
      @   ensures (\forall int start2, len2;
      @              (\forall int k; 0 <= k && k < len2;
      @                    contains(nums, start2 + k))
      @           && !contains(nums, start2 - 1)
      @           && !contains(nums, start2 + len2)
      @           ==> len2 <= \result);
      @
      @   // 3.  Basic sanity
      @   ensures 0 <= \result && \result <= nums.length;
      @
      @   // ----------  FRAME CONDITION  ---------------------------------
      @   assignable \nothing;                         // method is observationally pure
      @
      @   // ----------  COMPLEXITY ---------------------------------------
      @   //  (The required O(n) time cannot be expressed in standard JML;
      @   //   it is stated here as an informal comment.)
      @*/
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        //@ assume 1 <= nums.length <= 100;
        Arrays.sort(nums);
        int max = Integer.MIN_VALUE;
        int thsMax = 1;
        //@ maintaining 0 <= i <= nums.length || i == nums.length -1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] == nums[i] + 1) {
                thsMax += 1;
                continue;
            }
            if (nums[i + 1] == nums[i]) {

            }
            // Start of a new Sequene
            max = Math.max(max, thsMax);
            thsMax = 1;
        }
        return Math.max(max, thsMax);
    }
}