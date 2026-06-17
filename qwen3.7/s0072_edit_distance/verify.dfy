// s0072 - Edit Distance
// Dafny formal specification (spec-only: minimum edit operations)

method minDistance(word1: string, word2: string) returns (result: int)
    requires 0 <= |word1| <= 500
    requires 0 <= |word2| <= 500
    ensures result >= 0
    ensures result <= |word1| + |word2|
    ensures word1 == word2 ==> result == 0
    ensures |word1| == 0 ==> result == |word2|
    ensures |word2| == 0 ==> result == |word1|
{
    result := 0;
    assume false;
}
