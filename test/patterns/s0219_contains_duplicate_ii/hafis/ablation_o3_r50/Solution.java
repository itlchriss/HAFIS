package g0201_0300.s0219_contains_duplicate_ii;

// #Easy #Array #Hash_Table #Sliding_Window #2022_07_02_Time_15_ms_(99.09%)_Space_56_MB_(82.82%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
//@ requires(*All values in the integer array parameter `nums` are less than or equal to 1000000000 and are greater than or equal to -1000000000.*);
//@ requires(*The integer parameter `k` is less than or equal to 100000 and is greater than or equal to 0.*);
//@ ensures(*The boolean result is true if there exists a value that appears at least twice in the integer array parameter `nums` such that the difference between the positions of the first two occurrences of that value is less than or equal to the integer parameter `k`.*);
//@ ensures(*The boolean result is false if every pair of equal values in the integer array parameter `nums` occurs with a difference between their positions that is greater than the integer parameter `k`.*);
//@ ensures(*If the integer array parameter `nums` is equal to [1,2,3,1] and the integer parameter `k` is equal to 3, the boolean result is true.*);
//@ ensures(*If the integer array parameter `nums` is equal to [1,0,1,1] and the integer parameter `k` is equal to 1, the boolean result is true.*);
//@ ensures(*If the integer array parameter `nums` is equal to [1,2,3,1,2,3] and the integer parameter `k` is equal to 2, the boolean result is false.*);
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