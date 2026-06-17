// #Easy #Top_Interview_Questions #Math #Recursion
// #2022_07_09_Time_18_ms_(85.35%)_Space_47.9_MB_(14.68%)
// Dafny version of Solution

// requires(*The integer parameter `n` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*If the boolean result is equal to true, there exists an integer `x` such that `n` is equal to 3 raised to the power of `x`.*);
// ensures(*If the boolean result is equal to false, there does not exist an integer `x` such that `n` is equal to 3 raised to the power of `x`.*);
    method isPowerOfThree(n: int) returns (result: bool)
    {
        if n < 3 && n != 1 {
            result := false;
            return;
        }
        while n != 1
            invariant true
        {
            if n % 3 != 0 {
                result := false;
                return;
            }
            n := n / 3;
        }
        result := true;
        return;
    }
