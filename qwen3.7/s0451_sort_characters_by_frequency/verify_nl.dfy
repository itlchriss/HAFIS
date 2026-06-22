// s0451 - Sort Characters By Frequency
// Dafny formal specification (spec-only)
method frequencySort(s: string) returns (result: string)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 500000.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // requires(*All values in the string parameter `s` are uppercase English letters, lowercase English letters, or digits.*);
    // ensures(*The string result is not equal to the null literal.*);
    // ensures(*The length of the string result is equal to the length of the string parameter `s`.*);
    // ensures(*The string result is sorted in descending order by the frequency of characters.*);
    // ensures(*If the string parameter `s` is equal to "tree", the string result is equal to "eert" or the string result is equal to "eetr".*);
    // ensures(*If the string parameter `s` is equal to "cccaaa", the string result is equal to "cccaaa" or the string result is equal to "aaaccc".*);
    // ensures(*If the string parameter `s` is equal to "Aabb", the string result is equal to "bbAa" or the string result is equal to "bbaA".*);
    result := s;
    assume false;
}
