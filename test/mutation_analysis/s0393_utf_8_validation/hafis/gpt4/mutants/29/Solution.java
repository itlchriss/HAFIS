package g0301_0400.s0393_utf_8_validation;

// #Medium #Array #Bit_Manipulation #2022_07_13_Time_1_ms_(100.00%)_Space_43_MB_(87.62%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((data.length <= 20000) && (data.length >= 1));
//@ requires((\forall int i; 0 <= i < data.length; data[i] <= 255) && (\forall int i; 0 <= i < data.length; data[i] >= 0));
//@ ensures((data[0] == 235 && data[1] == 140 && data[2] == 4 && data.length == 3) ==> (\result == false));
//@ ensures((data[0] == 197 && data[1] == 130 && data[2] == 1 && data.length == 3) ==> (\result == true));
    public boolean validUtf8(int[] data) {
        int count = 0;
        for (int d : data) {
            if (count == 0) {
                if (d >> 5 == 0b110) {
                    count = 1;
                } else if (d >> 4 == 0b1110) {
                    count = 2;
                } else if (d >> 3 == 0b11110) {
                    count = 3;
                } else if (d >> 7 == 1) {
                    return false;
                }
            } else {
                if (d >> 6 != 0b10) {
                    return false;
                } else {
                    count--;
                }
            }
        }
        return count >= 0;
    }
}
