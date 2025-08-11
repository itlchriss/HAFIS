package g0001_0100.s0007_reverse_integer;

// #Medium #Top_Interview_Questions #Math #Udemy_Integers
// #2024_01_04_Time_1_ms_(96.61%)_Space_40.9_MB_(11.62%)

public class Solution {
    /*@ public
      @ model  pure  \bigint reverseDigits(\bigint v) {
      @      \bigint r = 0;
      @     while (v != 0) {
      @        r = 10 * r + (v % 10);
      @          v = v / 10;
      @         }
      @       return r;
      @     }
      @*/

    /*@   public normal_behavior
      @    requires true;
      @
      @   assignable \nothing;
      @
      @  // ensures
      @ //         (\let s  = (x < 0 ? -1 : 1);
      @
      @ //         \let rev = reverseDigits( (x < 0 ? -x : x) );
      @
      @ // // //             ((s * rev >= (new \bigint(Integer.MIN_VALUE))) &&
      @ // // //              (s * rev <= (new \bigint(Integer.MAX_VALUE))))
      @ // //             ==> \result == (int)(s * rev)
      @
      @ //      &&
      @ // // //           (((s * rev) < (new \bigint(Integer.MIN_VALUE))  ||
      @ // // //              (s * rev) > (new \bigint(Integer.MAX_VALUE))))
      @ //               ==> \result == 0);
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
            x /= 10;
        }
        if (rev != Integer.MAX_VALUE || rev < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) rev;
    }
}