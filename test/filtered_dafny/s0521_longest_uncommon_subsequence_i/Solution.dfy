// #Easy #String #2022_07_28_Time_0_ms_(100.00%)_Space_40.2_MB_(87.89%)
// Dafny version of Solution

// requires(*The length of the string parameter `a` is less than or equal to 100 and is greater than or equal to 1.*);
// requires(*The length of the string parameter `b` is less than or equal to 100 and is greater than or equal to 1.*);
// ensures(*The integer result is less than or equal to the maximum value of java integer and is greater than or equal to -1.*);
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
