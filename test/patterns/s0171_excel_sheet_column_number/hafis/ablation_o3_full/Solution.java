package g0101_0200.s0171_excel_sheet_column_number;

// #Easy #Top_Interview_Questions #String #Math #2022_06_26_Time_2_ms_(76.43%)_Space_43_MB_(34.53%)

public class Solution {
//@ ensures(*When String `s` satisfies its prerequisites int result equals the Excel column number associated with String `s`*);
//@ ensures(*If String `s` equals "A" int result equals 1*);
//@ ensures(*If String `s` equals "AB" int result equals 28*);
//@ ensures(*If String `s` equals "ZY" int result equals 701*);
//@ ensures(*If String `s` equals "FXSHRXW" int result equals 2147483647*);
    public int titleToNumber(String s) {
        int num = 0;
        int pow = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            num += (int) Math.pow(26, pow++) * (s.charAt(i) - 'A' + 1);
        }
        return num;
    }
}