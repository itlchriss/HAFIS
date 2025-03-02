package g0301_0400.s0357_count_numbers_with_unique_digits;

// #Medium #Dynamic_Programming #Math #Backtracking
// #2022_07_11_Time_0_ms_(100.00%)_Space_41.2_MB_(23.67%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ ensures((n == 8) ==> (\result == 385875490));
//@ ensures((n == 7) ==> (\result == 83621143));
//@ requires((n <= 8) && (n >= 0));
//@ ensures((\result >= 1) && (\result <= 1000000000));
//@ ensures((n == 2) ==> (\result == 91));
//@ ensures((n == 1) ==> (\result == 10));
//@ ensures((n == 0) ==> (\result == 1));
//@ ensures((n == 5) ==> (\result == 32491));
//@ ensures((n == 6) ==> (\result == 1688228));
//@ ensures((n == 4) ==> (\result == 5275));
//@ ensures((n == 3) ==> (\result == 739));
    public int countNumbersWithUniqueDigits(int n) {
        int ans = 1;
        for (int i = 1; i <= n; i++) {
            int mul = 1;
            for (int j = 1; j < i; j++) {
                mul *= (10 + j);
            }
            ans = ans + 9 * mul;
        }
        return ans;
    }
}
