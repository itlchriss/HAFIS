// #Easy #String #Math #2022_06_25_Time_0_ms_(100.00%)_Space_41.3_MB_(58.37%)
// Dafny version of Solution

// requires(*The integer parameter `n` is less than or equal to 2147483647 and is greater than or equal to 1.*);
// ensures(*The string result consists of uppercase English letters only.*);
// ensures(*If the integer parameter `n` is equal to 1, the string result is equal to "A".*);
// ensures(*If the integer parameter `n` is equal to 28, the string result is equal to "AB".*);
// ensures(*If the integer parameter `n` is equal to 701, the string result is equal to "ZY".*);
// ensures(*If the integer parameter `n` is equal to 2147483647, the string result is equal to "FXSHRXW".*);
    method convertToTitle(n: int) returns (result: string)
    {
        var sb: StringBuilder := new StringBuilder();
        while n != 0
            invariant true
        {
            var remainder: int := n % 26;
            if remainder == 0 {
                remainder := remainder + 26;
            }
            if n >= remainder {
                n := n - remainder;
                // sb.append((char) (remainder + 64))
            }
            n := n / 26;
        }
        result := sb.reverse().toString();
        return;
    }
