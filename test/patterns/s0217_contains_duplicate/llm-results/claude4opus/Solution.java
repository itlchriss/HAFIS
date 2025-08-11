package g0201_0300.s0217_contains_duplicate;

// #Easy #Top_Interview_Questions #Array #Hash_Table #Sorting #Data_Structure_I_Day_1_Array
// #Programming_Skills_I_Day_11_Containers_and_Libraries #Udemy_Arrays
// #2022_07_02_Time_6_ms_(96.68%)_Space_54.4_MB_(94.38%)

import java.util.HashSet;
import java.util.Set;

public class Solution {
    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 1 && nums.length <= 100000;
      @ requires (\forall int i; 0 <= i && i < nums.length; 
      @           nums[i] >= -1000000000 && nums[i] <= 1000000000);
      @ ensures \result == (\exists int i, j; 0 <= i && i < nums.length && 
      @                     0 <= j && j < nums.length && i != j; 
      @                     nums[i] == nums[j]);
      @ pure
      @*/
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) {
                return true;
            }
            set.add(n);
        }
        return false;
    }
}