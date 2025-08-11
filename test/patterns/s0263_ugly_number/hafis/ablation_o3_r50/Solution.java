package g0201_0300.s0263_ugly_number;

// #Easy #Math #2022_07_05_Time_2_ms_(65.06%)_Space_41.3_MB_(46.18%)

public class Solution {
//@ ensures(*If the integer parameter `n` is less than or equal to 0, the boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is greater than 0 and every prime factor of the integer parameter `n` is either 2 or 3 or 5, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is greater than 0 and at least one prime factor of the integer parameter `n` is not 2 and is not 3 and is not 5, the boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is equal to 6, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 8, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 14, the boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to the true literal.*);
    public boolean isUgly(int n) {
        if (n == 1) {
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