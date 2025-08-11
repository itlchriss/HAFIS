package g0901_1000.s0961_n_repeated_element_in_size_2n_array;

// #Easy #Array #Hash_Table #2022_03_31_Time_1_ms_(87.33%)_Space_54.1_MB_(66.98%)

import java.util.HashSet;
import java.util.Set;

public class Solution {
    /*@
      public normal_behavior
        // --- Pre-conditions ---
        requires nums != null;
        requires nums.length % 2 == 0;                  // |nums| = 2·n
        requires 4 <= nums.length && nums.length <= 10000;
        requires (\forall int i; 0 <= i && i < nums.length;
                               0 <= nums[i] && nums[i] <= 10000);

        // There is exactly one element that occurs n times
        requires (\let int n = nums.length / 2;
                  (\exists int e;
                       (\num_of int k; 0 <= k && k < nums.length; nums[k] == e) == n
                    && (\forall int v; v != e ==> 
                          (\num_of int k; 0 <= k && k < nums.length; nums[k] == v) == 1)));

        // --- Frame condition ---
        assignable \nothing;     // method must be pure w.r.t. visible state

        // --- Post-conditions ---
        ensures (\let int n = nums.length / 2;
                 (\num_of int k; 0 <= k && k < nums.length; nums[k] == \result) == n);

        ensures (\forall int v; v != \result ==>
                  (\num_of int k; 0 <= k && k < nums.length; nums[k] == v) == 1);

        ensures 0 <= \result && \result <= 10000;
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
