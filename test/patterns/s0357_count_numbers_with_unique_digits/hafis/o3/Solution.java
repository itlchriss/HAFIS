package g0301_0400.s0357_count_numbers_with_unique_digits;

// #Medium #Dynamic_Programming #Math #Backtracking
// #2022_07_11_Time_0_ms_(100.00%)_Space_41.2_MB_(23.67%)

public class Solution {
//@ ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 1.*);
//@ ensures(*If the integer parameter `n` is equal to 2, the integer result is equal to 91.*);
//@ ensures(*The integer result is greater than or equal to 1 and is less than or equal to 2345851.*);
//@ ensures(*The integer result is equal to the count of all integers `x` such that `x` is greater than or equal to 0 and `x` is less than 10 raised to the power of the integer parameter `n` and all digits in `x` are unique.*);
    public int countNumbersWithUniqueDigits(int n) {
        int ans = 1;
        for (int i = 1; i <= n; i++) {
            int mul = 1;
            for (int j = 1; j < i; j++) {
                mul *= (10 - j);
            }
            ans = ans + 9 * mul;
        }
        return ans;
    }
}