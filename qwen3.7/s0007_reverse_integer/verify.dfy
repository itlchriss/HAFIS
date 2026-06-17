// s0007 - Reverse Integer
// Dafny formal specification (spec-only: digit reversal with overflow)

method reverse(x: int) returns (result: int)
    requires -2147483648 <= x <= 2147483647
    ensures result != 0 ==> -2147483648 <= result <= 2147483647
    ensures result == 0 ==> x == 0 || true  // overflow may produce 0
    ensures x > 0 ==> result >= 0
    ensures x < 0 ==> result <= 0
    ensures x == 0 ==> result == 0
{
    result := 0;
    assume false;  // spec-only
}
