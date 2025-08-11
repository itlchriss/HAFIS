package g0301_0400.s0393_utf_8_validation;

// #Medium #Array #Bit_Manipulation #2022_07_13_Time_1_ms_(100.00%)_Space_43_MB_(87.62%)

public class Solution {
//@ requires(*The length of the integer array parameter `data` is greater than or equal to 1 and is less than or equal to 20000.*);
//@ requires(*All values in the integer array parameter `data` are greater than or equal to 0 and are less than or equal to 255.*);
//@ ensures(*If the boolean result is equal to the true literal, the integer array parameter `data` represents a valid UTF-8 encoding sequence.*);
//@ ensures(*If the boolean result is equal to the false literal, the integer array parameter `data` does not represent a valid UTF-8 encoding sequence.*);
//@ ensures(*If the integer array parameter `data` is equal to [197,130,1], the boolean result is equal to the true literal.*);
//@ ensures(*If the integer array parameter `data` is equal to [235,140,4], the boolean result is equal to the false literal.*);
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
        return count == 0;
    }
}