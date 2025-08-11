package g0401_0500.s0461_hamming_distance;

// #Easy #Bit_Manipulation #Udemy_Bit_Manipulation
// #2022_07_19_Time_0_ms_(100.00%)_Space_40.9_MB_(60.77%)

public class Solution {
    /*@ public normal_behavior
      @ requires 0 <= x && x <= Integer.MAX_VALUE;
      @ requires 0 <= y && y <= Integer.MAX_VALUE;
      @ ensures \result == Integer.bitCount(x ^ y);
      @ ensures \result >= 0 && \result <= 32;
      @ pure
      @*/
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}