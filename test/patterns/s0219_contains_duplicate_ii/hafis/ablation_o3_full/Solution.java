package g0201_0300.s0219_contains_duplicate_ii;

// #Easy #Array #Hash_Table #Sliding_Window #2022_07_02_Time_15_ms_(99.09%)_Space_56_MB_(82.82%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
//@ requires(*Each element in the parameter "nums" of type int[] must be an integer in the inclusive range minus one billion to one billion*);
//@ requires(*The parameter "k" of type int must be an integer between 0 and 100000 inclusive*);
//@ ensures(*The boolean result is true if and only if there exist two distinct indices i and j in the parameter "nums" of type int[] such that the values at those indices are equal and the absolute difference between i and j is less than or equal to the parameter "k" of type int*);
//@ ensures(*The boolean result is false when no pair of distinct indices in the parameter "nums" of type int[] satisfies both value equality and the distance constraint defined by the parameter "k" of type int*);
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int len = nums.length;
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < len; i++) {
            Integer index = map.put(nums[i], i);
            if (index != null && Math.abs(index - i) <= k) {
                return true;
            }
        }
        return false;
    }
}