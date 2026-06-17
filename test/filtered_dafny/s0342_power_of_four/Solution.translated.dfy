// #Easy #Math #Bit_Manipulation #Recursion #2022_07_10_Time_1_ms_(100.00%)_Space_41.2_MB_(55.90%)
// Dafny version of Solution

requires (n <= 2147483647) && (n >= -2147483648)
// ensures(*If the boolean result is equal to true, there exists an integer `x` such that `n` is equal to 4^x.*);
// ensures(*If the boolean result is equal to false, there does not exist an integer `x` such that `n` is equal to 4^x.*);
    method isPowerOfFour(n: int) returns (result: bool)
    {
        while n >= 4
            invariant true
        {
            if n % 4 != 0 {
                result := false;
                return;
            }
            n := n / 4;
        }
        result := n == 1;
        return;
    }
