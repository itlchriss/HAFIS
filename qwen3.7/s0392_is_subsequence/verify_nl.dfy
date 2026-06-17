// s0392 - Is Subsequence
// Dafny formal specification (spec-only: subsequence checking)

// requires(*The length of the string parameter `s` is greater than or equal to 0 and is less than or equal to 100.*);
// requires(*The length of the string parameter `t` is greater than or equal to 0 and is less than or equal to 10000.*);
// requires(*The string parameter `s` consists of only lowercase English letters.*);
// requires(*The string parameter `t` consists of only lowercase English letters.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// requires(*The string parameter `t` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, the string parameter `s` is a subsequence of the string parameter `t`.*);
// ensures(*If the boolean result is equal to the false literal, the string parameter `s` is not a subsequence of the string parameter `t`.*);
// ensures(*If the string parameter `s` is equal to "abc" and the string parameter `t` is equal to "ahbgdc", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "axc" and the string parameter `t` is equal to "ahbgdc", the boolean result is equal to the false literal.*);
method isSubsequence(s: string, t: string) returns (result: bool)
{
    result := false;
    assume false;  // spec-only
}
