// s0006 - Zigzag Conversion
// Dafny formal specification (spec-only: string rearrangement)

method convert(s: string, numRows: int) returns (result: string)
    requires 1 <= |s| <= 1000
    requires 1 <= numRows <= 1000
    ensures |result| == |s|
    ensures numRows == 1 ==> result == s
{
    result := s;
    assume false;  // spec-only
}
