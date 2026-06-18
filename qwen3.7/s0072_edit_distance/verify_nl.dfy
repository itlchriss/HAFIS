// s0072 - Edit Distance
// Dafny formal specification (spec-only: minimum edit operations)

// requires(*The length of the string parameter `word1` is greater than or equal to 0 and is less than or equal to 500.*);
// requires(*The length of the string parameter `word2` is greater than or equal to 0 and is less than or equal to 500.*);
// requires(*The string parameter `word1` consists of only lowercase English letters.*);
// requires(*The string parameter `word2` consists of only lowercase English letters.*);
// requires(*The string parameter `word1` is not equal to the null literal.*);
// requires(*The string parameter `word2` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is equal to the minimum number of operations required to convert the string parameter `word1` to the string parameter `word2` using insert and delete and replace operations.*);
// ensures(*If the string parameter `word1` is equal to "horse" and the string parameter `word2` is equal to "ros", the integer result is equal to 3.*);
// ensures(*If the string parameter `word1` is equal to "intention" and the string parameter `word2` is equal to "execution", the integer result is equal to 5.*);
method minDistance(word1: string, word2: string) returns (result: int)
{
    result := 0;
    assume false;
}
