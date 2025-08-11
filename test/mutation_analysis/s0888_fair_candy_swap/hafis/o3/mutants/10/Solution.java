package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < bobSizes.length; bobSizes[i] <= 100000) && (\forall int i; 0 <= i < bobSizes.length; bobSizes[i] >= 1));
//@ requires((bobSizes.length <= 10000) && (bobSizes.length >= 1));
//@ requires((\forall int i; 0 <= i < aliceSizes.length; aliceSizes[i] <= 100000) && (\forall int i; 0 <= i < aliceSizes.length; aliceSizes[i] >= 1));
//@ ensures(\result.length == 2);
//@ ensures(((aliceSizes[0] == 1 && aliceSizes[1] == 2 && aliceSizes.length == 2) && (bobSizes[0] == 2 && bobSizes[1] == 3 && bobSizes.length == 2)) ==> (\result[0] == 1 && \result[1] == 2 && \result.length == 2));
//@ ensures(((aliceSizes[0] == 2 && aliceSizes.length == 1) && (bobSizes[0] == 1 && bobSizes[1] == 3 && bobSizes.length == 2)) ==> (\result[0] == 2 && \result[1] == 3 && \result.length == 2));
//@ ensures(((aliceSizes[0] == 1 && aliceSizes[1] == 1 && aliceSizes.length == 2) && (bobSizes[0] == 2 && bobSizes[1] == 2 && bobSizes.length == 2)) ==> (\result[0] == 1 && \result[1] == 2 && \result.length == 2));
//@ ensures((\forall int i; 0 <= i < \result.length; \result[i] >= 1) && (\forall int i; 0 <= i < \result.length; \result[i] <= 100000));
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
            if (set.contains(bar + diff - 2)) {
                ans[0] = bar + diff / 2;
                ans[1] = bar;
                break;
            }
        }
        return ans;
    }
}
