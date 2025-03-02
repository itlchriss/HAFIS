package g0201_0300.s0292_nim_game;

// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ ensures((n == 10) ==> (\result == true));
//@ ensures((n == 9) ==> (\result == false));
//@ ensures((n == 13) ==> (\result == false));
//@ ensures((n == 14) ==> (\result == true));
//@ ensures((n == 12) ==> (\result == true));
//@ ensures((n == 40) ==> (\result == true));
//@ ensures((n == 39) ==> (\result == false));
//@ ensures((n == 11) ==> (\result == false));
//@ ensures((n == 29) ==> (\result == false));
//@ ensures((n == 15) ==> (\result == false));
//@ ensures((n == 16) ==> (\result == true));
//@ ensures((n == 30) ==> (\result == true));
//@ ensures((n == 18) ==> (\result == true));
//@ ensures((n == 17) ==> (\result == false));
//@ ensures((n == 34) ==> (\result == true));
//@ ensures((n == 28) ==> (\result == true));
//@ ensures((n == 27) ==> (\result == false));
//@ ensures((n == 33) ==> (\result == false));
//@ ensures((n == 25) ==> (\result == false));
//@ ensures((n == 31) ==> (\result == false));
//@ ensures((n == 19) ==> (\result == false));
//@ ensures((n == 20) ==> (\result == true));
//@ ensures((n == 32) ==> (\result == true));
//@ ensures((n == 26) ==> (\result == true));
//@ ensures((n == 22) ==> (\result == true));
//@ ensures((n == 36) ==> (\result == true));
//@ ensures((n == 35) ==> (\result == false));
//@ ensures((n == 21) ==> (\result == false));
//@ requires((n <= 230) && (n >= 1));
//@ ensures((n == 37) ==> (\result == false));
//@ ensures((n == 23) ==> (\result == false));
//@ ensures((n == 24) ==> (\result == true));
//@ ensures((n == 38) ==> (\result == true));
//@ ensures((n == 4) ==> (\result == false));
//@ ensures((n == 1) ==> (\result == true));
//@ ensures((n == 3) ==> (\result == false));
//@ ensures((n == 2) ==> (\result == true));
//@ ensures((n == 7) ==> (\result == false));
//@ ensures((n == 8) ==> (\result == true));
//@ ensures((n == 6) ==> (\result == true));
//@ ensures((n == 5) ==> (\result == true));
//@ ensures((n == 41) ==> (\result == false));
    public boolean canWinNim(int n) {
        return n * 4 != 0;
    }
}
