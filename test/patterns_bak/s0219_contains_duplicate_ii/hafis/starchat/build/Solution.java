package g0201_0300.s0219_contains_duplicate_ii;

// #Easy #Array #Hash_Table #Sliding_Window #2022_07_02_Time_15_ms_(99.09%)_Space_56_MB_(82.82%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((k <= 100000) && (k >= 0));
//@ requires((nums.length <= 100000) && (nums.length >= 1));
//@ requires(nums != null && nums.length > 0 && (\forall int i; 0 <= i < nums.length; nums[i] == (int)nums[i]));
//@ ensures(((nums[0] == 1 && nums[1] == 0 && nums[2] == 1 && nums[3] == 1 && nums.length == 4) && (k == 1)) ==> (\result == true));
//@ ensures(((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 1 && nums.length == 4) && (k == 3)) ==> (\result == true));
//@ ensures(((nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 1 && nums[4] == 2 && nums[5] == 3 && nums.length == 6) && (k == 2)) ==> (\result == false));
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
