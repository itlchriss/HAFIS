// s0044 - Wildcard Matching
// Dafny formal specification (spec-only)

method isMatch(s: string, p: string) returns (result: bool)
{
    // requires(*The length of the string parameter `inputString` is greater than or equal to 0 and is less than or equal to 2000.*);
    // requires(*The length of the string parameter `pattern` is greater than or equal to 0 and is less than or equal to 2000.*);
    // requires(*The string parameter `inputString` consists of only lowercase English letters.*);
    // requires(*The string parameter `pattern` consists of only lowercase English letters and the character `?` and the character `*`.*);
    // requires(*The string parameter `inputString` is not equal to the null literal.*);
    // requires(*The string parameter `pattern` is not equal to the null literal.*);
    // ensures(*If the string parameter `inputString` is equal to "aa" and the string parameter `pattern` is equal to "a", the boolean result is equal to the false literal.*);
    // ensures(*If the string parameter `inputString` is equal to "aa" and the string parameter `pattern` is equal to "*", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `inputString` is equal to "cb" and the string parameter `pattern` is equal to "?a", the boolean result is equal to the false literal.*);
    // ensures(*If the string parameter `inputString` is equal to "adceb" and the string parameter `pattern` is equal to "*a*b", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `inputString` is equal to "acdcb" and the string parameter `pattern` is equal to "a*c?b", the boolean result is equal to the false literal.*);
    // ensures(*The boolean result is equal to the true literal if and only if the string parameter `inputString` matches the string parameter `pattern` where the character `?` matches any single character and the character `*` matches any sequence of characters.*);
    result := false;
    assume false;
}
