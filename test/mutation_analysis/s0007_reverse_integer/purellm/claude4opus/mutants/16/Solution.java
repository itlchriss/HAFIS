package g0001_0100.s0007_reverse_integer;

// #Medium #Top_Interview_Questions #Math #Udemy_Integers
// #2024_01_04_Time_1_ms_(96.61%)_Space_40.9_MB_(11.62%)

public class Solution {
    /*@ 
      @ assignable \nothing;
      @
      @ model pure helper long reverseDigits(int x) {
      @     return computeReverse(x);
      @ }
      @*/

    /*@ private normal_behavior
      @ ensures x == 0 ==> \result == 0;
      @ ensures x > 0 ==> \result == reversePositive(x);
      @ ensures x < 0 ==> \result == -reversePositive(-x);
      @ assignable \nothing;
      @
      @ model pure helper long computeReverse(int x) {
      @     if (x == 0) return 0;
      @     if (x > 0) return reversePositive(x);
      @     return -reversePositive(-x);
      @ }
      @*/

    /*@ private normal_behavior
      @ requires x > 0;
      @ ensures \result >= 0;
      @ ensures x < 10 ==> \result == x;
      @ ensures x >= 10 ==>
      @     \result == (x % 10) * pow10(countDigits(x) - 1) +
      @                reversePositive(x / 10);
      @ measured_by x;
      @ assignable \nothing;
      @
      @ model pure helper long reversePositive(int x) {
      @     if (x < 10) return x;
      @     int lastDigit = x % 10;
      @     int remaining = x / 10;
      @     int digitCount = countDigits(x);
      @     return lastDigit * pow10(digitCount - 1) + reversePositive(remaining);
      @ }
      @*/

    /*@ private normal_behavior
      @ requires x > 0;
      @ ensures \result >= 1;
      @ ensures x < 10 ==> \result == 1;
      @ ensures x >= 10 ==> \result == 1 + countDigits(x / 10);
      @ measured_by x;
      @ assignable \nothing;
      @
      @ model pure helper int countDigits(int x) {
      @     if (x < 10) return 1;
      @     return 1 + countDigits(x / 10);
      @ }
      @*/

    /*@ private normal_behavior
      @ requires n >= 0;
      @ ensures n == 0 ==> \result == 1;
      @ ensures n > 0 ==> \result == 10 * pow10(n - 1);
      @ measured_by n;
      @ assignable \nothing;
      @
      @ model pure helper long pow10(int n) {
      @     if (n == 0) return 1;
      @     return 10 * pow10(n - 1);
      @ }
      @*/

    /*@ public normal_behavior
      @ requires Integer.MIN_VALUE <= x && x <= Integer.MAX_VALUE;
      @ assignable \nothing;
      @*/
    public int reverse(int x) {
        long rev = 0;
        //@ ghost long k;
        //@ ghost int m;
        //@ set k = rev;
        //@ set m = x;
        while (x != 0) {
            //@ set k = rev;
            //@ set m = x;
            // assume (Long.MIN_VALUE)/10 + m <= k <= (Long.MAX_VALUE)/10 - m;
            // assume ((Long.MIN_VALUE) + ( m % 10 ))/10 <= k <= ((Long.MAX_VALUE) - ( m % 10 )) / 10;
            rev = (rev * 10) + (x % 10);
            //@ set k = rev;
            //@ set m = m/10;

        }
        if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) rev;
    }
}