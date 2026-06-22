// s0032 - Longest Valid Parentheses
// Dafny formal specification (spec-only)

method longestValidParentheses(s: string) returns (result: int)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 0 and is less than or equal to 30000.*);
    // requires(*All values in the string parameter `s` are the character `(` or the character `)`.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the string parameter `s`.*);
    // ensures(*If the string parameter `s` is equal to "(()", the integer result is equal to 2.*);
    // ensures(*If the string parameter `s` is equal to ")()())", the integer result is equal to 4.*);
    // ensures(*If the string parameter `s` is equal to "", the integer result is equal to 0.*);
    result := 0;
    assume false;
}
