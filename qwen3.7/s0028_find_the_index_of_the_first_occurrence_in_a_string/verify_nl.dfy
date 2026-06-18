// s0028 - Find the Index of the First Occurrence in a String
// Dafny formal specification (spec-only: string matching)

// requires(*The length of the string parameter `haystack` is greater than or equal to 0 and is less than or equal to 50000.*);
// requires(*The length of the string parameter `needle` is greater than or equal to 0 and is less than or equal to 50000.*);
// requires(*The string parameter `haystack` consists of only lowercase English letters.*);
// requires(*The string parameter `needle` consists of only lowercase English letters.*);
// requires(*The string parameter `haystack` is not equal to the null literal.*);
// requires(*The string parameter `needle` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to -1 and is less than or equal to the length of the string parameter `haystack`.*);
// ensures(*If the integer result is greater than or equal to 0, the substring of the string parameter `haystack` starting at the integer result and having the length equal to the length of the string parameter `needle` is equal to the string parameter `needle`.*);
// ensures(*If the integer result is greater than or equal to 0, for every non-negative integer `j` the integer result is less than the integer result, the substring of the string parameter `haystack` starting at `j` and having the length equal to the length of the string parameter `needle` is not equal to the string parameter `needle`.*);
// ensures(*If the integer result is equal to -1, the string parameter `needle` is not a substring of the string parameter `haystack`.*);
// ensures(*If the string parameter `haystack` is equal to "hello" and the string parameter `needle` is equal to "ll", the integer result is equal to 2.*);
// ensures(*If the string parameter `haystack` is equal to "aaaaa" and the string parameter `needle` is equal to "bba", the integer result is equal to -1.*);
// ensures(*If the string parameter `haystack` is equal to "" and the string parameter `needle` is equal to "", the integer result is equal to 0.*);
method strStr(haystack: string, needle: string) returns (result: int)
{
    result := -1;
    assume false;  // spec-only
}
