package g0401_0500.s0461_hamming_distance;

// #Easy #Bit_Manipulation #Udemy_Bit_Manipulation
// #2022_07_19_Time_0_ms_(100.00%)_Space_40.9_MB_(60.77%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((y >= 0) && (y <= 2147483647));
//@ ensures((\result >= 0) && (\result <= 31));
//@ ensures((x == y) ==> (\result == 0));
//@ ensures(((x == 3) && (y == 1)) ==> (\result == 1));
//@ ensures(((x == 1) && (y == 4)) ==> (\result == 2));
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x & y);
    }
}
