package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

public class Solution {
//@ requires(*The length of the integer array parameter `aliceSizes` is less than or equal to 10000 and is greater than or equal to 1.*);
//@ requires(*The length of the integer array parameter `bobSizes` is less than or equal to 10000 and is greater than or equal to 1.*);
//@ requires(*All values in the integer array parameter `aliceSizes` are less than or equal to 100000 and are greater than or equal to 1.*);
//@ requires(*All values in the integer array parameter `bobSizes` are less than or equal to 100000 and are greater than or equal to 1.*);
//@ requires(*The sum of all values in the integer array parameter `aliceSizes` is not equal to the sum of all values in the integer array parameter `bobSizes`.*);
//@ ensures(*The length of the integer array result is equal to 2.*);
//@ ensures(*The first value of the integer array result is contained in the integer array parameter `aliceSizes`.*);
//@ ensures(*The second value of the integer array result is contained in the integer array parameter `bobSizes`.*);
//@ ensures(*If the integer array parameter `aliceSizes` is equal to [1,1] and the integer array parameter `bobSizes` is equal to [2,2], the integer array result is equal to [1,2].*);
//@ ensures(*If the integer array parameter `aliceSizes` is equal to [1,2] and the integer array parameter `bobSizes` is equal to [2,3], the integer array result is equal to [1,2].*);
//@ ensures(*If the integer array parameter `aliceSizes` is equal to [2] and the integer array parameter `bobSizes` is equal to [1,3], the integer array result is equal to [2,3].*);
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