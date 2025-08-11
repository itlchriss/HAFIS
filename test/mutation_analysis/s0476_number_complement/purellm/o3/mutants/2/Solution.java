package g0401_0500.s0476_number_complement;

// #Easy #Bit_Manipulation #2022_07_20_Time_0_ms_(100.00%)_Space_40.7_MB_(65.79%)

public class Solution {
    /*@
      @ // --- Pre-condition ---------------------------------------------------
      @ // input range taken from the exercise statement
      @ requires 1 <= num && num < 2147483648L;   // 1 ≤ num < 2³¹
      @
      @ // --- Frame condition -------------------------------------------------
      @ assignable \nothing;                      // function is pure
      @
      @ // --- Post-condition --------------------------------------------------
      @ // Let k be the number of significant bits of num
      @ // (i.e. 2^(k-1) ≤ num < 2^k).  The value returned is the
      @ // bitwise complement of num restricted to those k bits.
      @ ensures (\exists int k;
      @            0 < k &&
      @            (1 << (k-1)) <= num && num < (1 << k) &&
      @            \result == (((1 << k) - 1) ^ num));
      @
      @ // The result is always a non-negative 31-bit value
      @ ensures 0 <= \result && \result < (1 << 31);
      @*/
    public int findComplement(int num) {
        return ~num & ((Integer.highestOneBit(num) >> 1) - 1);
    }
}