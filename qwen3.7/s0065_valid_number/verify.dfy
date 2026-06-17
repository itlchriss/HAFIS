// s0065 - Valid Number
// Dafny formal specification (spec-only)

method isNumber(s: string) returns (result: bool)
    requires 1 <= |s| <= 20
{
    result := false;
    assume false;
}
