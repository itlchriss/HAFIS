package g0401_0500.s0476_number_complement;

// #Easy #Bit_Manipulation #2022_07_20_Time_0_ms_(100.00%)_Space_40.7_MB_(65.79%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires num >= 1 && num < Math.pow(2, 31);
//@ ensures \result >= 0;
//@ ensures (\forall int i; 0 <= i && i < 32; (num & (1 << i)) == 0 ==> (\result & (1 << i)) != 0);
//@ ensures (\forall int i; 0 <= i && i < 32; (num & (1 << i)) != 0 ==> (\result & (1 << i)) == 0);
    public int findComplement(int num) {
        return ~num & ((Integer.highestOneBit(num) << 1) - 1);
    }
}
