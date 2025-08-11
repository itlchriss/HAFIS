package g0301_0400.s0393_utf_8_validation;

// #Medium #Array #Bit_Manipulation #2022_07_13_Time_1_ms_(100.00%)_Space_43_MB_(87.62%)

public class Solution {
     /*@ // public static pure boolean isAscii        (int b) { return (b & 0x80) == 0x00; } @ //*/
    /*@ // public static pure boolean isLeadOf2Bytes (int b) { return (b & 0xE0) == 0xC0; } @ //*/
    /*@ // public static pure boolean isLeadOf3Bytes (int b) { return (b & 0xF0) == 0xE0; } @ //*/
    /*@ // public static pure boolean isLeadOf4Bytes (int b) { return (b & 0xF8) == 0xF0; } @ //*/
    /*@ // public static pure boolean isContinuation (int b) { return (b & 0xC0) == 0x80; } @ //*/


    /*------------------------------------------------------------------
      A pure model operation that *defines* what “being valid UTF-8”
      means.  The public routine has to return the same truth value.
      ------------------------------------------------------------------*/
    /*@ public static model pure boolean utf8ValidModel (int[] bytes) {
          int expected = 0;                       // pending continuation bytes
          for (int i = 0; i < bytes.length; i++) {
              int b = bytes[i];

              // while waiting for a leading byte
              if (expected == 0) {
//if      (isAscii(b))             { expected = 0;              }
//else if (isLeadOf2Bytes(b))      { expected = 1;              }
//else if (isLeadOf3Bytes(b))      { expected = 2;              }
//else if (isLeadOf4Bytes(b))      { expected = 3;              }
//else                             { return false;              }
              }
              // inside a multi–byte character
              else {
//if (!isContinuation(b))          { return false;              }
                  --expected;
              }
          }
          return expected == 0;                    // no trailing debt allowed
      } @*/

    /*@ public normal_behavior
          // -------------  Pre–conditions  --------------------------------
          requires data != null;
          requires 1            <= data.length && data.length <= 20000;
          requires (\forall int i; 0 <= i && i < data.length;
                                  0 <= data[i] && data[i] <= 255);

          // -------------  Post–condition  --------------------------------
          ensures  \result == utf8ValidModel(data);

          // -------------  Assignable clause  -----------------------------
          assignable \nothing;                        // pure function
      @*/
    public boolean validUtf8(int[] data) {
        int count = 0;
        for (int d : data) {
            if (count == 0) {
                if (d >> 5 == 0b110) {
                    count = 1;
                } else if (d >> 4 == 0b1110) {
                    count = 2;
                } else if (d >> 3 == 0b11110) {
                    count = 3;
                } else if (d >> 7 == 1) {
                    return false;
                }
            } else {
                if (d >>> 6 != 0b10) {
                    return false;
                } else {
                    count--;
                }
            }
        }
        return count == 0;
    }
}