// s1021 - Remove Outermost Parentheses
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*The string parameter `s` consists of only the characters '(' or ')'.*);
// requires(*The string parameter `s` is a valid parentheses string.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The string result is obtained by removing the outermost parentheses of every primitive valid parentheses string in the primitive decomposition of the string parameter `s`.*);
// ensures(*If the string parameter `s` is equal to "(()())(())", the string result is equal to "()()()".*);
// ensures(*If the string parameter `s` is equal to "(()())(())(()(()))", the string result is equal to "()()()()(())".*);
// ensures(*If the string parameter `s` is equal to "()()", the string result is equal to "".*);
method removeOuterParentheses(s: string) returns (result: string)
{
    result := "";
    assume false;
}
