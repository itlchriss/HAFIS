// s0044 - Wildcard Matching
// Dafny formal specification (spec-only)

// requires(*The length of the string parameter `s` is greater than or equal to 0 and is less than or equal to 2000.*);
// requires(*The length of the string parameter `p` is greater than or equal to 0 and is less than or equal to 2000.*);
// requires(*The string parameter `s` consists of only lowercase English letters.*);
// requires(*The string parameter `p` consists of only lowercase English letters or the characters '?' or '*'.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// requires(*The string parameter `p` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, the string parameter `p` matches the entire string parameter `s` under the rules the boolean result the character '?' matches any single character and the character '*' matches any sequence of characters including the empty sequence.*);
// ensures(*If the boolean result is equal to the false literal, the string parameter `p` does not match the entire string parameter `s` under the rules the boolean result the character '?' matches any single character and the character '*' matches any sequence of characters including the empty sequence.*);
// ensures(*If the string parameter `s` is equal to "aa" and the string parameter `p` is equal to "a", the boolean result is equal to the false literal.*);
// ensures(*If the string parameter `s` is equal to "aa" and the string parameter `p` is equal to "*", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "cb" and the string parameter `p` is equal to "?a", the boolean result is equal to the false literal.*);
// ensures(*If the string parameter `s` is equal to "adceb" and the string parameter `p` is equal to "*a*b", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "acdcb" and the string parameter `p` is equal to "a*c?b", the boolean result is equal to the false literal.*);
method isMatch(s: string, p: string) returns (result: bool)
{
    result := false;
    assume false;
}
