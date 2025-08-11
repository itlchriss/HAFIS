package g0301_0400.s0390_elimination_game;

// #Medium #Math #Recursion #2022_07_13_Time_4_ms_(81.16%)_Space_42_MB_(71.14%)

public class Solution {
//@ ensures(*The integer result is greater than or equal to 1 and is less than or equal to the integer parameter `n`.*);
//@ ensures(*If the integer parameter `n` is equal to 9, the integer result is equal to 6.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the integer result is equal to 1.*);
    public int lastRemaining(int n) {
        return n == 1 ? 1 : 2 * (n / 2 - lastRemaining(n / 2) + 1);
    }
}