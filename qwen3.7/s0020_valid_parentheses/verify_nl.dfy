// s0020 - Valid Parentheses
// Dafny formal specification (spec-only: stack-based matching)

// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*The string parameter `s` consists of only the characters '(' or ')' or '[' or ']' or '{' or '}'.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, every open bracket in the string parameter `s` is closed by the same type of bracket and every open bracket is closed in the correct order.*);
// ensures(*If the boolean result is equal to the false literal, there exists at least one open bracket in the string parameter `s` the boolean result is not closed by the same type of bracket or is not closed in the correct order.*);
// ensures(*If the string parameter `s` is equal to "()", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "()[]{}", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "(]", the boolean result is equal to the false literal.*);
// ensures(*If the string parameter `s` is equal to "([)]", the boolean result is equal to the false literal.*);
// ensures(*If the string parameter `s` is equal to "{[]}", the boolean result is equal to the true literal.*);
method isValid(s: string) returns (result: bool)
{
    result := false;
    assume false;  // spec-only
}
