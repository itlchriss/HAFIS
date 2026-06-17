// #Medium #Math #Recursion #2022_07_13_Time_4_ms_(81.16%)_Space_42_MB_(71.14%)
// Dafny version of Solution

// requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 1000000000.*);
// ensures(*The integer result is equal to the last number that remains in the integer array `arr` after applying the elimination algorithm.*);
// ensures(*If the integer parameter `n` is equal to 9, the integer result is equal to 6.*);
// ensures(*If the integer parameter `n` is equal to 1, the integer result is equal to 1.*);
    method lastRemaining(n: int) returns (result: int)
    {
        result := if n == 1 then 1 else 2 * (n / 2 - lastRemaining(n / 2) + 1);
        return;
    }
