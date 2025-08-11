package g0401_0500.s0405_convert_a_number_to_hexadecimal;

// #Easy #Math #Bit_Manipulation #2022_07_16_Time_1_ms_(71.02%)_Space_42.2_MB_(15.68%)

public class Solution {
    /*@ public model pure static long unsignedValueOfHex(String s);
      @ //  represents unsignedValueOfHex <- (
      @ // // // // // // // // //      (\sum int i; 0 <= i && i < s.length();
      @ // // //           charToValue(s.charAt(i)) * pow16(s.length() - 1 - i))
      @ // //  );
      @*/

    /*@ public model pure static int charToValue(char c);
      @ //  represents charToValue <- (
      @ // // // // // // // // // // // // // // // // // // // // // // // // // // // //      ( '0' <= c && c <= '9' ) ?  c - '0' : 10 + (c - 'a')
      @ // //  );
      @*/

    /*@ public model pure static long pow16(int k);
      @ // // //  represents pow16 <- (\pow 16L k);   // 16^k as a long
      @*/

    /*@ public normal_behavior
      @   // 1.  Result equals the canonical, lower-case, 32-bit two’s–complement
      @   //     hexadecimal representation of num (exactly what the Java
      @   //     library routine Integer.toHexString(int) would return).
      @   ensures  \result.equals(Integer.toHexString(num));
      @
      @   // 2.  Shape constraints that follow from the previous clause, written
      @   //     explicitly so that a verifier does not need to know the library:
      @   ensures  \result != null;                         // never null
      @   ensures  1 <= \result.length() <= 8;              // at most 32 bits → 8 hex digits
      @   ensures  (\forall int i; 0 <= i && i < \result.length();
      @               ('0' <= \result.charAt(i) && \result.charAt(i) <= '9') ||
      @               ('a' <= \result.charAt(i) && \result.charAt(i) <= 'f')); // only lower-case hex digits
      @
      @   // 3.  No leading zeroes (except for the number zero itself).
      @   ensures  (num == 0) ==> \result.equals("0");
      @   ensures  (num != 0) ==> \result.charAt(0) != '0';
      @
      @   // 4.  Result interpreted as an unsigned 32-bit value equals num’s
      @   //     two’s-complement bit pattern (guarantees correct handling
      @   //     of negative inputs).
      @   ensures  unsignedValueOfHex(\result) == ((long)num & 0xFFFF_FFFFL);
      @
      @   // 5.  Method is total and side-effect-free.
      @ //   signals (Exception e) false;
      @   assignable \nothing;
      @*/
    public String toHex(int num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int x;
        while (num != 0) {
            x = num & 0xf;
            if (x <= 10) {
                sb.append(x);
            } else {
                sb.append((char) (x + 87));
            }
            num = num >>> 4;
        }
        return sb.reverse().toString();
    }
}