// #Easy #String #2022_07_25_Time_2_ms_(65.95%)_Space_42_MB_(74.10%)
// Dafny version of Solution

// requires(*The length of the string parameter `word` is less than or equal to 100 and is greater than or equal to 1.*);
// requires(*The string parameter `word` consists of lowercase and uppercase English letters.*);
// ensures(*If all letters in the string parameter `word` are capitals, the boolean result is true.*);
// ensures(*If all letters in the string parameter `word` are not capitals, the boolean result is true.*);
// ensures(*If only the first letter in the string parameter `word` is capital, the boolean result is true.*);
// ensures(*If none of the above cases hold, the boolean result is false.*);
    method detectCapitalUse(word: string) returns (result: bool)
    {
        if word == null || |word|() == 0 {
            result := false;
            return;
        }
        var upper: int := 0;
        var lower: int := 0;
        var n: int := |word|();
        var firstUpper: bool := Character.isUpperCase(word[0]);
        for i := 0 to n
            invariant i >= 0
            invariant i <= n
        {
            if Character.isUpperCase(word[i]) {
                upper := upper + 1;
            } else if Character.isLowerCase(word[i]) {
                lower := lower + 1;
            }
        }
        if firstUpper && upper > 1 {
            firstUpper := false;
        }
        result := upper == n || lower == n || firstUpper;
        return;
    }
