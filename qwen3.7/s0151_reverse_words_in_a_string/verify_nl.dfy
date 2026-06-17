// s0151 - Reverse Words in a String
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*The string parameter `s` consists of only English letters and spaces.*);
// requires(*There is at least one word in the string parameter `s`.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The string result contains the words of the string parameter `s` in reverse order separated by a single space.*);
// ensures(*The string result does not contain leading or trailing spaces.*);
// ensures(*If the string parameter `s` is equal to "the sky is blue", the string result is equal to "blue is sky the".*);
// ensures(*If the string parameter `s` is equal to " hello world ", the string result is equal to "world hello".*);
// ensures(*If the string parameter `s` is equal to "a good example", the string result is equal to "example good a".*);
// ensures(*If the string parameter `s` is equal to " Bob Loves Alice ", the string result is equal to "Alice Loves Bob".*);
// ensures(*If the string parameter `s` is equal to "Alice does not even like bob", the string result is equal to "bob like even not does Alice".*);
method reverseWords(s: string) returns (result: string)
{
    result := s;
    assume false;
}
