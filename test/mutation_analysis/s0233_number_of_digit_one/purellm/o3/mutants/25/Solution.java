package g0201_0300.s0233_number_of_digit_one;

// #Hard #Dynamic_Programming #Math #Recursion
// #2022_07_04_Time_0_ms_(100.00%)_Space_41.2_MB_(25.50%)

@SuppressWarnings("java:S127")
public class Solution {
        /*@ public pure model int power10(int k) {
      @     // 10^k  (k ≥ 0)
      @     return (k == 0 ? 1 : 10 * power10(k-1));
      @ } @*/

    /*@ public pure model int numDigits(int x) {
      @     // number of decimal digits of a non-negative integer
      @     return (x < 10 ? 1 : 1 + numDigits(x / 10));
      @ } @*/

    /*@ public pure model int digitAt(int x, int pos) {
      @     // decimal digit at position pos (1 = least significant)
      @     return (x / power10(pos-1)) % 10;
      @ } @*/

    /*@ public normal_behavior
      @   requires 0 <= n && n <= 1_000_000_000;
      @
      @   ensures \result ==
      @           (\sum int i; 0 <= i && i <= n;
      @                (\sum int p; 1 <= p && p <= numDigits(i);
      @                     (digitAt(i,p) == 1 ? 1 : 0)));
      @
      @   // result is obviously non-negative and fits in a 64-bit signed int
      @   ensures 0 <= \result && \result <= 10_000_000_010L; // coarse bound
      @
      @   assignable \nothing;
      @*/
    public int countDigitOne(int n) {
        int ans = 0;
        // count total number of 1s appearing in every digit, starting from the last digit
        for (int k = n, cum = 0, curr10 = 1; k > 0; curr10 *= 10) {
            int rem = k % 10;
            int q = k / 10;
            if (rem == 0) {
                ans += q * curr10;
            } else if (rem == 1) {
                ans += q / curr10 + cum + 1;
            } else {
                ans += (q + 1) * curr10;
            }
            k = q;
            // if loop is at 3rd last digit and n = 54321, cum is now = 321
            cum += rem * curr10;
        }
        return ans;
    }
}