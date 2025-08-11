package g0101_0200.s0171_excel_sheet_column_number;

// #Easy #Top_Interview_Questions #String #Math #2022_06_26_Time_2_ms_(76.43%)_Space_43_MB_(34.53%)

public class Solution {
    /*@
      @   // ---------------- PRECONDITIONS ----------------
      @   requires columnTitle != null;
      @   requires 1 <= columnTitle.length() && columnTitle.length() <= 7;
      @   requires (\forall int i; 0 <= i && i < columnTitle.length();
      @                   'A' <= columnTitle.charAt(i) &&
      @                   columnTitle.charAt(i) <= 'Z');
      @
      @   // --------------- EXPECTED RESULT RANGE --------------
      @   ensures 1 <= \result && \result <= 2147483647;   // Integer.MAX_VALUE
      @
      @   // --------------- FUNCTIONAL SPECIFICATION --------------
      @   // Let n == columnTitle.length().
      @   // The value is interpreted as a base-26 number with
      @   // ‘A’ … ‘Z’ mapped to 1 … 26.
      @   ensures
      @      \result ==
      @        (\sum int k; 0 <= k && k < columnTitle.length();
      @              (columnTitle.charAt(k) - 'A' + 1)
      @                  * ((int)java.lang.Math.pow(26, columnTitle.length() - k - 1)));
      @
      @   // --------------- MISCELLANEOUS --------------
      @   // The method does not have side effects.
      @   pure
      @*/
    public int titleToNumber(String s) {
        int num = 0;
        int pow = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            num += (int) Math.pow(26, pow++) * (s.charAt(i) - 'A' + 1);
        }
        return num;
    }
}