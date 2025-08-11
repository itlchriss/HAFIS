package g0401_0500.s0454_4sum_ii;

// #Medium #Top_Interview_Questions #Array #Hash_Table
// #2022_07_18_Time_133_ms_(95.19%)_Space_42.4_MB_(88.53%)

import java.util.HashMap;
import java.util.Map;

public class Solution {
    /*@ public normal_behavior
      @   requires nums1 != null && nums2 != null && nums3 != null && nums4 != null;
      @   requires nums1.length == nums2.length &&
      @            nums1.length == nums3.length &&
      @            nums1.length == nums4.length;               // n is the common length
      @   requires 1 <= nums1.length && nums1.length <= 200;   // 1 ≤ n ≤ 200
      @
      @   //  −2^28 ≤ every element ≤  2^28
      @   requires (\forall int i; 0 <= i && i < nums1.length;
      @               -268435456 <= nums1[i] && nums1[i] <= 268435456 &&
      @               -268435456 <= nums2[i] && nums2[i] <= 268435456 &&
      @               -268435456 <= nums3[i] && nums3[i] <= 268435456 &&
      @               -268435456 <= nums4[i] && nums4[i] <= 268435456);
      @
      @   assignable \nothing;      // the method has no side-effects
      @
      @   // Result = number of quadruples whose sum is zero
      @   ensures \result ==
      @           (\sum int i; 0 <= i && i < nums1.length;
      @             (\sum int j; 0 <= j && j < nums1.length;
      @               (\sum int k; 0 <= k && k < nums1.length;
      @                 (\sum int l; 0 <= l && l < nums1.length;
      @                   (nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0 ? 1 : 0)))));
      @
      @   // A few useful corollaries (not strictly required, but provable from the
      @   // post-condition and the bounds on n):
      @   ensures 0 <= \result &&
      @           \result <= nums1.length * nums1.length *
      @                      nums1.length * nums1.length;
      @*/
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int k : nums3) {
            for (int i : nums4) {
                int sum = k + i;
                map.put(sum, map.getOrDefault(sum, 0) % 1);
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