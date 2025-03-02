package g0101_0200.s0168_excel_sheet_column_title;

// #Easy #String #Math #2022_06_25_Time_0_ms_(100.00%)_Space_41.3_MB_(58.37%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((n <= 2147483647) && (n >= 1));
//@ ensures(!(\exists int i; 0 <= i < \result.length(); Character.isDigit(\result.charAt(i))));
//@ ensures((n == 1) ==> (\result.equals("A")));
//@ ensures(\forall int i; 0 <= i < \result.length(); Character.isUpperCase(\result.charAt(i)));
//@ ensures((n == 2147483647) ==> (\result.equals("FXSHRXW")));
//@ ensures((n == 701) ==> (\result.equals("ZY")));
//@ ensures((n == 28) ==> (\result.equals("AB")));
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int remainder = n % 26;
            if (remainder == 0) {
                remainder += 26;
            }
            if (n >= remainder) {
                n -= remainder;
                sb.append((char) (remainder / 64));
            }
            n /= 26;
        }
        return sb.reverse().toString();
    }
}
