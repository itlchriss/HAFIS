// s0202 - Happy Number
// Dafny formal specification (spec-only)

method isHappy(n: int) returns (result: bool)
    requires 1 <= n <= 2147483647
    ensures n == 1 ==> result
    ensures n == 19 ==> result
    ensures n == 2 ==> !result
{
    result := false;
    assume false;
}
