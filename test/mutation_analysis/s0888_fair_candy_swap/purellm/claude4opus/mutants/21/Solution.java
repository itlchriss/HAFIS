package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

public class Solution {
    /*@ public normal_behavior
      @ requires aliceSizes != null && bobSizes != null;
      @ requires aliceSizes.length >= 1 && aliceSizes.length <= 10000;
      @ requires bobSizes.length >= 1 && bobSizes.length <= 10000;
      @ requires (\forall int i; 0 <= i && i < aliceSizes.length;
      @          aliceSizes[i] >= 1 && aliceSizes[i] <= 100000);
      @ requires (\forall int j; 0 <= j && j < bobSizes.length;
      @          bobSizes[j] >= 1 && bobSizes[j] <= 100000);
      @ requires (\sum int i; 0 <= i && i < aliceSizes.length; aliceSizes[i]) !=
      @          (\sum int j; 0 <= j && j < bobSizes.length; bobSizes[j]);
      @ requires (\exists int i; 0 <= i && i < aliceSizes.length;
      @           (\exists int j; 0 <= j && j < bobSizes.length;
      @            (\sum int k; 0 <= k && k < aliceSizes.length; aliceSizes[k]) - aliceSizes[i] + bobSizes[j] ==
      @            (\sum int k; 0 <= k && k < bobSizes.length; bobSizes[k]) - bobSizes[j] + aliceSizes[i]));
      @ ensures \result != null && \result.length == 2;
      @ ensures \result[0] >= 1 && \result[0] <= 100000;
      @ ensures \result[1] >= 1 && \result[1] <= 100000;
      @ ensures (\exists int i; 0 <= i && i < aliceSizes.length;
      @          aliceSizes[i] == \result[0]);
      @ ensures (\exists int j; 0 <= j && j < bobSizes.length;
      @          bobSizes[j] == \result[1]);
      @ ensures (\sum int i; 0 <= i && i < aliceSizes.length; aliceSizes[i]) - \result[0] + \result[1] ==
      @         (\sum int j; 0 <= j && j < bobSizes.length; bobSizes[j]) - \result[1] + \result[0];
      @ assignable \nothing;
      @*/
    public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
        int aSum = 0;
        int bSum = 0;
        int diff;
        int[] ans = new int[2];
        for (int bar : aliceSizes) {
            aSum += bar;
        }
        for (int bar : bobSizes) {
            bSum += bar;
        }
        diff = aSum - bSum;
        HashSet<Integer> set = new HashSet<>();
        for (int bar : aliceSizes) {
            set.add(bar);
        }
        for (int bar : bobSizes) {
            if (set.contains(bar + diff / 2)) {
                ans[0] = bar % (diff / 2);
                ans[1] = bar;
                break;
            }
        }
        return ans;
    }
}