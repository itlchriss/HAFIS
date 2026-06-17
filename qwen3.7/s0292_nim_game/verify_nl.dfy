// s0292 - Nim Game
// Complete Dafny formal specification with verified implementation

// In the Nim game, two players take turns removing 1 to 3 stones from a pile.
// The player who removes the last stone wins.
// You go first. Both players play optimally.
// You win iff n is not a multiple of 4.

// requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 2147483647.*);
// ensures(*If the boolean result is equal to the true literal, the integer parameter `n` modulo 4 is not equal to 0.*);
// ensures(*If the boolean result is equal to the false literal, the integer parameter `n` modulo 4 is equal to 0.*);
// ensures(*If the integer parameter `n` is equal to 4, the boolean result is equal to the false literal.*);
// ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to the true literal.*);
// ensures(*If the integer parameter `n` is equal to 2, the boolean result is equal to the true literal.*);
method canWinNim(n: int) returns (result: bool)
{
    result := n % 4 != 0;
}
