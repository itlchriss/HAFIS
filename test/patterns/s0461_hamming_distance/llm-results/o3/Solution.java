package g0401_0500.s0461_hamming_distance;

// #Easy #Bit_Manipulation #Udemy_Bit_Manipulation
// #2022_07_19_Time_0_ms_(100.00%)_Space_40.9_MB_(60.77%)

public class Solution {
     /*@ public normal_behavior
      @   requires 0 <= x && x <= 0x7FFFFFFF;          // 0 … 2^31 – 1
      @   requires 0 <= y && y <= 0x7FFFFFFF;          // 0 … 2^31 – 1
      @
      @   assignable \nothing;                         // function is pure
      @
      @   ensures 0 <= \result && \result <= 31;       // at most 31 differing bits
      @
      @   // \result equals the number of bit positions
      @   // (among the least–significant 32 bits) where x and y differ.
      @   ensures \result ==
      @           (\num_of int i; 0 <= i && i < 32;
      @                     (((x >>> i) & 1) != ((y >>> i) & 1)));
      @*/
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}