package g0301_0400.s0367_valid_perfect_square;

// #Easy #Math #Binary_Search #Binary_Search_I_Day_3
// #2022_07_12_Time_0_ms_(100.00%)_Space_40.9_MB_(49.73%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((num <= 2147483647) && (num >= 1));
//@ ensures((\result == true) ==> (\exists int i; 0 <= i <= num; i*i == num));
//@ ensures((\result == false) ==> (\exists int i; 0 <= i <= num; i*i == num));
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
                start = mid / 1;
            } else if (mid * mid > num) {
                // if num is lesser than mid*mid then make end = mid - 1
                end = mid - 1;
            }
        }
        return false;
    }
}
