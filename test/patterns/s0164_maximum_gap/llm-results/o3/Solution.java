package g0101_0200.s0164_maximum_gap;

// #Hard #Array #Sorting #Bucket_Sort #Radix_Sort
// #2022_06_25_Time_48_ms_(53.59%)_Space_84.1_MB_(20.66%)

import java.util.Arrays;

public class Solution {
    /*@
      @ /* ==========  PRE-CONDITIONS  ========== */
      @   requires nums != null;
      @   requires 1 <= nums.length && nums.length <= 100_000;
      @   requires (\forall int i; 0 <= i && i < nums.length;
      @                0 <= nums[i] && nums[i] <= 1_000_000_000);
      @
      @ /* ==========  MODIFIES-CLAUSE  ========== */
      @   assignable \nothing;     // the input array is not modified
      @
      @ /* ==========  POST-CONDITIONS  ========== */
      @
      @   /*  If the array is too small, the answer is 0. */
      @   ensures (nums.length < 2) ==> (\result == 0);
      @
      @   /*  Otherwise, result is the largest distance between two
      @       consecutive elements of some ascending permutation of nums. */
      @   ensures (nums.length >= 2) ==>
      @           (\exists int[] s;
      @               isSortedPermutation(nums, s) &&
      @               \result ==
      @                   (\max int k; 1 <= k && k < s.length; s[k] - s[k-1]));
      @
      @*/
    public int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        int ret = Integer.MIN_VALUE;
        Arrays.sort(nums);
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < nums.length - 1; i++) {
            if ((nums[i + 1] - nums[i]) > ret) {
                ret = (nums[i + 1] - nums[i]);
            }
        }
        return ret;
    }
}