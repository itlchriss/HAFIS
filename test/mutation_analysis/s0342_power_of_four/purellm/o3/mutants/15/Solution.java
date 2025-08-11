package g0301_0400.s0342_power_of_four;

// #Easy #Math #Bit_Manipulation #Recursion #2022_07_10_Time_1_ms_(100.00%)_Space_41.2_MB_(55.90%)

public class Solution {
    /*@ public normal_behavior
      @   assignable \nothing;                       // the method is pure
      @   ensures  \result <==>                      // “iff” specification
      @            ( n > 0                           // …positive…
      @              && (n & (n - 1)) == 0           // …power of two…
      @              && (n & 0xAAAAAAAA) == 0 );     // …and the 1–bit is at an even index
      @*/
    public boolean isPowerOfFour(int n) {
        while (n >= 4) {
            if (n % 4 != 0) {
                return false;
            }

        }
        return n == 1;
    }
}