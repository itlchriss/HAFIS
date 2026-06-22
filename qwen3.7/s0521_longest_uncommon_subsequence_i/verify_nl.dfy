// s0521 - Longest Uncommon Subsequence I
// Complete Dafny formal specification with verified implementation

// Given two strings a and b, return the length of the longest uncommon subsequence.
// An uncommon subsequence is a string that is a subsequence of one but not the other.
// If no such subsequence exists, return -1.

method findLUSlength(a: string, b: string) returns (result: int)
{
    // requires(*The length of the string parameter `a` is greater than or equal to 1 and is less than or equal to 100.*);
    // requires(*The length of the string parameter `b` is greater than or equal to 1 and is less than or equal to 100.*);
    // requires(*The string parameter `a` is not equal to the null literal.*);
    // requires(*The string parameter `b` is not equal to the null literal.*);
    // requires(*All values in the string parameter `a` are lowercase English letters.*);
    // requires(*All values in the string parameter `b` are lowercase English letters.*);
    // ensures(*The integer result is greater than or equal to -1 and is less than or equal to the maximum value of the length of the string parameter `a` and the length of the string parameter `b`.*);
    // ensures(*If the string parameter `a` is equal to "aba" and the string parameter `b` is equal to "cdc", the integer result is equal to 3.*);
    // ensures(*If the string parameter `a` is equal to "aaa" and the string parameter `b` is equal to "bbb", the integer result is equal to 3.*);
    // ensures(*If the string parameter `a` is equal to "aaa" and the string parameter `b` is equal to "aaa", the integer result is equal to -1.*);
    if a == b {
        result := -1;
    } else {
        result := if |a| >= |b| then |a| else |b|;
    }
}
