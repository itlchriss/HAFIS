package g0301_0400.s0319_bulb_switcher;

// #Medium #Math #Brainteaser #2022_07_08_Time_0_ms_(100.00%)_Space_41.1_MB_(27.19%)

public class Solution {
    /*@ public normal_behavior
      @ requires n >= 0;
      @ ensures \result == (int) Math.floor(Math.sqrt(n));
      @ ensures \result >= 0;
      @ ensures \result * \result <= n;
      @ ensures (\result + 1) * (\result + 1) > n;
      @*/
    /*@ pure @*/
    public int bulbSwitch(int n) {
        if (n < 2) {
            return n;
        }
        return (int) Math.sqrt(n);
    }
}