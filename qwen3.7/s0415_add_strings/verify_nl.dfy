// s0415 - Add Strings
// Dafny formal specification (spec-only)
method addStrings(num1: string, num2: string) returns (result: string)
{
    // requires(*The length of the string parameter `num1` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The length of the string parameter `num2` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The string parameter `num1` is not equal to the null literal.*);
    // requires(*The string parameter `num2` is not equal to the null literal.*);
    // requires(*All values in the string parameter `num1` are digits.*);
    // requires(*All values in the string parameter `num2` are digits.*);
    // ensures(*The string result is not equal to the null literal.*);
    // ensures(*The string result is equal to the sum of the integer represented by the string parameter `num1` and the integer represented by the string parameter `num2` as a string.*);
    // ensures(*If the string parameter `num1` is equal to "11" and the string parameter `num2` is equal to "123", the string result is equal to "134".*);
    // ensures(*If the string parameter `num1` is equal to "456" and the string parameter `num2` is equal to "77", the string result is equal to "533".*);
    // ensures(*If the string parameter `num1` is equal to "0" and the string parameter `num2` is equal to "0", the string result is equal to "0".*);
    result := "0";
    assume false;
}
