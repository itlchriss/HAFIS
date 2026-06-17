// #Easy #Math #Binary_Search #Binary_Search_I_Day_3
// #2022_07_12_Time_0_ms_(100.00%)_Space_40.9_MB_(49.73%)
// Dafny version of Solution

// requires(*The integer parameter `num` is greater than or equal to 1 and is less than or equal to 2^31 - 1.*);
// requires(*The method does not use any built-in library function such as `sqrt`.*);
// ensures(*If the boolean result is true, the integer parameter `num` is a perfect square.*);
// ensures(*If the boolean result is false, the integer parameter `num` is not a perfect square.*);
    method isPerfectSquare(num: int) returns (result: bool)
    {
        if num == 0 {
            // If num is 0 return false
            result := false;
            return;
        }
        // long datatype can holds huge number.
        var start: int := 0;
        var end: int := num;
        var mid: int;
        while start <= end
            invariant true
        {
            // until start is lesser or equal to end do this
            // Finding middle value
            mid := start + (end - start) / 2;
            if mid * mid == num {
                // if mid*mid == num return true
                result := true;
                return;
            } else if mid * mid < num {
                // if num is greater than mid*mid then make start = mid + 1
                start := mid + 1;
            } else if mid * mid > num {
                // if num is lesser than mid*mid then make end = mid - 1
                end := mid - 1;
            }
        }
        result := false;
        return;
    }
