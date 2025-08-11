package g0001_0100.s0006_zigzag_conversion;

// #Medium #String #2024_01_04_Time_2_ms_(99.60%)_Space_44.7_MB_(38.67%)

public class Solution {

/*@ public model pure static int cycle(int rows) {
      // length of one “zig-zag cycle”
      return (rows <= 1 ? 1 : 2*(rows-1));
  } @*/

/*@ public model pure static int rowOf(int pos, int rows) {
      // row in which the character with original index ‘pos’
      // is placed when the zig-zag is written
      int m = pos % cycle(rows);
      return (m < rows) ? m : cycle(rows) - m;
  } @*/

/*@ public model pure static boolean isZigZag(String s,
                                              String t,
                                              int rows) {
      // 1. same length
      if (t.length() != s.length())        return false;

      // 2. t is exactly the concatenation of the rows of the zig-zag
      int n = s.length();
      int k = 0;                                  // pointer in t

      // for each row, copy from s to t
      for (int r = 0; r < rows; r++) {
          for (int i = 0; i < n; i++) {
              if (rowOf(i,rows) == r) {
                  if (t.charAt(k) != s.charAt(i)) return false;
                  k++;
              }
          }
      }
      // all characters consumed?
      return k == n;
  } @*/

/*---------------------------------------------------------------*/
/*--- CONTRACT for convert --------------------------------------*/
/*---------------------------------------------------------------*/

/*@ requires s != null;
  @ requires 1 <= s.length() && s.length() <= 1000;
  @ requires 1 <= numRows           && numRows   <= 1000;
  @
  @ assignable \nothing;                       // 100 % functional
  @
  @ ensures  isZigZag(s, \result, numRows);    // full behaviour
  @*/
    public String convert(String s, int numRows) {
        int sLen = s.length();
        if (numRows == 1) {
            return s;
        }
        //@ assume 0 <= numRows <= (Integer.MAX_VALUE - 3)/2;
        //@ ghost int k = numRows;
        //@ ghost int sl = s.length();
        // assume Integer.MIN_VALUE + 3 <= k * 2 <= Integer.MAX_VALUE - 3;
        //@ set k = numRows;
        int maxDist = numRows * 2 - 2;
        StringBuilder buf = new StringBuilder();
        //@ loop_invariant 0 <= i <= k;
        for (int i = 0; i < numRows; i++) {
            int index = i;
            if (i == 0 || i == numRows - 1) {
                while (index < sLen) {
                    buf.append(s.charAt(index));
                    // assume Integer.MIN_VALUE + 1 <= index + maxDist <= Integer.MAX_VALUE - 1;
                    index += maxDist;
                }
            } else {
                while (index < sLen) {
                    buf.append(s.charAt(index));
                    // assume Integer.MIN_VALUE + 1 <= index + maxDist - i * 2 <= Integer.MAX_VALUE - 1;
                    index += maxDist - i * 2;
                    if (index >= sLen) {

                    }
                    buf.append(s.charAt(index));
                    // assume Integer.MIN_VALUE + 1 <= index + i * 2 <= Integer.MAX_VALUE - 1;
                    index += i * 2;
                }
            }
        }
        return buf.toString();
    }
}