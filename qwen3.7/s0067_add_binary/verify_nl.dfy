// s0067 - Add Binary
// Dafny formal specification (spec-only)

// requires(*The length of the string parameter `a` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*The length of the string parameter `b` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*The string parameter `a` consists of only the characters '0' or '1'.*);
// requires(*The string parameter `b` consists of only the characters '0' or '1'.*);
// requires(*The string parameter `a` does not contain leading zeros.*);
// requires(*The string parameter `b` does not contain leading zeros.*);
// requires(*The string parameter `a` is not equal to the null literal.*);
// requires(*The string parameter `b` is not equal to the null literal.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The string result is the binary representation of the sum of the binary number represented by the string parameter `a` and the binary number represented by the string parameter `b`.*);
// ensures(*If the string parameter `a` is equal to "11" and the string parameter `b` is equal to "1", the string result is equal to "100".*);
// ensures(*If the string parameter `a` is equal to "1010" and the string parameter `b` is equal to "1011", the string result is equal to "10101".*);
method addBinary(a: string, b: string) returns (result: string)
{
    result := "0";
    assume false;
}
