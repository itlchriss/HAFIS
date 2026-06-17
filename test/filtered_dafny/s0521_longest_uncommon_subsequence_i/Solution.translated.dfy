// #Easy #String #2022_07_28_Time_0_ms_(100.00%)_Space_40.2_MB_(87.89%)
// Dafny version of Solution

requires (a.length <= 100) && (a.length >= 1)
requires (a.length <= 100) && (a.length >= 1)
requires (a.size() <= 100) && (a.size() >= 1)
requires (a.length() <= 100) && (a.length() >= 1)
requires (a.size() <= 100) && (a.size() >= 1)
requires (b.length <= 100) && (b.length >= 1)
requires (b.length <= 100) && (b.length >= 1)
requires (b.size() <= 100) && (b.size() >= 1)
requires (b.length() <= 100) && (b.length() >= 1)
requires (b.size() <= 100) && (b.size() >= 1)
ensures (\result <= 2147483647) && (\result >= -1)
// ensures(*If the string parameter `a` is equal to "aba" and the string parameter `b` is equal to "cdc", the integer result is equal to 3.*);
// ensures(*If the string parameter `a` is equal to "aaa" and the string parameter `b` is equal to "bbb", the integer result is equal to 3.*);
// ensures(*If the string parameter `a` is equal to "aaa" and the string parameter `b` is equal to "aaa", the integer result is equal to -1.*);
    method findLUSlength(a: string, b: string) returns (result: int)
    {
        if a == b {
            result := -1;
            return;
        }
        result := max(|a|(), |b|());
        return;
    }
