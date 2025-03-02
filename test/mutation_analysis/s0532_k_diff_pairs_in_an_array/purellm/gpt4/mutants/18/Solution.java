package g0501_0600.s0532_k_diff_pairs_in_an_array;

// #Medium #Array #Hash_Table #Sorting #Binary_Search #Two_Pointers #Udemy_Arrays
// #2022_07_28_Time_13_ms_(58.23%)_Space_48.7_MB_(27.94%)

import java.util.HashSet;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires nums != null;
//@ requires nums.length >= 1 && nums.length <= 10000;
//@ requires (\forall int i; 0 <= i < nums.length; -10000000 <= nums[i] <= 10000000);
//@ requires 0 <= k && k <= 10000000;
//@ ensures \result >= 0;
//@ ensures (\forall int i, j; 0 <= i < j < nums.length; Math.abs(nums[i] - nums[j]) == k ==> (\exists int count; 0 <= count <= \result; (\forall int x, y; 0 <= x < y < nums.length; Math.abs(nums[x] - nums[y]) == k ==> (nums[i] == nums[x] && nums[j] == nums[y]) || (nums[i] == nums[y] && nums[j] == nums[x]))));
    public int findPairs(int[] nums, int k) {
        int res = 0;
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> twice = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) {
                if (k == 0 && !twice.contains(n)) {
                    res++;
                    twice.add(n);
                } else {
                    continue;
                }
            } else {
                if (set.contains(n - k)) {

                }
                if (set.contains(n + k)) {
                    res++;
                }
            }
            set.add(n);
        }
        return res;
    }
}
