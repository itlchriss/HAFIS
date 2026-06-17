// s0709 - To Lower Case
// Dafny formal specification (spec-only)
method toLowerCase(s: string) returns (result: string)
    requires 1 <= |s| <= 100
    ensures |result| == |s|
{
    result := s;
    assume false;
}
