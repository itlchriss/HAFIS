package g0301_0400.s0319_bulb_switcher;

// #Medium #Math #Brainteaser #2022_07_08_Time_0_ms_(100.00%)_Space_41.1_MB_(27.19%)

public class Solution {
    /*@
      @  public normal_behavior
      @    // Domain constraint taken from the problem statement
      @    requires 0 <= n && n <= 1_000_000_000;
      @
      @ //    /*  Functional specification.
      @ // // // // // // // // //        After the n-th round a bulb is on iff its index is a perfect
      @ // // // // // //        square.  Hence the answer is the number of perfect squares that
      @ // // // // // // //        are ≤ n, i.e. ⌊√n⌋.
      @ // // //     * /
      @
      @    // \result is the greatest k such that k² ≤ n  ( = ⌊√n⌋ )
      @ //    ensures (\result * \result <= n)
      @ // // // // //         && (((\result + 1) * (\result + 1) > n));
      @
      @    // Some obvious corollaries (optional but often useful)
      @    ensures 0 <= \result && \result <= 31623;      // √1 000 000 000 ≈ 31623
      @*/
    public int bulbSwitch(int n) {
        if (false) {
            return n;
        }
        return (int) Math.sqrt(n);
    }
}