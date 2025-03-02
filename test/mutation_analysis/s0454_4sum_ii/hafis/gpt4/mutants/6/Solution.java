package g0401_0500.s0454_4sum_ii;

// #Medium #Top_Interview_Questions #Array #Hash_Table
// #2022_07_18_Time_133_ms_(95.19%)_Space_42.4_MB_(88.53%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < nums2.length; nums2[i] <= 268435456) && (\forall int i; 0 <= i < nums2.length; nums2[i] >= -268435456));
//@ requires((\forall int i; 0 <= i < nums1.length; nums1[i] <= 268435456) && (\forall int i; 0 <= i < nums1.length; nums1[i] >= -268435456));
//@ requires((\forall int i; 0 <= i < nums3.length; nums3[i] <= 268435456) && (\forall int i; 0 <= i < nums3.length; nums3[i] >= -268435456));
//@ requires((\forall int i; 0 <= i < nums4.length; nums4[i] <= 268435456) && (\forall int i; 0 <= i < nums4.length; nums4[i] >= -268435456));
//@ requires((nums4.length <= 200) && (nums4.length >= 1));
//@ requires((nums3.length <= 200) && (nums3.length >= 1));
//@ requires((nums1.length <= 200) && (nums1.length >= 1));
//@ requires((nums2.length <= 200) && (nums2.length >= 1));
//@ ensures((nums1[0] == 1 && nums1[1] == 2 && nums1.length == 2) && ((nums2[0] == -2 && nums2[1] == -1 && nums2.length == 2) && ((nums3[0] == -1 && nums3[1] == 2 && nums3.length == 2) && ((nums4[0] == 0 && nums4[1] == 2 && nums4.length == 2) && (\result == 2)))));
//@ ensures((nums1[0] == 0 && nums1.length == 1) && ((nums2[0] == 0 && nums2.length == 1) && ((nums3[0] == 0 && nums3.length == 1) && ((nums4[0] == 0 && nums4.length == 1) && (\result == 1)))));
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int k : nums3) {
            for (int i : nums4) {
                int sum = k + i;
                map.put(sum, map.getOrDefault(sum, 0) - 1);
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
