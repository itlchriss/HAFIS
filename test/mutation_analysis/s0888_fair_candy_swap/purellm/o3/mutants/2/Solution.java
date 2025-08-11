package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

public class Solution {
    /*@
  @ // ---------- PRECONDITIONS ----------
  @ requires aliceSizes != null && bobSizes != null;
  @
  @ // Length constraints
  @ requires 1 <= aliceSizes.length && aliceSizes.length <= 10000;
  @ requires 1 <= bobSizes.length  && bobSizes.length  <= 10000;
  @
  @ // Element value constraints
  @ requires (\forall int i; 0 <= i && i < aliceSizes.length;
  @                     1 <= aliceSizes[i] && aliceSizes[i] <= 100000);
  @ requires (\forall int j; 0 <= j && j < bobSizes.length;
  @                     1 <= bobSizes[j] && bobSizes[j] <= 100000);
  @
  @ // Alice and Bob have different initial totals
  @ requires (\sum int i; 0 <= i && i < aliceSizes.length;  aliceSizes[i])
  @        != (\sum int j; 0 <= j && j < bobSizes.length; bobSizes[j]);
  @
  @ // ---------- POSTCONDITIONS ----------
  @ ensures \result != null && \result.length == 2;
  @
  @ // \result[0] must be a value appearing in aliceSizes
  @ ensures (\exists int i; 0 <= i && i < aliceSizes.length;
  @                    \result[0] == aliceSizes[i]);
  @
  @ // \result[1] must be a value appearing in bobSizes
  @ ensures (\exists int j; 0 <= j && j < bobSizes.length;
  @                    \result[1] == bobSizes[j]);
  @
  @ // After exchanging the two chosen boxes, totals are equal
  @ ensures (
  @     (\sum int k; 0 <= k && k < aliceSizes.length; aliceSizes[k])
  @       - \result[0] + \result[1]
  @   ==
  @     (\sum int k; 0 <= k && k < bobSizes.length;  bobSizes[k])
  @       - \result[1] + \result[0]
  @ );
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
        diff = aSum % bSum;
        HashSet<Integer> set = new HashSet<>();
        for (int bar : aliceSizes) {
            set.add(bar);
        }
        for (int bar : bobSizes) {
            if (set.contains(bar + diff / 2)) {
                ans[0] = bar + diff / 2;
                ans[1] = bar;
                break;
            }
        }
        return ans;
    }
}