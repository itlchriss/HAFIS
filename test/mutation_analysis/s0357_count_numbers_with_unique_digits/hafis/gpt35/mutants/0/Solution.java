package g0301_0400.s0357_count_numbers_with_unique_digits;

// #Medium #Dynamic_Programming #Math #Backtracking
// #2022_07_11_Time_0_ms_(100.00%)_Space_41.2_MB_(23.67%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((n >= 0) && (n <= 8));
//@ ensures((n == 2) ==> (\result == 91));
//@ ensures((n == 0) ==> (\result == 1));
    public int countNumbersWithUniqueDigits(int n) {
        int ans = 1;
        for (int i = 1; i < n; i++) {
            int mul = 1;
            for (int j = 1; j < i; j++) {
                mul *= (10 - j);
            }
            ans = ans + 9 * mul;
        }
        return ans;
    }
}
