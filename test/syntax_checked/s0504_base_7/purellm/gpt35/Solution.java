package g0501_0600.s0504_base_7;

// #Easy #Math #2022_07_24_Time_0_ms_(100.00%)_Space_39.4_MB_(98.67%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires num >= -10000000 && num <= 10000000;
//@ ensures \result != null;
//@ ensures \result.equals(Integer.toString(num, 7));
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}
