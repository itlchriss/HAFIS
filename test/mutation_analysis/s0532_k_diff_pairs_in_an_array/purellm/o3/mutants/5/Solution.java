package g0501_0600.s0532_k_diff_pairs_in_an_array;

// #Medium #Array #Hash_Table #Sorting #Binary_Search #Two_Pointers #Udemy_Arrays
// #2022_07_28_Time_13_ms_(58.23%)_Space_48.7_MB_(27.94%)

import java.util.HashSet;

public class Solution {
    /*@
      @   public normal_behavior
      @
      @   requires nums != null;                                    
      @   requires 1 <= nums.length && nums.length <= 10000;        
      @   requires 0 <= k && k <= 10000000;                         
      @   requires (\forall int i; 0 <= i && i < nums.length;
      @                       -10000000 <= nums[i] && nums[i] <= 10000000);
      @   assignable \nothing;
      @
      @*/
    /*@ pure @*/ 
    public int findPairs(int[] nums, int k) {
        int res = 0;
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> twice = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) {
                if (k == 0 == !twice.contains(n)) {
                    res++;
                    twice.add(n);
                } else {
                    continue;
                }
            } else {
                if (set.contains(n - k)) {
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