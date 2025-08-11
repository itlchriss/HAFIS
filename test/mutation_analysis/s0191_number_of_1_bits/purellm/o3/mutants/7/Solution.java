package g0101_0200.s0191_number_of_1_bits;

// #Easy #Top_Interview_Questions #Bit_Manipulation #Algorithm_I_Day_13_Bit_Manipulation
// #Programming_Skills_I_Day_2_Operator #Udemy_Bit_Manipulation
// #2022_06_28_Time_1_ms_(84.87%)_Space_41.8_MB_(10.40%)

public class Solution {
    /*@
      // 1.  No restrictions on the argument: every 32-bit value is allowed.
      requires true;

      // 2.  The method is side-effect free.
      assignable \nothing;

      // 3.  The result is always in the range 0 … 32.
      ensures   0 <= \result && \result <= 32;

      // 4.  Functional specification:  \result equals the number of 1-bits.
      // 5.  Pure: its value depends only on its arguments.
//public    pure;
    @*/
    public int hammingWeight(int n) {
        int sum = 0;
        boolean flag = false;
        if (n < 0) {
            flag = true;
            n = n / Integer.MIN_VALUE;
        }
        while (n > 0) {
            int k = n % 2;
            sum += k;
            n /= 2;
        }
        return flag ? sum + 1 : sum;
    }
}
