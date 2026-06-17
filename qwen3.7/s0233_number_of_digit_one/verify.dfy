// s0233 - Number of Digit One
// Dafny formal specification (spec-only)
method countDigitOne(n: int) returns (result: int)
    requires 0 <= n <= 1000000000
    ensures result >= 0
    ensures n == 0 ==> result == 0
{
    result := 0;
    assume false;
}
