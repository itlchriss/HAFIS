package g0301_0400.s0342_power_of_four;

// #Easy #Math #Bit_Manipulation #Recursion #2022_07_10_Time_1_ms_(100.00%)_Space_41.2_MB_(55.90%)

public class Solution {
//@ ensures(*If boolean result is equal to the true literal, there exists an integer x greater than or equal to 0 such that 4 raised to x is equal to the integer parameter `n`.*);
//@ ensures(*If boolean result is equal to the false literal, for all integers x greater than or equal to 0 the value 4 raised to x is not equal to the integer parameter `n`.*);
//@ ensures(*If the integer parameter `n` is equal to 16, boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 5, boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is equal to 1, boolean result is equal to the true literal.*);
    public boolean isPowerOfFour(int n) {
        while (n >= 4) {
            if (n % 4 != 0) {
                return false;
            }
            n = n / 4;
        }
        return n == 1;
    }
}