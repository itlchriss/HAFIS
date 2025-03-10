package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires aliceSizes != null && bobSizes != null;
//@ requires aliceSizes.length >= 1 && aliceSizes.length <= 10000;
//@ requires bobSizes.length >= 1 && bobSizes.length <= 10000;
//@ requires (\forall int i; 0 <= i < aliceSizes.length; 1 <= aliceSizes[i] && aliceSizes[i] <= 100000);
//@ requires (\forall int j; 0 <= j < bobSizes.length; 1 <= bobSizes[j] && bobSizes[j] <= 100000);
//@ requires (\sum int i; 0 <= i < aliceSizes.length; aliceSizes[i]) != (\sum int j; 0 <= j < bobSizes.length; bobSizes[j]);
//@ ensures \result.length == 2;
//@ ensures (\exists int i, j; 0 <= i < aliceSizes.length && 0 <= j < bobSizes.length; aliceSizes[i] - bobSizes[j] == ((\sum int k; 0 <= k < aliceSizes.length; aliceSizes[k]) -(\sum int l; 0 <= l < bobSizes.length; bobSizes[l])) / 2 && \result[0] == aliceSizes[i] && \result[1] == bobSizes[j]);
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
            if (set.contains(bar - diff / 2)) {
                ans[0] = bar + diff / 2;
                ans[1] = bar;
                break;
            }
        }
        return ans;
    }
}
