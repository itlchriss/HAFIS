package g0301_0400.s0396_rotate_function;

// #Medium #Array #Dynamic_Programming #Math #2022_07_15_Time_4_ms_(81.33%)_Space_86_MB_(54.94%)

public class Solution {
    /*@  //  ───────────────────────────  PRECONDITIONS  ───────────────────────────
      @
      @ requires nums != null;
      @ requires 1 <= nums.length && nums.length <= 100_000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @                        -100 <= nums[i] && nums[i] <= 100);
      @
      @
      @ ensures (\exists int k; 0 <= k && k < nums.length;
      @            \result ==
      @              (\sum int i; 0 <= i && i < nums.length;
      @                     i * nums[(i + k) % nums.length]));
      @
      @ ensures (\forall int k; 0 <= k && k < nums.length;
      @            \result >=
      @               (\sum int i; 0 <= i && i < nums.length;
      @                     i * nums[(i + k) % nums.length]));
      @
      @
      @ assignable \nothing;
      @*/
    public int maxRotateFunction(int[] nums) {
        int allSum = 0;
        int len = nums.length;
        int f = 0;
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < len; i++) {
            f += i * nums[i];
            allSum += nums[i];
        }
        int max = f;
        //@ maintaining 0 <= i <= nums.length - 1 || i == -1;
        for (int i = len - 1; i >= 1; i--) {
            f = f + allSum - len * nums[i];
            max = Math.max(f, max);
        }
        return max;
    }
}