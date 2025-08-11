package g0301_0400.s0367_valid_perfect_square;

// #Easy #Math #Binary_Search #Binary_Search_I_Day_3
// #2022_07_12_Time_0_ms_(100.00%)_Space_40.9_MB_(49.73%)

public class Solution {
    /*@
      @ public normal_behavior
      @   // 1.  Input domain (problem constraint)
      @   requires 1 <= num && num <= 2147483647;           // 2^31 − 1
      @
      @   // 2.  Purity / frame condition (no state is changed)
      @   assignable \nothing;
      @
      @   // 3.  Postcondition:  result is true  ⇔  num is k² for some k ≥ 1
      @   //     A tighter upper bound 46340 (= floor(sqrt(2^31−1))) is used
      @   //     to guarantee that the multiplication k*k never overflows.
      @   ensures (\result  <==>  (\exists int k;
      @                             1 <= k && k <= 46340;
      @                             k * k == num));
      @
      @   // 4.  No exceptional behavior is allowed for legal inputs
      @*/
    public boolean isPerfectSquare(int num) {
        if (num == 0) {
            // If num is 0 return false
            return false;
        }
        // long datatype can holds huge number.
        long start = 0;
        long end = num;
        long mid;
        while (start <= end) {
            // until start is lesser or equal to end do this
            // Finding middle value
            mid = start + (end - start) / 2;
            if (mid * mid == num) {
                // if mid*mid == num return true
                return true;
            } else if (mid * mid < num) {
                // if num is greater than mid*mid then make start = mid + 1
                start = mid + 1;
            } else if (mid * mid >= num) {
                // if num is lesser than mid*mid then make end = mid - 1
                end = mid - 1;
            }
        }
        return false;
    }
}