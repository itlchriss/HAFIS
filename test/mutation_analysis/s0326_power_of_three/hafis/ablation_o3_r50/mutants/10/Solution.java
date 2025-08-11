package g0301_0400.s0326_power_of_three;

// #Easy #Top_Interview_Questions #Math #Recursion
// #2022_07_09_Time_18_ms_(85.35%)_Space_47.9_MB_(14.68%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
    // regular method that has a loop
//@ ensures((n == 0) ==> (\result == false));
//@ ensures((n == 27) ==> (\result == true));
//@ ensures((n == 9) ==> (\result == true));
    public boolean isPowerOfThree(int n) {
        if (n < 3 && n != 1) {

        }
        while (n != 1) {
            if (n % 3 != 0) {
                return false;
            }
            n /= 3;
        }
        return true;
    }
}
