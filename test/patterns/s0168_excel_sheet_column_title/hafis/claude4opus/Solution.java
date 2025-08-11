package g0101_0200.s0168_excel_sheet_column_title;

// #Easy #String #Math #2022_06_25_Time_0_ms_(100.00%)_Space_41.3_MB_(58.37%)

public class Solution {
//@ requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 2147483647.*);
//@ ensures(*The string result consists of only uppercase English letters.*);
//@ ensures(*The length of the string result is greater than or equal to 1.*);
//@ ensures(*If the integer parameter `n` is equal to 1, the string result is equal to "A".*);
//@ ensures(*If the integer parameter `n` is equal to 28, the string result is equal to "AB".*);
//@ ensures(*If the integer parameter `n` is equal to 701, the string result is equal to "ZY".*);
//@ ensures(*If the integer parameter `n` is equal to 2147483647, the string result is equal to "FXSHRXW".*);
//@ ensures(*If the integer parameter `n` is less than or equal to 26, the length of the string result is equal to 1.*);
//@ ensures(*If the integer parameter `n` is greater than 26 and is less than or equal to 702, the length of the string result is equal to 2.*);
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int remainder = n % 26;
            if (remainder == 0) {
                remainder += 26;
            }
            if (n >= remainder) {
                n -= remainder;
                sb.append((char) (remainder + 64));
            }
            n /= 26;
        }
        return sb.reverse().toString();
    }
}