// s0008 - String to Integer (atoi)
// Dafny formal specification (spec-only: string parsing with overflow)

// requires(*The length of the string parameter `s` is greater than or equal to 0 and is less than or equal to 200.*);
// requires(*The string parameter `s` consists of only English letters and digits and the characters ' ' or '+' or '-' or '.'.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*The integer result is the 32-bit signed integer parsed from the string parameter `s` following the atoi algorithm.*);
// ensures(*If the string parameter `s` is equal to "42", the integer result is equal to 42.*);
// ensures(*If the string parameter `s` is equal to " -42", the integer result is equal to -42.*);
// ensures(*If the string parameter `s` is equal to "4193 with words", the integer result is equal to 4193.*);
// ensures(*If the string parameter `s` is equal to "words and 987", the integer result is equal to 0.*);
// ensures(*If the string parameter `s` is equal to "-91283472332", the integer result is equal to -2147483648.*);
method myAtoi(str: string) returns (result: int)
{
    result := 0;
    assume false;  // spec-only
}
