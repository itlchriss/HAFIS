// s0032 - Longest Valid Parentheses
// Dafny formal specification (spec-only)

// requires(*The length of the string parameter `s` is greater than or equal to 0 and is less than or equal to 30000.*);
// requires(*The string parameter `s` consists of only the characters '(' or ')'.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the string parameter `s`.*);
// ensures(*The integer result is equal to the length of the longest contiguous substring of the string parameter `s` The integer result is a valid parentheses string.*);
// ensures(*If the string parameter `s` is equal to "(()", the integer result is equal to 2.*);
// ensures(*If the string parameter `s` is equal to ")()())", the integer result is equal to 4.*);
// ensures(*If the string parameter `s` is equal to "", the integer result is equal to 0.*);
method longestValidParentheses(s: string) returns (result: int)
{
    result := 0;
    assume false;
}
