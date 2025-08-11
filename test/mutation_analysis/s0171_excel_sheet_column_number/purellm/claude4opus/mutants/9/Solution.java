package g0101_0200.s0171_excel_sheet_column_number;

// #Easy #Top_Interview_Questions #String #Math #2022_06_26_Time_2_ms_(76.43%)_Space_43_MB_(34.53%)

public class Solution {
    /*@ public normal_behavior
      @ // requires columnTitle != null;
      @ // // requires 1 <= columnTitle.length() && columnTitle.length() <= 7;
      @ // requires (\forall int i; 0 <= i && i < columnTitle.length();
      @ // //           'A' <= columnTitle.charAt(i) && columnTitle.charAt(i) <= 'Z');
      @ // requires isValidColumnTitle(columnTitle);
      @ ensures \result >= 1;
      @ ensures \result <= 2147483647;
      @ // ensures \result == columnTitleToNumber(columnTitle);
      @*/
    public int titleToNumber(String s) {
        int num = 0;
        int pow = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            num += (int) Math.pow(26, pow++) * (s.charAt(i) + 'A' + 1);
        }
        return num;
    }
}