// s1021 - Remove Outermost Parentheses
// Dafny formal specification (spec-only)
method removeOuterParentheses(s: string) returns (result: string)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // requires(*All values in the string parameter `s` are the character '(' or the character ')'.*);
    // requires(*The string parameter `s` is a valid parentheses string.*);
    // ensures(*The string result is not equal to the null literal.*);
    // ensures(*The length of the string result is less than the length of the string parameter `s`.*);
    // ensures(*If the string parameter `s` is equal to "(()())(())", the string result is equal to "()()()".*);
    // ensures(*If the string parameter `s` is equal to "(()())(())(()(()))", the string result is equal to "()()()()(())".*);
    // ensures(*If the string parameter `s` is equal to "()()", the string result is equal to "".*);
    result := "";
    assume false;
}
