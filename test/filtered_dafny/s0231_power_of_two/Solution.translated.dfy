// #Easy #Math #Bit_Manipulation #Recursion #Algorithm_I_Day_13_Bit_Manipulation
// #2022_07_04_Time_1_ms_(100.00%)_Space_39.6_MB_(90.19%)
// Dafny version of Solution

requires (n <= 2147483647) && (n >= -2147483648)
// ensures(*If the boolean result is equal to true, there exists an integer `x` such that n is equal to 2^x.*);
// ensures(*If the boolean result is equal to false, there does not exist an integer `x` such that n is equal to 2^x.*);
    method isPowerOfTwo(n: int) returns (result: bool)
    {
        if n <= 0 {
            result := false;
            return;
        }
        while true
            invariant true
        {
            if n == 1 {
                result := true;
                return;
            }
            if n % 2 == 1 {
                result := false;
                return;
            }
            n := n / 2;
        }
    }
