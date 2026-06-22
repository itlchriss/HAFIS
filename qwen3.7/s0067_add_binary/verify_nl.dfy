// s0067 - Add Binary
// Dafny formal specification (spec-only)

method addBinary(a: string, b: string) returns (result: string)
{
    // requires(*The length of the string parameter `a` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The length of the string parameter `b` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The string parameter `a` consists of only the character `0` and the character `1`.*);
    // requires(*The string parameter `b` consists of only the character `0` and the character `1`.*);
    // requires(*The string parameter `a` is not equal to the null literal.*);
    // requires(*The string parameter `b` is not equal to the null literal.*);
    // ensures(*The string result is not equal to the null literal.*);
    // ensures(*The string result is the binary sum of the string parameter `a` and the string parameter `b`.*);
    // ensures(*If the string parameter `a` is equal to "11" and the string parameter `b` is equal to "1", the string result is equal to "100".*);
    // ensures(*If the string parameter `a` is equal to "1010" and the string parameter `b` is equal to "1011", the string result is equal to "10101".*);
    result := "0";
    assume false;
}
