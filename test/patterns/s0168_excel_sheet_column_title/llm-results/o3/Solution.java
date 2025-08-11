package g0101_0200.s0168_excel_sheet_column_title;

// #Easy #String #Math #2022_06_25_Time_0_ms_(100.00%)_Space_41.3_MB_(58.37%)

public class Solution {
    /*@ public normal_behavior
  @   requires 1 <= columnNumber && columnNumber <= 2147483647;   // 2^31-1
  @
  @   ensures \result != null;
  @
  @   // 1.  Every character is between ‘A’ and ‘Z’.
  @   ensures (\forall int i; 0 <= i && i < \result.length();
  @               'A' <= \result.charAt(i) && \result.charAt(i) <= 'Z');
  @
  @   /* 2.  Let the string be s_ℓ … s_1 (left-most is the highest digit).
  @    *     Define digit_k  = (s_k - 'A' + 1)   with 1 ≤ digit_k ≤ 26.
  @    *     Then
  @    *
  @    *         columnNumber  =  Σ_{k=1..ℓ}  digit_k · 26^{k-1}
  @    *
  @    *     The summation below is written in JML using
  @    *     (length-1-k) to index from the right-most character.
  @    */
  @   ensures
  @       columnNumber ==
  @           (\sum int k; 0 <= k && k < \result.length();
  @               (\result.charAt(\result.length() - 1 - k) - 'A' + 1)
  @               * ( (\bigint) JMLMath.pow(26, k) ));
  @
  @   assignable \nothing;       // the method is side-effect free
  @   pure;                      // and can be used in annotations
  @*/
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