// s0020 - Valid Parentheses
// Dafny formal specification (spec-only: stack-based matching)

method isValid(s: string) returns (result: bool)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The string parameter `s` consists of only the character `(` and the character `)` and the character `{` and the character `}` and the character `[` and the character `]`.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // ensures(*If the string parameter `s` is equal to "()", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `s` is equal to "()[]{}", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `s` is equal to "(]", the boolean result is equal to the false literal.*);
    // ensures(*If the string parameter `s` is equal to "([)]", the boolean result is equal to the false literal.*);
    // ensures(*If the string parameter `s` is equal to "{[]}", the boolean result is equal to the true literal.*);
    // ensures(*The boolean result is equal to the true literal if and only if every open bracket in the string parameter `s` is closed by the same type of bracket and every open bracket is closed in the correct order.*);
    result := false;
    assume false;  // spec-only
}
