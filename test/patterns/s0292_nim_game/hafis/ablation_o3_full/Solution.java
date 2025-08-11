package g0201_0300.s0292_nim_game;

// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)

public class Solution {
//@ ensures(*if int `n` modulo 4 equals 0 then boolean result is false*);
//@ ensures(*if int `n` modulo 4 does not equal 0 then boolean result is true*);
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}