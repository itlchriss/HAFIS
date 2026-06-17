// #Hard #Top_Interview_Questions #String #Dynamic_Programming #Greedy #Recursion
// #Udemy_Dynamic_Programming #2023_08_11_Time_2_ms_(99.87%)_Space_43.2_MB_(99.49%)
// Dafny version of Solution

// requires(*The string parameter `inputString` and the string parameter `pattern` contain only lowercase English letters, '?' or '*'.*);
// requires(*The length of the string parameter `inputString` and the string parameter `pattern` is less than or equal to 2000.*);
// ensures(*If the boolean result is equal to false, the string parameter `pattern` does not match the entire string parameter `inputString`.*);
// ensures(*If the boolean result is equal to true, the string parameter `pattern` matches the entire string parameter `inputString`.*);
// ensures(*If the string parameter `inputString` is equal to "aa" and the string parameter `pattern` is equal to "a", the boolean result is false.*);
// ensures(*If the string parameter `inputString` is equal to "aa" and the string parameter `pattern` is equal to "*", the boolean result is true.*);
// ensures(*If the string parameter `inputString` is equal to "cb" and the string parameter `pattern` is equal to "?a", the boolean result is false.*);
// ensures(*If the string parameter `inputString` is equal to "adceb" and the string parameter `pattern` is equal to "*a*b", the boolean result is true.*);
// ensures(*If the string parameter `inputString` is equal to "acdcb" and the string parameter `pattern` is equal to "a*c?b", the boolean result is false.*);
    method isMatch(inputString: string, pattern: string) returns (result: bool)
    {
        var i: int := 0;
        var j: int := 0;
        var starIdx: int := -1;
        var lastMatch: int := -1;
        while i < |inputString|()
            invariant true
        {
            if  {
                // && (inputString.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '?')) {
            }
            i := i + 1;
            j := j + 1;
            // } else if (j < pattern.length() && pattern.charAt(j) == '*') {
            starIdx := j;
            lastMatch := i;
            j := j + 1;
            // } else if (starIdx != -1) {
            // there is a no match and there was a previous star, we will reset the j to indx
            // after star_index
            // lastMatch will tell from which index we start comparing the string if we
            // encounter * in pattern
            j := starIdx + 1;
            // we are saying we included more characters in * so we incremented the
            lastMatch := lastMatch + 1;
            // index
            i := lastMatch;
            // } else {
            result := false;
            return;
        }
        var isMatch: bool := true;
        while j < |pattern|() && pattern[j] == '*'
            invariant true
        {
            j := j + 1;
        }
        if i != |inputString|() || j != |pattern|() {
            isMatch := false;
        }
        result := isMatch;
        return;
    }
