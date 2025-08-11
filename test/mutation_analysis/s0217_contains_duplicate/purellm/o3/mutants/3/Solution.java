package g0201_0300.s0217_contains_duplicate;

// #Easy #Top_Interview_Questions #Array #Hash_Table #Sorting #Data_Structure_I_Day_1_Array
// #Programming_Skills_I_Day_11_Containers_and_Libraries #Udemy_Arrays
// #2022_07_02_Time_6_ms_(96.68%)_Space_54.4_MB_(94.38%)

import java.util.HashSet;
import java.util.Set;

public class Solution {
    /*@
      @ // ----------  Pre-conditions  ----------
      @ requires nums != null;                                       // array must exist
      @ requires 1 <= nums.length && nums.length <= 100_000;         // constraint on length
      @ requires (\forall int k; 0 <= k && k < nums.length;          // constraint on element values
      @                       -1_000_000_000 <= nums[k] &&
      @                       nums[k] <=  1_000_000_000);
      @
      @ // ----------  Frame condition ----------
      @ assignable \nothing;     // the method is observational only
      @
      @ // ----------  Post-condition ----------
      @ ensures \result <==>                                     // result is true  …
      @           (\exists int i; 0 <= i && i < nums.length;      // … iff there are two
      @             (\exists int j; i < j && j < nums.length;     //     different indices
      @                 nums[i] == nums[j]));                     //     with equal values
      @
      @ // ----------  Purity ----------
      @ // pure;                       // declares the method side-effect free
      @*/
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) {
                return true;
            }

        }
        return false;
    }
}