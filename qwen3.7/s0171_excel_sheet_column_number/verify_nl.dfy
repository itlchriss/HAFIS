// s0171 - Excel Sheet Column Number
// Dafny formal specification (spec-only: string parsing via base-26 decoding)

// requires(*The length of the string parameter `columnTitle` is greater than or equal to 1 and is less than or equal to 7.*);
// requires(*The string parameter `columnTitle` consists of only uppercase English letters.*);
// requires(*The string parameter `columnTitle` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 1 and is less than or equal to 2147483647.*);
// ensures(*The integer result is the column number corresponding to the Excel column title represented by the string parameter `columnTitle`.*);
// ensures(*If the string parameter `columnTitle` is equal to "A", the integer result is equal to 1.*);
// ensures(*If the string parameter `columnTitle` is equal to "AB", the integer result is equal to 28.*);
// ensures(*If the string parameter `columnTitle` is equal to "ZY", the integer result is equal to 701.*);
// ensures(*If the string parameter `columnTitle` is equal to "FXSHRXW", the integer result is equal to 2147483647.*);
method titleToNumber(columnTitle: string) returns (result: int)
{
    result := 1;
    assume false;  // spec-only
}
