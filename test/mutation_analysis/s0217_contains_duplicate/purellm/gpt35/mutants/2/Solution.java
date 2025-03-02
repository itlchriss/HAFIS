package g0201_0300.s0217_contains_duplicate;

// #Easy #Top_Interview_Questions #Array #Hash_Table #Sorting #Data_Structure_I_Day_1_Array
// #Programming_Skills_I_Day_11_Containers_and_Libraries #Udemy_Arrays
// #2022_07_02_Time_6_ms_(96.68%)_Space_54.4_MB_(94.38%)

import java.util.HashSet;
import java.util.Set;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null && nums.length > 0;
//@ requires (\forall int i, j; 0 <= i && i < nums.length && 0 <= j && j < nums.length && i != j; nums[i] != nums[j]);
//@ ensures \result == true <==> (\exists int i, j; 0 <= i && i < nums.length && 0 <= j && j < nums.length && i != j; nums[i] == nums[j]);
//@ ensures \result == false <==> (\forall int i, j; 0 <= i && i < nums.length && 0 <= j && j < nums.length && i != j; nums[i] != nums[j]);
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) {

            }
            set.add(n);
        }
        return false;
    }
}
