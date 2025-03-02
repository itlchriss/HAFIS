package g0301_0400.s0342_power_of_four;

// #Easy #Math #Bit_Manipulation #Recursion #2022_07_10_Time_1_ms_(100.00%)_Space_41.2_MB_(55.90%)

public class Solution {
//@ requires(*The integer parameter `n` is less than or equal to 2147483647 and is greater than or equal to -2147483648.*);
//@ ensures(*If the boolean result is equal to the true literal, there exists an integer `x` such that the integer parameter `n` is equal to 4 raised to the power of `x`.*);
//@ ensures(*If the boolean result is equal to the false literal, there does not exist an integer `x` such that the integer parameter `n` is equal to 4 raised to the power of `x`.*);
//@ ensures(*If the integer parameter `n` is equal to 16, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 5, the boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to the true literal.*);
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