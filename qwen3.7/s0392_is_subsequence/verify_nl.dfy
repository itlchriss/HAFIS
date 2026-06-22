// s0392 - Is Subsequence
// Dafny formal specification (spec-only: subsequence checking)

method isSubsequence(s: string, t: string) returns (result: bool)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 0 and is less than or equal to 100.*);
    // requires(*The length of the string parameter `t` is greater than or equal to 0 and is less than or equal to 10000.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // requires(*The string parameter `t` is not equal to the null literal.*);
    // requires(*All values in the string parameter `s` are lowercase English letters.*);
    // requires(*All values in the string parameter `t` are lowercase English letters.*);
    // ensures(*The boolean result is equal to the true literal if and only if there exist a non-negative integer sequence `indices` such that the length of the non-negative integer sequence `indices` is equal to the length of the string parameter `s` and for every non-negative integer `i` such that `i` is less than the length of the string parameter `s`, the value at index `i` of the non-negative integer sequence `indices` is less than the length of the string parameter `t` and the character at index the value at index `i` of the non-negative integer sequence `indices` of the string parameter `t` is equal to the character at index `i` of the string parameter `s` and for every non-negative integer `i` such that `i` is less than the difference between the length of the string parameter `s` and 1, the value at index `i` of the non-negative integer sequence `indices` is less than the value at index the sum of `i` and 1 of the non-negative integer sequence `indices`.*);
    // ensures(*If the string parameter `s` is equal to "abc" and the string parameter `t` is equal to "ahbgdc", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `s` is equal to "axc" and the string parameter `t` is equal to "ahbgdc", the boolean result is equal to the false literal.*);
    result := false;
    assume false;  // spec-only
}
