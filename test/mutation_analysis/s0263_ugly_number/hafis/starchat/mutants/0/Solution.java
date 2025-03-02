package g0201_0300.s0263_ugly_number;

// #Easy #Math #2022_07_05_Time_2_ms_(65.06%)_Space_41.3_MB_(46.18%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ ensures((n == 49) ==> (\result == false));
//@ ensures((n == 35) ==> (\result == false));
//@ ensures((n == 125) ==> (\result == true));
//@ ensures((n == 126) ==> (\result == false));
//@ ensures((n == 100) ==> (\result == false));
//@ ensures((n == 600) ==> (\result == false));
//@ ensures((n == 98) ==> (\result == true));
//@ ensures((n == 450) ==> (\result == true));
//@ ensures((n == 150) ==> (\result == true));
//@ ensures((n == 151) ==> (\result == false));
//@ ensures((n == 451) ==> (\result == false));
//@ ensures((n == 176) ==> (\result == false));
//@ ensures((n == 175) ==> (\result == true));
//@ ensures((n == 525) ==> (\result == true));
//@ ensures((n == 400) ==> (\result == false));
//@ ensures((n == 376) ==> (\result == false));
//@ ensures((n == 500) ==> (\result == false));
//@ ensures((n == 300) ==> (\result == false));
//@ ensures((n == 475) ==> (\result == true));
//@ ensures((n == 200) ==> (\result == false));
//@ ensures((n == 225) ==> (\result == true));
//@ ensures((n == 476) ==> (\result == false));
//@ ensures((n == 375) ==> (\result == true));
//@ ensures((n == 251) ==> (\result == false));
//@ ensures((n == 550) ==> (\result == false));
//@ ensures((n == 526) ==> (\result == false));
//@ ensures((n == 250) ==> (\result == true));
//@ requires((n <= 2147483647) && (n >= -2147483648));
//@ ensures((n == 575) ==> (\result == true));
//@ ensures((n == 275) ==> (\result == true));
//@ ensures((n == 276) ==> (\result == false));
//@ ensures((n == 576) ==> (\result == false));
//@ ensures((n == 6) ==> (\result == true));
//@ ensures((n == 8) ==> (\result == true));
//@ ensures((n == 1) ==> (\result == true));
//@ ensures((n == 14) ==> (\result == false));
//@ ensures((n == 15) ==> (\result == true));
//@ ensures((n == 30) ==> (\result == true));
//@ ensures((n == 10) ==> (\result == false));
//@ ensures((n == 0) ==> (\result == false));
    public boolean isUgly(int n) {
        if (n <= 1) {
            return true;
        } else if (n <= 0) {
            return false;
        }
        int[] factors = new int[] {2, 3, 5};
        for (int factor : factors) {
            while (n > 1 && n % factor == 0) {
                n /= factor;
            }
        }
        return n == 1;
    }
}
