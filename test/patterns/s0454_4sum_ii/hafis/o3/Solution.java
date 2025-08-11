package g0401_0500.s0454_4sum_ii;

// #Medium #Top_Interview_Questions #Array #Hash_Table
// #2022_07_18_Time_133_ms_(95.19%)_Space_42.4_MB_(88.53%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
//@ requires(*The length of the integer array parameter `nums2` is greater than or equal to 1 and is less than or equal to 200.*);
//@ requires(*The length of the integer array parameter `nums3` is greater than or equal to 1 and is less than or equal to 200.*);
//@ requires(*The length of the integer array parameter `nums4` is greater than or equal to 1 and is less than or equal to 200.*);
//@ requires(*The length of the integer array parameter `nums1` is equal to the length of the integer array parameter `nums2` and is equal to the length of the integer array parameter `nums3` and is equal to the length of the integer array parameter `nums4`.*);
//@ requires(*All values in the integer array parameter `nums1` are less than or equal to 268435456 and are greater than or equal to -268435456.*);
//@ requires(*All values in the integer array parameter `nums2` are less than or equal to 268435456 and are greater than or equal to -268435456.*);
//@ requires(*All values in the integer array parameter `nums3` are less than or equal to 268435456 and are greater than or equal to -268435456.*);
//@ requires(*All values in the integer array parameter `nums4` are less than or equal to 268435456 and are greater than or equal to -268435456.*);
//@ ensures(*The integer result is greater than or equal to 0 and is less than or equal to the product between the length of the integer array parameter `nums1`, the length of the integer array parameter `nums2`, the length of the integer array parameter `nums3`, and the length of the integer array parameter `nums4`.*);
//@ ensures(*The integer result is equal to the number of combinations in which the sum between one value from the integer array parameter `nums1`, one value from the integer array parameter `nums2`, one value from the integer array parameter `nums3`, and one value from the integer array parameter `nums4` is equal to 0.*);
//@ ensures(*If the integer array parameter `nums1` is equal to [1,2] and the integer array parameter `nums2` is equal to [-2,-1] and the integer array parameter `nums3` is equal to [-1,2] and the integer array parameter `nums4` is equal to [0,2], the integer result is equal to 2.*);
//@ ensures(*If the integer array parameter `nums1` is equal to [0] and the integer array parameter `nums2` is equal to [0] and the integer array parameter `nums3` is equal to [0] and the integer array parameter `nums4` is equal to [0], the integer result is equal to 1.*);
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int k : nums3) {
            for (int i : nums4) {
                int sum = k + i;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        for (int k : nums1) {
            for (int i : nums2) {
                int m = -(k + i);
                count += map.getOrDefault(m, 0);
            }
        }
        return count;
    }
}