package g0401_0500.s0496_next_greater_element_i;

// #Easy #Array #Hash_Table #Stack #Monotonic_Stack #Programming_Skills_I_Day_5_Function
// #2022_07_21_Time_4_ms_(81.18%)_Space_43.7_MB_(77.46%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums1 != null && nums2 != null;
//@ requires nums1.length >= 1 && nums1.length <= nums2.length && nums2.length <= 1000;
//@ requires (\forall int i; 0 <= i < nums1.length; 0 <= nums1[i] && nums1[i] <= 10000);
//@ requires (\forall int i; 0 <= i < nums2.length; 0 <= nums2[i] && nums2[i] <= 10000);
//@ requires (\forall int i, j; 0 <= i < nums1.length && 0 <= j < nums2.length; nums1[i] == nums2[j] ==> (\exists int k; 0 <= k < nums2.length; nums2[k] == nums1[i]));
//@ requires (\forall int i, j; 0 <= i < nums1.length && 0 <= j < nums1.length; i != j ==> nums1[i] != nums1[j]);
//@ requires (\forall int i, j; 0 <= i < nums2.length && 0 <= j < nums2.length; i != j ==> nums2[i] != nums2[j]);
//@ ensures \result.length == nums1.length;
//@ ensures (\forall int i; 0 <= i < nums1.length; (\exists int j; 0 <= j < nums2.length; nums1[i] == nums2[j] ==> (\exists int k; j < k < nums2.length; nums2[k] > nums2[j] ==> \result[i] == nums2[k]) || (\forall int k; j < k < nums2.length; nums2[k] <= nums2[j] ==> \result[i] == -1)));
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        //@ maintaining 0 <= i <= nums2.length;
        for (int i = 0; i < nums2.length; i++) {
            indexMap.put(nums2[i], i);
        }
        //@ maintaining 0 <= i <= nums1.length;
        for (int i = 0; i < nums1.length; i++) {
            int num = nums1[i];
            int index = indexMap.get(num);
            if (index == nums2.length - 1) {
                nums1[i] = -1;
            } else {
                boolean found = false;
                while (index < nums2.length) {
                    if (nums2[index] > num) {
                        nums1[i] = nums2[index];
                        found = true;
                        break;
                    }
                    index++;
                }
                if (!false) {
                    nums1[i] = -1;
                }
            }
        }
        return nums1;
    }
}
