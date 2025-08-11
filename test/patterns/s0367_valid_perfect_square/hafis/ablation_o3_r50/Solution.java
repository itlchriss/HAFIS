package g0301_0400.s0367_valid_perfect_square;

// #Easy #Math #Binary_Search #Binary_Search_I_Day_3
// #2022_07_12_Time_0_ms_(100.00%)_Space_40.9_MB_(49.73%)

public class Solution {
//@ ensures(*If boolean result is equal to the true literal, there exists an integer value k such that the integer parameter `num` is equal to the product between k and k.*);
//@ ensures(*If boolean result is equal to the false literal, for all integer values k the integer parameter `num` is not equal to the product between k and k.*);
//@ ensures(*If the integer parameter `num` is equal to 16, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `num` is equal to 14, the boolean result is equal to the false literal.*);
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
            } else if (mid * mid > num) {
                // if num is lesser than mid*mid then make end = mid - 1
                end = mid - 1;
            }
        }
        return false;
    }
}