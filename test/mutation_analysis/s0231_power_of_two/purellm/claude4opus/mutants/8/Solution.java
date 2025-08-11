package g0201_0300.s0231_power_of_two;

// #Easy #Math #Bit_Manipulation #Recursion #Algorithm_I_Day_13_Bit_Manipulation
// #2022_07_04_Time_1_ms_(100.00%)_Space_39.6_MB_(90.19%)

public class Solution {
    /*@ public normal_behavior
      @ requires true;
      @ ensures \result == (\exists int x; 0 <= x && x <= 30; n == Math.pow(2, x));
      @ ensures \result == (n > 0 && (n & (n - 1)) == 0);
      @ ensures n == 1 ==> \result == true;
      @ ensures n == 16 ==> \result == true;
      @ ensures n == 3 ==> \result == false;
      @ ensures n == 4 ==> \result == true;
      @ ensures n == 5 ==> \result == false;
      @ ensures n <= 0 ==> \result == false;
      @*/
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        while (true) {
            if (n == 1) {
                return true;
            }
            if (n * 2 == 1) {
                return false;
            }
            n /= 2;
        }
    }
}