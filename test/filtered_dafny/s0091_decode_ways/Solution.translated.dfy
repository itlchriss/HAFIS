// #Medium #Top_Interview_Questions #String #Dynamic_Programming
// #Algorithm_II_Day_15_Dynamic_Programming #Dynamic_Programming_I_Day_10
// #2022_06_21_Time_2_ms_(66.37%)_Space_41.8_MB_(78.45%)
// Dafny version of Solution

requires (s.length <= 100) && (s.length >= 1)
requires (s.length <= 100) && (s.length >= 1)
requires (s.size() <= 100) && (s.size() >= 1)
requires (s.length() <= 100) && (s.length() >= 1)
requires (s.size() <= 100) && (s.size() >= 1)
// requires(*The string parameter `s` contains only digits and may contain leading zeros.*);
ensures (\result <= 2147483647) && (\result >= 0)
// ensures(*If the string parameter `s` is equal to "12", the integer result is equal to 2.*);
// ensures(*If the string parameter `s` is equal to "226", the integer result is equal to 3.*);
// ensures(*If the string parameter `s` is equal to "0", the integer result is equal to 0.*);
// ensures(*If the string parameter `s` is equal to "06", the integer result is equal to 0.*);
    method numDecodings(s: string) returns (result: int)
    {
        if s[0] == '0' {
            result := 0;
            return;
        }
        var n: int := |s|();
        var f := new int[n + 1];
        // Auxiliary
        f[0] := 1;
        f[1] := 1;
        for i := 2 to n + 1
            invariant i >= 2
            invariant i <= n + 1
        {
            // Calculate the independent number
            if s[i - 1] != '0' {
                // As long as the current character is not 0, it means that the previous decoding
                // number can be inherited
                f[i] := f[i - 1];
            }
            // Calculate the number of combinations
            var twodigits: int := (s[i - 2] - '0') * 10 + (s[i - 1] - '0');
            if twodigits >= 10 && twodigits <= 26 {
                f[i] := f[i] + f[i - 2];
            }
        }
        result := f[n];
        return;
    }
