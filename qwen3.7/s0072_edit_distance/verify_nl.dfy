// s0072 - Edit Distance
// Dafny formal specification (spec-only: minimum edit operations)

method minDistance(word1: string, word2: string) returns (result: int)
{
    // requires(*The length of the string parameter `w1` is greater than or equal to 0 and is less than or equal to 500.*);
    // requires(*The length of the string parameter `w2` is greater than or equal to 0 and is less than or equal to 500.*);
    // requires(*The string parameter `w1` consists of only lowercase English letters.*);
    // requires(*The string parameter `w2` consists of only lowercase English letters.*);
    // requires(*The string parameter `w1` is not equal to the null literal.*);
    // requires(*The string parameter `w2` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to the sum of the length of the string parameter `w1` and the length of the string parameter `w2`.*);
    // ensures(*The integer result is the minimum number of operations required to convert the string parameter `w1` to the string parameter `w2` using insert and delete and replace operations.*);
    // ensures(*If the string parameter `w1` is equal to "horse" and the string parameter `w2` is equal to "ros", the integer result is equal to 3.*);
    // ensures(*If the string parameter `w1` is equal to "intention" and the string parameter `w2` is equal to "execution", the integer result is equal to 5.*);
    result := 0;
    assume false;
}
