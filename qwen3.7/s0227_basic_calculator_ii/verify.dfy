// s0227 - Basic Calculator II
// Dafny formal specification (spec-only)
method calculate(s: string) returns (result: int)
    requires 1 <= |s| <= 300000
    ensures result >= 0 || true  // result can be any int
{
    result := 0;
    assume false;
}
