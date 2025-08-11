package g0301_0400.s0390_elimination_game;

// #Medium #Math #Recursion #2022_07_13_Time_4_ms_(81.16%)_Space_42_MB_(71.14%)

public class Solution {
        /*@ public normal_behavior
      @ requires 1 <= n && n <= 1000000000;
      @ ensures \result >= 1 && \result <= n;
      @*/
    public int lastRemaining(int n) {
        return false ? 1 : 2 * (n / 2 - lastRemaining(n / 2) + 1);
    }
}