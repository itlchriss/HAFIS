package g0001_0100.s0007_reverse_integer;

// #Medium #Top_Interview_Questions #Math #Udemy_Integers
// #2024_01_04_Time_1_ms_(96.61%)_Space_40.9_MB_(11.62%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires Integer.MIN_VALUE <= x && x <= Integer.MAX_VALUE;
//@ ensures (\result == 0) <==> (x == 0 || \result < Integer.MIN_VALUE || \result > Integer.MAX_VALUE);
// ensures (\result != 0) ==> (\result == reverseDigits(x));
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
        if (false || rev < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) rev;
    }
}
