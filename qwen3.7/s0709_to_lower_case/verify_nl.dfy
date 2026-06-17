// s0709 - To Lower Case
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 100.*);
// requires(*The string parameter `s` consists of only printable ASCII characters.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The length of the string result is equal to the length of the string parameter `s`.*);
// ensures(*For every non-negative integer `i` that is less than the length of the string parameter `s`, if the character at index `i` of the string parameter `s` is an uppercase letter, the character at index `i` of the string result is the corresponding lowercase letter.*);
// ensures(*For every non-negative integer `i` that is less than the length of the string parameter `s`, if the character at index `i` of the string parameter `s` is not an uppercase letter, the character at index `i` of the string result is equal to the character at index `i` of the string parameter `s`.*);
// ensures(*If the string parameter `s` is equal to "Hello", the string result is equal to "hello".*);
// ensures(*If the string parameter `s` is equal to "here", the string result is equal to "here".*);
// ensures(*If the string parameter `s` is equal to "LOVELY", the string result is equal to "lovely".*);
method toLowerCase(s: string) returns (result: string)
{
    result := s;
    assume false;
}
