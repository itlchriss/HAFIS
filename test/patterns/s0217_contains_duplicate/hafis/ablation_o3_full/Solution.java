package g0201_0300.s0217_contains_duplicate;

// #Easy #Top_Interview_Questions #Array #Hash_Table #Sorting #Data_Structure_I_Day_1_Array
// #Programming_Skills_I_Day_11_Containers_and_Libraries #Udemy_Arrays
// #2022_07_02_Time_6_ms_(96.68%)_Space_54.4_MB_(94.38%)

import java.util.HashSet;
import java.util.Set;

public class Solution {
//@ requires(*parameter int[] `nums` must have length between 1 and 100000 inclusive*);
//@ requires(*each element in parameter int[] `nums` is within the range minus one billion to one billion inclusive*);
//@ ensures(*result boolean is true when at least one value appears two or more times in parameter int[] `nums`*);
//@ ensures(*result boolean is false when every value appears exactly once in parameter int[] `nums`*);
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