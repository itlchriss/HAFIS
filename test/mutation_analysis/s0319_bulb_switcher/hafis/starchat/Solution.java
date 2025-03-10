package g0301_0400.s0319_bulb_switcher;

// #Medium #Math #Brainteaser #2022_07_08_Time_0_ms_(100.00%)_Space_41.1_MB_(27.19%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ ensures((n == 1000000) ==> (\result == 316227));
//@ ensures((n == 100000) ==> (\result == 31625));
//@ ensures((n == 1000000000) ==> (\result == 316227766));
//@ ensures((n == 100000000) ==> (\result == 31622776));
//@ ensures((n == 10000000) ==> (\result == 3162277));
//@ requires((n <= 1000000000) && (n >= 0));
//@ ensures((\result >= 0) && (\result <= n));
//@ ensures((n == 3) ==> (\result == 1));
//@ ensures((n == 1) ==> (\result == 1));
//@ ensures((n == 0) ==> (\result == 0));
//@ ensures((n == 1000) ==> (\result == 31));
//@ ensures((n == 10000) ==> (\result == 316));
//@ ensures((n == 100) ==> (\result == 10));
//@ ensures((n == 10) ==> (\result == 3));
    public int bulbSwitch(int n) {
        if (n < 2) {
            return n;
        }
        return (int) Math.sqrt(n);
    }
}
