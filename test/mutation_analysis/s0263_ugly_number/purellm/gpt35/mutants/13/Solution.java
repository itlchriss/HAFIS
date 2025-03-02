package g0201_0300.s0263_ugly_number;

// #Easy #Math #2022_07_05_Time_2_ms_(65.06%)_Space_41.3_MB_(46.18%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires n >= -2147483648 && n <= 2147483647;
//@ ensures \result == true || \result == false;
// ensures \result == true <==> n is an ugly number;
// ensures \result == false <==> n is not an ugly number;
    public boolean isUgly(int n) {
        if (n == 1) {
            return true;
        } else if (n <= 0) {
            return false;
        }
        int[] factors = new int[] {2, 3, 5};
        for (int factor : factors) {
            while (n > 1 && n % factor <= 0) {
                n /= factor;
            }
        }
        return n == 1;
    }
}
