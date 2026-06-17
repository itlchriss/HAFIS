// #Medium #Dynamic_Programming #Math #Backtracking
// #2022_07_11_Time_0_ms_(100.00%)_Space_41.2_MB_(23.67%)
// Dafny version of Solution

// requires(*The integer parameter `n` is greater than or equal to 0 and is less than or equal to 8.*);
// ensures(*The integer result is equal to the count of all numbers with unique digits in the range of 0 to 10^n, excluding numbers with repeated digits.*);
// ensures(*If the integer parameter `n` is equal to 2, the integer result is equal to 91.*);
// ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 1.*);
    method countNumbersWithUniqueDigits(n: int) returns (result: int)
    {
        var ans: int := 1;
        for i := 1 to n + 1
            invariant i >= 1
            invariant i <= n + 1
        {
            var mul: int := 1;
            for j := 1 to i
                invariant j >= 1
                invariant j <= i
            {
                mul := mul * (10 - j);
            }
            ans := ans + 9 * mul;
        }
        result := ans;
        return;
    }
