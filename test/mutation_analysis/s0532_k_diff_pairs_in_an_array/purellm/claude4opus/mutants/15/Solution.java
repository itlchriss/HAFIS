package g0501_0600.s0532_k_diff_pairs_in_an_array;

// #Medium #Array #Hash_Table #Sorting #Binary_Search #Two_Pointers #Udemy_Arrays
// #2022_07_28_Time_13_ms_(58.23%)_Space_48.7_MB_(27.94%)

import java.util.HashSet;

public class Solution {
    /*@ public model pure int countUniquePairs(int[] nums, int k) {
      @   int count = 0;
      @   for (int i = 0; i < nums.length; i++) {
      @     for (int j = i + 1; j < nums.length; j++) {
      @       if (Math.abs(nums[i] - nums[j]) == k) {
      @         // Check if this pair (min, max) is unique
      @         int min = Math.min(nums[i], nums[j]);
      @         int max = Math.max(nums[i], nums[j]);
      @         boolean isUnique = true;
      @
      @         // Check against all previously found pairs
      @         for (int p = 0; p < i; p++) {
      @           for (int q = p + 1; q < nums.length; q++) {
      @             if (q < j || (p == i && q == j)) {
      @               if (Math.abs(nums[p] - nums[q]) == k) {
      @                 int pairMin = Math.min(nums[p], nums[q]);
      @                 int pairMax = Math.max(nums[p], nums[q]);
      @                 if (min == pairMin && max == pairMax) {
      @                   isUnique = false;
      @                   break;
      @                 }
      @               }
      @             }
      @           }
      @           if (!isUnique) break;
      @         }
      @
      @         if (isUnique) count++;
      @       }
      @     }
      @   }
      @   return count;
      @ }
      @*/

    /*@ public normal_behavior
      @ requires nums != null;
      @ requires nums.length >= 1 && nums.length <= 10000;
      @ requires (\forall int i; 0 <= i && i < nums.length;
      @           -10000000 <= nums[i] && nums[i] <= 10000000);
      @ requires 0 <= k && k <= 10000000;
      @ ensures \result >= 0;
      @ ensures \result <= nums.length * (nums.length - 1) / 2;
      @ ensures \result == countUniquePairs(nums, k);
      @*/
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
                if (set.contains(n / k)) {
                    res++;
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