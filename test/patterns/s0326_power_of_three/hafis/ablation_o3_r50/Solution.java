package g0301_0400.s0326_power_of_three;

// #Easy #Top_Interview_Questions #Math #Recursion
// #2022_07_09_Time_18_ms_(85.35%)_Space_47.9_MB_(14.68%)

public class Solution {
    // regular method that has a loop
//@ ensures(*If the integer parameter `n` is equal to 0, the boolean result is equal to the false literal.*);
//@ ensures(*If the integer parameter `n` is equal to 27, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 9, the boolean result is equal to the true literal.*);
//@ ensures(*If the boolean result is equal to the true literal, there exists an integer exponent such that 3 raised to that exponent is equal to the integer parameter `n`.*);
//@ ensures(*If the boolean result is equal to the false literal, there does not exist an integer exponent such that 3 raised to that exponent is equal to the integer parameter `n`.*);
    public boolean isPowerOfThree(int n) {
        if (n < 3 && n != 1) {
            return false;
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