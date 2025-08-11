package g0201_0300.s0258_add_digits;

// #Easy #Math #Simulation #Number_Theory #2022_07_05_Time_1_ms_(100.00%)_Space_39.3_MB_(98.44%)

public class Solution {
    /*@ public normal_behavior
      @ requires 0 <= num && num <= Integer.MAX_VALUE;
      @ ensures 0 <= \result && \result <= 9;
      @ ensures num == 0 ==> \result == 0;
      @ ensures num > 0 ==> \result == ((num - 1) % 9) + 1;
      @ pure
      @*/
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }
        if (num / 9 == 0) {
            return 9;
        }
        return num % 9;
    }
}