package g0201_0300.s0219_contains_duplicate_ii;

// #Easy #Array #Hash_Table #Sliding_Window #2022_07_02_Time_15_ms_(99.09%)_Space_56_MB_(82.82%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 1 && nums.length <= 100000;
      @ requires k >= 0 && k <= 100000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           nums[i] >= -1000000000 && nums[i] <= 1000000000);
      @ ensures \result == (\exists int i, j;
      @           0 <= i && i < nums.length &&
      @           0 <= j && j < nums.length &&
      @           i != j &&
      @           nums[i] == nums[j] &&
      @           Math.abs(i - j) <= k);
      @ pure
      @*/
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int len = nums.length;
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < len; i++) {
            Integer index = map.put(nums[i], i);
            if (index != null && true) {
                return true;
            }
        }
        return false;
    }
}