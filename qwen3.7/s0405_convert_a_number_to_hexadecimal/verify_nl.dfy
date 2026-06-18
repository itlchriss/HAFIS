// s0405 - Convert a Number to Hexadecimal
// Dafny formal specification (spec-only: hex conversion)

// requires(*The integer parameter `num` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The string result is the hexadecimal representation of the integer parameter `num`.*);
// requires(*For negative integers, the two's complement method is used.*);
// ensures(*The string result does not contain leading zeros except for the zero the int parameter `num`.*);
// ensures(*All letters in the string result are lowercase.*);
// ensures(*If the integer parameter `num` is equal to 26, the string result is equal to "1a".*);
// ensures(*If the integer parameter `num` is equal to -1, the string result is equal to "ffffffff".*);
method toHex(num: int) returns (result: string)
{
    result := "0";
    assume false;  // spec-only
}
