// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)
// Dafny version of Solution

// requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 2^31 - 1.*);
// ensures(*If the boolean result is equal to true, you can win the game assuming both you and your friend play optimally.*);
// ensures(*If the boolean result is equal to false, your friend will win the game.*);
// ensures(*If the integer parameter `n` is equal to 4, the boolean result is equal to false.*);
// ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to true.*);
// ensures(*If the integer parameter `n` is equal to 2, the boolean result is equal to true.*);
    method canWinNim(n: int) returns (result: bool)
    {
        result := n % 4 != 0;
        return;
    }
