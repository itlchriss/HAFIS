// #Easy #Top_Interview_Questions #String #Math #2022_06_26_Time_2_ms_(76.43%)_Space_43_MB_(34.53%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 7 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of only uppercase English letters.*);
// ensures(*If the string parameter `s` is equal to "A", the integer result is equal to 1.*);
// ensures(*If the string parameter `s` is equal to "AB", the integer result is equal to 28.*);
// ensures(*If the string parameter `s` is equal to "ZY", the integer result is equal to 701.*);
// ensures(*If the string parameter `s` is equal to "FXSHRXW", the integer result is equal to 2147483647.*);
    method titleToNumber(s: string) returns (result: int)
    {
        var num: int := 0;
        var pow: int := 0;
        var i := |s|() - 1;
        while i >= 0
            invariant true
        {
            num := num + (int) Math.pow(26, pow++) * (s[i] - 'A' + 1);
            i := i - 1;
        }
        result := num;
        return;
    }
