package g0401_0500.s0476_number_complement;

// #Easy #Bit_Manipulation #2022_07_20_Time_0_ms_(100.00%)_Space_40.7_MB_(65.79%)

public class Solution {
//@ ensures(*int result has a binary representation whose length equals the binary length of int 'num' when written without leading zero bits*);
//@ ensures(*every bit in result is one exactly when the corresponding bit in int 'num' is zero*);
//@ ensures(*every bit in result is zero exactly when the corresponding bit in int 'num' is one*);
//@ ensures(*int result lies in the closed interval from zero to two raised to the power of the binary length of int 'num' minus one and result is different from int 'num'*);
    public int findComplement(int num) {
        return ~num & ((Integer.highestOneBit(num) << 1) - 1);
    }
}