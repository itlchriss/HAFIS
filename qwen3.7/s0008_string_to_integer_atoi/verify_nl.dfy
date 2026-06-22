// s0008 - String to Integer (atoi)
// Dafny formal specification (spec-only: string parsing with overflow)

method myAtoi(str: string) returns (result: int)
{
    // requires(*The length of the string parameter `str` is greater than or equal to 0 and is less than or equal to 200.*);
    // requires(*The string parameter `str` consists of only English letters and digits and the character ` ` and the character `+` and the character `-` and the character `.`.*);
    // requires(*The string parameter `str` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*If the string parameter `str` is equal to "42", the integer result is equal to 42.*);
    // ensures(*If the string parameter `str` is equal to " -42", the integer result is equal to -42.*);
    // ensures(*If the string parameter `str` is equal to "4193 with words", the integer result is equal to 4193.*);
    // ensures(*If the string parameter `str` is equal to "words and 987", the integer result is equal to 0.*);
    // ensures(*If the string parameter `str` is equal to "-91283472332", the integer result is equal to -2147483648.*);
    // ensures(*If no digits are read from the string parameter `str`, the integer result is equal to 0.*);
    // ensures(*If the parsed integer from the string parameter `str` is less than -2147483648, the integer result is equal to -2147483648.*);
    // ensures(*If the parsed integer from the string parameter `str` is greater than 2147483647, the integer result is equal to 2147483647.*);
    result := 0;
    assume false;  // spec-only
}
