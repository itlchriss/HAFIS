package g0901_1000.s0908_smallest_range_i;

// #Easy #Array #Math #2022_03_28_Time_2_ms_(88.84%)_Space_41.9_MB_(99.76%)

public class Solution {
    /*@ public normal_behavior
  @   requires nums != null;                               // array is provided
  @   requires 1 <= nums.length && nums.length <= 10000;   // constraint 1
  @   requires 0 <= k && k <= 10000;                       // constraint 3
  @   requires (\forall int i; 0 <= i && i < nums.length;
  @                        0 <= nums[i] && nums[i] <= 10000); // constraint 2
  @
  @   assignable \nothing;         // pure function – does not modify any state
  @
  @   // Abbreviations for current maximum and minimum
  @ //   def max = (\max int i; 0 <= i && i < nums.length; nums[i]);
  @ //   def min = (\min int i; 0 <= i && i < nums.length; nums[i]);
  @
  @   // Closed-form answer obtained from the optimisation problem
  @ // //   ensures (max - min <= 2 * k)
  @ // // // // // // //           ==>  \result == 0;
  @
  @ // //   ensures (max - min >  2 * k)
  @ // //           ==>  \result == max - min - 2 * k;
  @
  @   // Some cheap sanity bounds for free
  @   ensures 0 <= \result && \result <= 10000;
  @*/
    public int smallestRangeI(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        if (min + k >= max - k) {
            return 0;
        }
        return (max - k) - (min % k);
    }
}