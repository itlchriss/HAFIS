// s0058 - Length of Last Word
// Dafny formal specification (spec-only)

method lengthOfLastWord(s: string) returns (result: int)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The string parameter `s` consists of only English letters and the character ` `.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to 1 and is less than or equal to the length of the string parameter `s`.*);
    // ensures(*If the string parameter `s` is equal to "Hello World", the integer result is equal to 5.*);
    // ensures(*If the string parameter `s` is equal to " fly me to the moon ", the integer result is equal to 4.*);
    // ensures(*If the string parameter `s` is equal to "luffy is still joyboy", the integer result is equal to 6.*);
    assume {:axiom} false;
}
