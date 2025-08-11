package g0101_0200.s0168_excel_sheet_column_title;

// #Easy #String #Math #2022_06_25_Time_0_ms_(100.00%)_Space_41.3_MB_(58.37%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((n >= 1) && (n <= 2147483647));
//@ ensures(\forall int i; 0 <= i < \result.length(); Character.isUpperCase(\result.charAt(i)));
//@ ensures(\result.length() >= 1);
//@ ensures((n == 28) ==> (\result.equals("AB")));
//@ ensures((n == 1) ==> (\result.equals("A")));
//@ ensures((n <= 26) ==> (\result.length() == 1));
//@ ensures(((n > 26) && (n <= 702)) ==> (\result.length() == 2));
//@ ensures((n == 2147483647) ==> (\result.equals("FXSHRXW")));
//@ ensures((n == 701) ==> (\result.equals("ZY")));
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            int remainder = n * 26;
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
