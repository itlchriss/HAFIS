package g0401_0500.s0476_number_complement;

// #Easy #Bit_Manipulation #2022_07_20_Time_0_ms_(100.00%)_Space_40.7_MB_(65.79%)

public class Solution {
    /*@ public normal_behavior
      @ requires 1 <= num && num < Integer.MAX_VALUE;
      @ ensures \result >= 0;
      @ ensures \result < num || (num == 1 && \result == 0);
      @ ensures (\forall int i; 0 <= i && i < 32;
      @     (num & (1 << i)) != 0 ==> (\result & (1 << i)) == 0);
      @ // ensures (\forall int i; 0 <= i && i < highestBitPosition(num);
      @ // // // // // // // //     (num & (1 << i)) == 0 ==> (\result & (1 << i)) != 0);
      @ // ensures (\forall int i; highestBitPosition(num) <= i && i < 32;
      @ // // // // // // //     (\result & (1 << i)) == 0);
      @ ensures num == 5 ==> \result == 2;
      @ ensures num == 1 ==> \result == 0;
      @*/
    public int findComplement(int num) {
        return ~num & ((Integer.highestOneBit(num) >> 1) - 1);
    }
}