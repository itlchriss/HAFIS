package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

public class Solution {
//@ requires(*Every value in parameter int[] `aliceSizes` must be at least 1 and at most 10^5*);
//@ requires(*Every value in parameter int[] `bobSizes` must be at least 1 and at most 10^5*);
//@ requires(*The sum of all values in parameter int[] `aliceSizes` must differ from the sum of all values in parameter int[] `bobSizes`*);
//@ ensures(*result int[] must contain exactly two elements*);
//@ ensures(*The first element of result int[] must be a value that occurs in parameter int[] `aliceSizes`*);
//@ ensures(*The second element of result int[] must be a value that occurs in parameter int[] `bobSizes`*);
//@ ensures(*After execution the sum of all values in parameter int[] `aliceSizes` minus the first element of result int[] plus the second element of result int[] equals the sum of all values in parameter int[] `bobSizes` minus the second element of result int[] plus the first element of result int[]*);
//@ ensures(*For every input that meets the prerequisites at least one result int[] meeting these behavioural specifications is guaranteed to exist*);
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
                ans[0] = bar + diff / 2;
                ans[1] = bar;
                break;
            }
        }
        return ans;
    }
}