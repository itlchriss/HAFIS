package g0201_0300.s0263_ugly_number;

// #Easy #Math #2022_07_05_Time_2_ms_(65.06%)_Space_41.3_MB_(46.18%)

public class Solution {
    /*@ public normal_behavior
      @ // // requires Integer.MIN_VALUE <= n && n <= Integer.MAX_VALUE;
      @ // // ensures \result == (n > 0 && isUglyNumber(n));
      @
      @ // Helper specification to define what makes a number ugly
      @ public static pure model boolean isUglyNumber(int num) {
      @     return num == 1 ||
      @            (num > 1 && onlyHasPrimeFactors235(num));
      @ }
      @
      @ // Helper specification to check if a number only has prime factors 2, 3, and 5
      @ public static pure model boolean onlyHasPrimeFactors235(int num) {
      @     return num > 1 &&
      @            (\forall int p; p > 1 && p <= num && isPrime(p) && divides(p, num);
      @             p == 2 || p == 3 || p == 5);
      @ }
      @
      @ // Helper specification to check if a number is prime
      @ public static pure model boolean isPrime(int p) {
      @     return p > 1 &&
      @            (\forall int d; d > 1 && d < p; !divides(d, p));
      @ }
      @
      @ // Helper specification to check if a divides b
      @ public static pure model boolean divides(int a, int b) {
      @     return b % a == 0;
      @ }
      @*/
    public boolean isUgly(int n) {
        if (n <= 1) {
            return true;
        } else if (n <= 0) {
            return false;
        }
        int[] factors = new int[] {2, 3, 5};
        for (int factor : factors) {
            while (n > 1 && n % factor == 0) {
                n /= factor;
            }
        }
        return n == 1;
    }
}