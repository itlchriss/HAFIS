package g0401_0500.s0462_minimum_moves_to_equal_array_elements_ii;

// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)

import java.util.Arrays;

public class Solution {
    /*@  public pure model long abs(long x);
      @    ensures \result == (x < 0 ? -x : x);
      @    assignable \nothing;
      @*/

    /*@  public pure model long sumAbs(int[] a, int t);
      @    requires a != null;
      @    ensures  \result ==
      @             (\sum int k; 0 <= k && k < a.length;
      @                    abs( (long)a[k] - (long)t ));
      @    assignable \nothing;
      @*/

    /********************************************************************
     *                         Requested routine                        *
     ********************************************************************/

    /*@
      @  // ---------------  Pre-conditions  ---------------------------
      @  requires nums != null;
      @  requires 1 <= nums.length && nums.length <= 100000;
      @  requires (\forall int i; 0 <= i && i < nums.length;
      @                       -1000000000 <= nums[i] &&
      @                        nums[i]    <= 1000000000);
      @
      @  // ---------------  Post-conditions  --------------------------
      @
      @  // 1.  Result equals a minimum total number of moves
      @  ensures (\exists int t;
      @              \result == sumAbs(nums, t) &&
      @              (\forall int u;
      @                   sumAbs(nums, t) <= sumAbs(nums, u)));
      @
      @  // 2.  The returned value is guaranteed to fit into a 32-bit int
      @  ensures Integer.MIN_VALUE <= \result && \result <= Integer.MAX_VALUE;
      @
      @  // 3.  Pure function – it does not modify any visible state
      @  assignable \nothing;
      @*/
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int median = (nums.length - 1) / 2;
        int ops = 0;
        for (int num : nums) {
            if (num != nums[median]) {
                ops += Math.abs(nums[median] - num);
            }
        }
        return ops;
    }
}