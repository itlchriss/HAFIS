package g0801_0900.s0888_fair_candy_swap;

// #Easy #Array #Hash_Table #Sorting #Binary_Search
// #2022_03_28_Time_18_ms_(68.20%)_Space_72.2_MB_(19.02%)

import java.util.HashSet;

public class Solution {
//@ requires(*The length of the integer array parameter `bobSizes` is greater than or equal to 1 and is less than or equal to 10000.*);
//@ requires(*All values in the integer array parameter `aliceSizes` are greater than or equal to 1 and are less than or equal to 100000.*);
//@ requires(*All values in the integer array parameter `bobSizes` are greater than or equal to 1 and are less than or equal to 100000.*);
//@ requires(*The sum of the values in the integer array parameter `aliceSizes` is not equal to the sum of the values in the integer array parameter `bobSizes`.*);
//@ ensures(*The integer array result has a length that is equal to 2.*);
//@ ensures(*All values in the integer array result are greater than or equal to 1 and are less than or equal to 100000.*);
//@ ensures(*The first element of the integer array result is an element of the integer array parameter `aliceSizes`.*);
//@ ensures(*The second element of the integer array result is an element of the integer array parameter `bobSizes`.*);
//@ ensures(*The sum of the integer array parameter `aliceSizes` minus the first element of the integer array result plus the second element of the integer array result is equal to the sum of the integer array parameter `bobSizes` minus the second element of the integer array result plus the first element of the integer array result.*);
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