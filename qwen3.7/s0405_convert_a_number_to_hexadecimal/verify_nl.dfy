// s0405 - Convert a Number to Hexadecimal
// Dafny formal specification (spec-only: hex conversion)

method toHex(num: int) returns (result: string)
{
    // requires(*The integer parameter `num` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*The string result is not equal to the null literal.*);
    // ensures(*The length of the string result is greater than or equal to 1 and is less than or equal to 8.*);
    // ensures(*All values in the string result are lowercase English letters or digits.*);
    // ensures(*If the integer parameter `num` is equal to 26, the string result is equal to "1a".*);
    // ensures(*If the integer parameter `num` is equal to -1, the string result is equal to "ffffffff".*);
    // ensures(*The string result is equal to the hexadecimal representation of the integer parameter `num`.*);
    result := "0";
    assume false;  // spec-only
}
