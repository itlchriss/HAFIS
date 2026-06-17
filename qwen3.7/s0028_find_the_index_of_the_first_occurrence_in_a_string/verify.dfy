// s0028 - Find the Index of the First Occurrence in a String
// Dafny formal specification (spec-only: string matching)

method strStr(haystack: string, needle: string) returns (result: int)
    requires 0 <= |haystack| <= 50000
    requires 1 <= |needle| <= 50000
    ensures -1 <= result
    ensures result >= 0 ==> result + |needle| <= |haystack|
    // If result >= 0, needle occurs at that position
    ensures result >= 0 ==> haystack[result..result + |needle|] == needle
    // If result >= 0, no earlier occurrence exists
    ensures result >= 0 ==> (forall start: nat :: start < result ==>
        start + |needle| <= |haystack| ==> haystack[start..start + |needle|] != needle)
    // If result == -1, needle doesn't occur in haystack
    ensures result == -1 ==> (forall start: nat :: start + |needle| <= |haystack| ==>
        haystack[start..start + |needle|] != needle)
{
    result := -1;
    assume false;  // spec-only
}
