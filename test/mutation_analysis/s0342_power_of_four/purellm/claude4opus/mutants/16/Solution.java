package g0301_0400.s0342_power_of_four;

// #Easy #Math #Bit_Manipulation #Recursion #2022_07_10_Time_1_ms_(100.00%)_Space_41.2_MB_(55.90%)

public class Solution {
    /*@ public normal_behavior
      @ requires Integer.MIN_VALUE <= n && n <= Integer.MAX_VALUE;
      @ // ensures \result == (\exists int x; x >= 0 && x <= 15; n == pow4(x));
      @ ensures \result ==> n > 0;
      @ ensures \result ==> (n & (n - 1)) == 0;  // n is a power of 2
      @ ensures \result ==> (n & 0x55555555) != 0;  // n has a 1 bit in an even position
      @*/
    public boolean isPowerOfFour(int n) {
        while (n >= 4) {
            if (n % 4 != 0) {
                return false;
            }
            n = n / 4;
        }
        return n <= 1;
    }
}