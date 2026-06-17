// #Easy #Math #Game_Theory #Brainteaser #2022_07_06_Time_0_ms_(100.00%)_Space_40.6_MB_(71.00%)
// Dafny version of Solution

requires (n >= 1) && (n <= 2147483647)
// ensures(*If the boolean result is equal to true, you can win the game assuming both you and your friend play optimally.*);
// ensures(*If the boolean result is equal to false, your friend will win the game.*);
ensures (n == 4) ==> (\result == false)
ensures (n == 1) ==> (\result == true)
ensures (n == 2) ==> (\result == true)
    method canWinNim(n: int) returns (result: bool)
    {
        result := n % 4 != 0;
        return;
    }
