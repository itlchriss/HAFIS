// s0065 - Valid Number
// Dafny formal specification (spec-only)

// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 20.*);
// requires(*The string parameter `s` consists of only English letters or digits or the characters '+' or '-' or '.'.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, the string parameter `s` is a valid number that can be composed of an optional sign followed by a decimal number or an integer optionally followed by an exponent part consisting of 'e' or 'E' and an integer.*);
// ensures(*If the boolean result is equal to the false literal, the string parameter `s` is not a valid number.*);
// ensures(*If the string parameter `s` is equal to "0", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "e", the boolean result is equal to the false literal.*);
// ensures(*If the string parameter `s` is equal to ".", the boolean result is equal to the false literal.*);
// ensures(*If the string parameter `s` is equal to ".1", the boolean result is equal to the true literal.*);
method isNumber(s: string) returns (result: bool)
{
    result := false;
    assume false;
}
