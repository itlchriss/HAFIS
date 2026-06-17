// s0292 - Nim Game
// Complete Dafny formal specification with verified implementation

// In the Nim game, two players take turns removing 1 to 3 stones from a pile.
// The player who removes the last stone wins.
// You go first. Both players play optimally.
// You win iff n is not a multiple of 4.

method canWinNim(n: int) returns (result: bool)
    requires 1 <= n <= 2147483647
    ensures result <==> n % 4 != 0
{
    result := n % 4 != 0;
}
