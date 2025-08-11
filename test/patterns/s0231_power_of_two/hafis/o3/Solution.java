package g0201_0300.s0231_power_of_two;

// #Easy #Math #Bit_Manipulation #Recursion #Algorithm_I_Day_13_Bit_Manipulation
// #2022_07_04_Time_1_ms_(100.00%)_Space_39.6_MB_(90.19%)

public class Solution {
//@ ensures(*If the integer parameter `n` is less than or equal to 0, the boolean result is equal to the false literal.*);
//@ ensures(*If the boolean result is equal to the true literal, the integer parameter `n` is greater than 0 and the integer parameter `n` is equal to two raised to an integer exponent.*);
//@ ensures(*If the boolean result is equal to the false literal and the integer parameter `n` is greater than 0, the integer parameter `n` is not equal to any power of two.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 16, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 3, the boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is equal to 4, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 5, the boolean result is equal to the false literal.*);
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        while (true) {
            if (n == 1) {
                return true;
            }
            if (n % 2 == 1) {
                return false;
            }
            n /= 2;
        }
    }
}