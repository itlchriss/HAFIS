// s0006 - Zigzag Conversion
// Dafny formal specification (spec-only: string rearrangement)

method convert(s: string, numRows: int) returns (result: string)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 1000.*);
    // requires(*The string parameter `s` consists of only English letters and the character `,` and the character `.`.*);
    // requires(*The integer parameter `numRows` is greater than or equal to 1 and is less than or equal to 1000.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // ensures(*The string result is not equal to the null literal.*);
    // ensures(*The length of the string result is equal to the length of the string parameter `s`.*);
    // ensures(*If the integer parameter `numRows` is equal to 1, the string result is equal to the string parameter `s`.*);
    // ensures(*If the string parameter `s` is equal to "PAYPALISHIRING" and the integer parameter `numRows` is equal to 3, the string result is equal to "PAHNAPLSIIGYIR".*);
    // ensures(*If the string parameter `s` is equal to "PAYPALISHIRING" and the integer parameter `numRows` is equal to 4, the string result is equal to "PINALSIGYAHRPI".*);
    // ensures(*If the string parameter `s` is equal to "A" and the integer parameter `numRows` is equal to 1, the string result is equal to "A".*);
    result := s;
    assume false;  // spec-only
}
