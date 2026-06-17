// s0008 - String to Integer (atoi)
// Dafny formal specification (spec-only: string parsing with overflow)

method myAtoi(str: string) returns (result: int)
    requires 0 <= |str| <= 200
    ensures -2147483648 <= result <= 2147483647
{
    result := 0;
    assume false;  // spec-only
}
