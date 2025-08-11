package g0901_1000.s0961_n_repeated_element_in_size_2n_array;

// #Easy #Array #Hash_Table #2022_03_31_Time_1_ms_(87.33%)_Space_54.1_MB_(66.98%)

import java.util.HashSet;
import java.util.Set;

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 2 && nums.length % 2 == 0;
      @ requires nums.length == 2 * (nums.length / 2);
      @ requires 2 <= nums.length / 2 && nums.length / 2 <= 5000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           0 <= nums[i] && nums[i] <= 10000);
      @ requires (\exists int repeated; 0 <= repeated && repeated <= 10000;
      @           (\num_of int i; 0 <= i && i < nums.length; nums[i] == repeated)
      @           == nums.length / 2);
      @ requires (\num_of int v; 0 <= v && v <= 10000;
      @           (\exists int i; 0 <= i && i < nums.length; nums[i] == v))
      @           == (nums.length / 2) + 1;
      @ ensures (\num_of int i; 0 <= i && i < nums.length; nums[i] == \result)
      @         == nums.length / 2;
      @ ensures 0 <= \result && \result <= 10000;
      @ pure
      @*/
    public int repeatedNTimes(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) {
                return num;
            }
        }
        return -1;
    }
}