package g0201_0300.s0292_nim_game;

// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)

public class Solution {
//@ requires(*The integer parameter `n` is less than or equal to 231 - 1 and is greater than or equal to 1.*);
//@ ensures(*If the integer parameter `n` is equal to 4, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 2, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 3, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 5, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 6, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 7, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 8, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 9, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 10, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 11, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 12, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 13, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 14, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 15, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 16, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 17, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 18, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 19, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 20, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 21, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 22, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 23, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 24, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 25, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 26, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 27, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 28, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 29, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 30, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 31, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 32, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 33, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 34, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 35, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 36, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 37, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 38, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 39, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 40, the boolean result is equal to true.*);
//@ ensures(*If the integer parameter `n` is equal to 41, the boolean result is equal to false.*);
//@ ensures(*If the integer parameter `n` is equal to 42, the boolean result*);
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}