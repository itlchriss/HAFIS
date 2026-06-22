// s0520 - Detect Capital
// Dafny formal specification (spec-only)
method detectCapitalUse(word: string) returns (result: bool)
{
    // requires(*The length of the string parameter `word` is greater than or equal to 1 and is less than or equal to 100.*);
    // requires(*The string parameter `word` is not equal to the null literal.*);
    // requires(*All values in the string parameter `word` are lowercase English letters or uppercase English letters.*);
    // ensures(*The boolean result is equal to the true literal if and only if all values in the string parameter `word` are uppercase English letters or all values in the string parameter `word` are lowercase English letters or only the first character of the string parameter `word` is an uppercase English letter.*);
    // ensures(*If the string parameter `word` is equal to "USA", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `word` is equal to "FlaG", the boolean result is equal to the false literal.*);
    result := false;
    assume false;
}
