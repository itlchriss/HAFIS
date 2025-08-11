package g0001_0100.s0091_decode_ways;

// #Medium #Top_Interview_Questions #String #Dynamic_Programming
// #Algorithm_II_Day_15_Dynamic_Programming #Dynamic_Programming_I_Day_10
// #2022_06_21_Time_2_ms_(66.37%)_Space_41.8_MB_(78.45%)

public class Solution {
//@ requires(*The string parameter `s` only consists of digits.*);
//@ ensures(*The integer result is less than or equal to 2147483647 and is greater than or equal to 0.*);
//@ ensures(*If the string parameter `s` is equal to "12" the integer result is equal to 2.*);
//@ ensures(*If the string parameter `s` is equal to "226" the integer result is equal to 3.*);
//@ ensures(*If the string parameter `s` is equal to "0" the integer result is equal to 0.*);
//@ ensures(*If the string parameter `s` is equal to "06" the integer result is equal to 0.*);
//@ ensures(*If the integer result is equal to 0 the string parameter `s` has no valid decoding using the mapping from the string "1" to the character 'A' through the string "26" to the character 'Z'.*);
    public int numDecodings(String s) {
        if (s.charAt(0) == '0') {
            return 0;
        }
        int n = s.length();
        int[] f = new int[n + 1];
        // Auxiliary
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            // Calculate the independent number
            if (s.charAt(i - 1) != '0') {
                // As long as the current character is not 0, it means that the previous decoding
                // number can be inherited
                f[i] = f[i - 1];
            }
            // Calculate the number of combinations
            int twodigits = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (twodigits >= 10 && twodigits <= 26) {
                f[i] += f[i - 2];
            }
        }
        return f[n];
    }
}