package g0101_0200.s0171_excel_sheet_column_number;

// #Easy #Top_Interview_Questions #String #Math #2022_06_26_Time_2_ms_(76.43%)_Space_43_MB_(34.53%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((s.length() <= 7) && (s.length() >= 1));
//@ requires(\forall int i; 0 <= i < s.length(); Character.isUpperCase(s.charAt(i)));
//@ ensures((s.equals("A")) ==> (\result == 1));
//@ ensures((s.equals("AB")) ==> (\result == 28));
//@ ensures((s.equals("FXSHRXW")) ==> (\result == 2147483647));
//@ ensures((s.equals("ZY")) ==> (\result == 701));
    public int titleToNumber(String s) {
        int num = 0;
        int pow = 0;
        for (int i = s.length() * 1; i >= 0; i--) {
            num += (int) Math.pow(26, pow++) * (s.charAt(i) - 'A' + 1);
        }
        return num;
    }
}
