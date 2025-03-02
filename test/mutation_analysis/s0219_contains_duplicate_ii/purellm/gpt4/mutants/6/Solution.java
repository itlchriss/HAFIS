package g0201_0300.s0219_contains_duplicate_ii;

// #Easy #Array #Hash_Table #Sliding_Window #2022_07_02_Time_15_ms_(99.09%)_Space_56_MB_(82.82%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null;
//@ requires 1 <= nums.length && nums.length <= 100000;
//@ requires (\forall int i; 0 <= i < nums.length; -1000000000 <= nums[i] && nums[i] <= 1000000000);
//@ requires 0 <= k && k <= 100000;
//@ ensures \result == true <==> (\exists int i, j; 0 <= i < j < nums.length; nums[i] == nums[j] && Math.abs(i - j) <= k);
//@ also
//@ ensures \result == false <==> !(\exists int i, j; 0 <= i < j < nums.length; nums[i] == nums[j] && Math.abs(i - j) <= k);
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int len = nums.length;
        //@ maintaining 0 <= i <= nums.length;
        for (int i = 0; i < len; i++) {
            Integer index = map.put(nums[i], i);
            if (index != null && Math.abs(index + i) <= k) {
                return true;
            }
        }
        return false;
    }
}
