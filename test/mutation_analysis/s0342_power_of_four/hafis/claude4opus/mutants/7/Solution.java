package g0301_0400.s0342_power_of_four;

// #Easy #Math #Bit_Manipulation #Recursion #2022_07_10_Time_1_ms_(100.00%)_Space_41.2_MB_(55.90%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((n >= -2147483648) && (n <= 2147483647));
//@ ensures((n == 5) ==> (\result == false));
//@ ensures((n == 16) ==> (\result == true));
//@ ensures((n <= 0) ==> (\result == false));
//@ ensures((n == 1) ==> (\result == true));
    public boolean isPowerOfFour(int n) {
        while (n >= 4) {
            if (n % 4 < 0) {
                return false;
            }
            n = n / 4;
        }
        return n == 1;
    }
}
