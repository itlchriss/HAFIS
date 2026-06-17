// s0521 - Longest Uncommon Subsequence I
// Complete Dafny formal specification with verified implementation

// Given two strings a and b, return the length of the longest uncommon subsequence.
// An uncommon subsequence is a string that is a subsequence of one but not the other.
// If no such subsequence exists, return -1.

method findLUSlength(a: string, b: string) returns (result: int)
    requires 1 <= |a| <= 100
    requires 1 <= |b| <= 100
    ensures a == b ==> result == -1
    ensures a != b ==> result == (if |a| >= |b| then |a| else |b|)
    ensures result == -1 || result == |a| || result == |b|
{
    if a == b {
        result := -1;
    } else {
        result := if |a| >= |b| then |a| else |b|;
    }
}
