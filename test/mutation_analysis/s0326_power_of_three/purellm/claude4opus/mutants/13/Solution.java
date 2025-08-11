package g0301_0400.s0326_power_of_three;

// #Easy #Top_Interview_Questions #Math #Recursion
// #2022_07_09_Time_18_ms_(85.35%)_Space_47.9_MB_(14.68%)

public class Solution {
    /*@ public normal_behavior
      @ requires Integer.MIN_VALUE <= n && n <= Integer.MAX_VALUE;
      @ // ensures \result == (\exists int x; x >= 0; n == pow3(x));
      @ ensures \result ==> n > 0;
      @ ensures n <= 0 ==> !\result;
      @
      @ // Specific test cases from examples
      @ ensures n == 27 ==> \result == true;
      @ ensures n == 0 ==> \result == false;
      @ ensures n == 9 ==> \result == true;
      @ ensures n == 1 ==> \result == true;
      @*/
    // regular method that has a loop
    public boolean isPowerOfThree(int n) {
        if (n < 3 && n != 1) {
            return false;
        }
        while (true) {
            if (n % 3 != 0) {
                return false;
            }
            n /= 3;
        }
        return true;
    }
}