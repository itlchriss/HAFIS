package g0201_0300.s0258_add_digits;

// #Easy #Math #Simulation #Number_Theory #2022_07_05_Time_1_ms_(100.00%)_Space_39.3_MB_(98.44%)

public class Solution {
    /*@
      @ public normal_behavior
      @   requires 0 <= num && num <= Integer.MAX_VALUE;
      @
      @ //   /*  The result is a single decimal digit. * /
      @   ensures 0 <= \result && \result <= 9;
      @
      @ //   /*  Functional definition (digital root). * /
      @   ensures (num == 0) ==> (\result == 0);
      @   ensures (num != 0) ==> (\result == 1 + (num - 1) % 9);
      @
      @ //   /*  No visible state is modified. * /
      @   assignable \nothing;
      @
      @ //   /*  Intended for use as a mathematical (side-effect-free) function. * /
      @*/
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }
        if (num % 9 == 0) {
            return 9;
        }
        return num % 9;
    }
}