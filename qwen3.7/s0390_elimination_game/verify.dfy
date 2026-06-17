// s0390 - Elimination Game
// Dafny formal specification (spec-only)
method lastRemaining(n: int) returns (result: int)
    requires 1 <= n <= 1000000000
    ensures 1 <= result <= n
    ensures n == 1 ==> result == 1
{
    result := 1;
    assume false;
}
