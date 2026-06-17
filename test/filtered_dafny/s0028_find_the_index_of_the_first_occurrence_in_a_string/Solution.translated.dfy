// #Easy #Top_Interview_Questions #String #Two_Pointers #String_Matching
// #Programming_Skills_II_Day_1 #2023_08_09_Time_0_ms_(100.00%)_Space_40.5_MB_(71.14%)
// Dafny version of Solution

requires (haystack.length <= 50000) && (haystack.length >= 0)
requires (haystack.length <= 50000) && (haystack.length >= 0)
requires (haystack.size() <= 50000) && (haystack.size() >= 0)
requires (haystack.length() <= 50000) && (haystack.length() >= 0)
requires (haystack.size() <= 50000) && (haystack.size() >= 0)
requires (needle.length <= 50000) && (needle.length >= 0)
requires (needle.length <= 50000) && (needle.length >= 0)
requires (needle.size() <= 50000) && (needle.size() >= 0)
requires (needle.length() <= 50000) && (needle.length() >= 0)
requires (needle.size() <= 50000) && (needle.size() >= 0)
// ensures(*The integer result is less than or equal to the length of the string parameter `haystack` and is greater than or equal to -1.*);
ensures (needle.length() == 0) ==> (\result == 0)
ensures (!(haystack != null && needle != null && haystack.length() <= needle.length() && (\exists int i; 0 <= i && i <= needle.length() - haystack.length(); (\forall int j; 0 <= j && j < haystack.length(); needle.charAt(i + j) == haystack.charAt(j))))) ==> (\result == -1)
// ensures(*If the string parameter `needle` is part of the string parameter `haystack`, the integer result is equal to the index of the first occurrence of `needle` in `haystack`.*);
    method strStr(haystack: string, needle: string) returns (result: int)
    {
        if |needle| == 0 {
            result := 0;
            return;
        }
        var m: int := |haystack|();
        var n: int := |needle|();
        for start := 0 to m - n + 1
            invariant start >= 0
            invariant start <= m - n + 1
        {
            if haystack.substring(start, start + n).equals(needle) {
                result := start;
                return;
            }
        }
        result := -1;
        return;
    }
