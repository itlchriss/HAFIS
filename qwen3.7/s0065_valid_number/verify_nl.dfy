// s0065 - Valid Number
// Dafny formal specification (spec-only)

method isNumber(s: string) returns (result: bool)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 20.*);
    // requires(*The string parameter `s` consists of only English letters and digits and the character `+` and the character `-` and the character `.`.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // ensures(*If the string parameter `s` is equal to "0", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `s` is equal to "e", the boolean result is equal to the false literal.*);
    // ensures(*If the string parameter `s` is equal to ".", the boolean result is equal to the false literal.*);
    // ensures(*If the string parameter `s` is equal to ".1", the boolean result is equal to the true literal.*);
    // ensures(*The boolean result is equal to the true literal if and only if the string parameter `s` is a valid number.*);
    result := false;
    assume false;
}
