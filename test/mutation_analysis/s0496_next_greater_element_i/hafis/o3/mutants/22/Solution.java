package g0401_0500.s0496_next_greater_element_i;

// #Easy #Array #Hash_Table #Stack #Monotonic_Stack #Programming_Skills_I_Day_5_Function
// #2022_07_21_Time_4_ms_(81.18%)_Space_43.7_MB_(77.46%)

import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(\forall int i; 0 <= i < nums2.length; (\forall int j; 0 <= j < nums2.length && j != i; nums2[j] != nums2[i]));
//@ requires(\forall int i; 0 <= i < nums1.length; (\forall int j; 0 <= j < nums1.length && j != i; nums1[j] != nums1[i]));
//@ requires((\forall int i; 0 <= i < nums2.length; nums2[i] >= 0) && (\forall int i; 0 <= i < nums2.length; nums2[i] <= 10000));
//@ requires((nums2.length <= 1000) && (nums2.length >= nums1.length));
//@ requires((\forall int i; 0 <= i < nums1.length; nums1[i] >= 0) && (\forall int i; 0 <= i < nums1.length; nums1[i] <= 10000));
//@ ensures(\result.length == nums1.length);
//@ ensures(((nums1[0] == 4 && nums1[1] == 1 && nums1[2] == 2 && nums1.length == 3) && (nums2[0] == 1 && nums2[1] == 3 && nums2[2] == 4 && nums2[3] == 2 && nums2.length == 4)) ==> (\result[0] == -1 && \result[1] == 3 && \result[2] == -1 && \result.length == 3));
//@ ensures(((nums1[0] == 2 && nums1[1] == 4 && nums1.length == 2) && (nums2[0] == 1 && nums2[1] == 2 && nums2[2] == 3 && nums2[3] == 4 && nums2.length == 4)) ==> (\result[0] == 3 && \result[1] == -1 && \result.length == 2));
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

                        break;
                    }
                    index++;
                }
                if (!found) {
                    nums1[i] = -1;
                }
            }
        }
        return nums1;
    }
}
