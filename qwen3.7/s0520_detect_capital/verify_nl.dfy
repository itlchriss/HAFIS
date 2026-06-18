// s0520 - Detect Capital
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `word` is greater than or equal to 1 and is less than or equal to 100.*);
// requires(*The string parameter `word` consists of only lowercase and uppercase English letters.*);
// requires(*The string parameter `word` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, all letters in the string parameter `word` are uppercase or all letters in the string parameter `word` are lowercase or only the first letter in the string parameter `word` is uppercase.*);
// ensures(*If the boolean result is equal to the false literal, the usage of capitals in the string parameter `word` is not right.*);
// ensures(*If the string parameter `word` is equal to "USA", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `word` is equal to "FlaG", the boolean result is equal to the false literal.*);
method detectCapitalUse(word: string) returns (result: bool)
{
    result := false;
    assume false;
}
