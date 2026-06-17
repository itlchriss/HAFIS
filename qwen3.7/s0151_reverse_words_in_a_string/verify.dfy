// s0151 - Reverse Words in a String
// Dafny formal specification (spec-only)
method reverseWords(s: string) returns (result: string)
    requires 1 <= |s| <= 10000
    ensures |result| >= 1
{
    result := s;
    assume false;
}
