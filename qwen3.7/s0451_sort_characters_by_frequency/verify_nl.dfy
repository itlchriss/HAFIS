// s0451 - Sort Characters By Frequency
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 500000.*);
// requires(*The string parameter `s` consists of only uppercase and lowercase English letters and digits.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The string result is not equal to the null literal.*);
// ensures(*The length of the string result is equal to the length of the string parameter `s`.*);
// ensures(*The string result is sorted in decreasing order based on the frequency of the characters.*);
// ensures(*If the string parameter `s` is equal to "tree", the string result is equal to "eert".*);
// ensures(*If the string parameter `s` is equal to "cccaaa", the string result is equal to "aaaccc".*);
// ensures(*If the string parameter `s` is equal to "Aabb", the string result is equal to "bbAa".*);
method frequencySort(s: string) returns (result: string)
{
    result := s;
    assume false;
}
