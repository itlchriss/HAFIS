package g0201_0300.s0231_power_of_two;

// #Easy #Math #Bit_Manipulation #Recursion #Algorithm_I_Day_13_Bit_Manipulation
// #2022_07_04_Time_1_ms_(100.00%)_Space_39.6_MB_(90.19%)

public class Solution {
//@ requires(*The integer parameter `n` is less than or equal to 2147483647 and is greater than or equal to -2147483648.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 16, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 3, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 4, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 5, the boolean result is equal to false.*);
//@ ensures(*The boolean result is equal to true if and only if there exists an integer `x` such that `n == 2^x`.*);
//@ ensures(*The boolean result is equal to false if and only if there does not exist an integer `x` such that `n == 2^x`.*);
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