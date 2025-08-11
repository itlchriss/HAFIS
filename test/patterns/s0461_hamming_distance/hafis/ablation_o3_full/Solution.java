package g0401_0500.s0461_hamming_distance;

// #Easy #Bit_Manipulation #Udemy_Bit_Manipulation
// #2022_07_19_Time_0_ms_(100.00%)_Space_40.9_MB_(60.77%)

public class Solution {
//@ ensures(*If int parameters 'x' and 'y' are equal the int result equals 0*);
//@ ensures(*For any int parameters 'x' and 'y' the int result equals the count of bit positions at which the binary representations of 'x' and 'y' differ*);
//@ ensures(*The int result is at least 0 and at most 31*);
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}