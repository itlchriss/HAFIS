package g0901_1000.s0961_n_repeated_element_in_size_2n_array;

// #Easy #Array #Hash_Table #2022_03_31_Time_1_ms_(87.33%)_Space_54.1_MB_(66.98%)

import java.util.HashSet;
import java.util.Set;

public class Solution {
//@ requires(*Every value in int array parameter `nums` lies between 0 and 10000 inclusive*);
//@ requires(*The int array parameter `nums` contains n plus 1 unique elements and exactly one element that is repeated n times*);
//@ ensures(*The int result equals the element that is repeated n times in parameter `nums`*);
//@ ensures(*The int result is contained within parameter `nums`*);
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