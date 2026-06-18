// s0299 - Bulls and Cows
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `secret` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The length of the string parameter `guess` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The length of the string parameter `secret` is equal to the length of the string parameter `guess`.*);
// requires(*The string parameter `secret` consists of only digits.*);
// requires(*The string parameter `guess` consists of only digits.*);
// requires(*The string parameter `secret` is not equal to the null literal.*);
// requires(*The string parameter `guess` is not equal to the null literal.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The string result is in the format "xAyB" where `x` is the number of bulls and `y` is the number of cows.*);
// requires(*A bull is a digit in the string parameter `guess` the string parameter `guess` is in the correct position.*);
// requires(*A cow is a digit in the string parameter `guess` the string parameter `secret` is in the string parameter `secret` but is in the wrong position.*);
// ensures(*If the string parameter `secret` is equal to "1807" and the string parameter `guess` is equal to "7810", the string result is equal to "1A3B".*);
// ensures(*If the string parameter `secret` is equal to "1123" and the string parameter `guess` is equal to "0111", the string result is equal to "1A1B".*);
// ensures(*If the string parameter `secret` is equal to "1" and the string parameter `guess` is equal to "0", the string result is equal to "0A0B".*);
// ensures(*If the string parameter `secret` is equal to "1" and the string parameter `guess` is equal to "1", the string result is equal to "1A0B".*);
method getHint(secret: string, guess: string) returns (result: string)
{
    result := "0A0B";
    assume false;
}
