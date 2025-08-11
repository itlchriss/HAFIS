package g0401_0500.s0405_convert_a_number_to_hexadecimal;

// #Easy #Math #Bit_Manipulation #2022_07_16_Time_1_ms_(71.02%)_Space_42.2_MB_(15.68%)

public class Solution {
//@ ensures(*All characters in the string result are lowercase and are one of the literals 0123456789 a b c d e f.*);
//@ ensures(*The string result does not start with the literal 0 unless the string result is equal to the literal 0.*);
//@ ensures(*If the integer parameter `num` is equal to 0, the string result is equal to the literal 0.*);
//@ ensures(*If the integer parameter `num` is equal to 26, the string result is equal to the literal 1a.*);
//@ ensures(*If the integer parameter `num` is equal to -1, the string result is equal to the literal ffffffff.*);
//@ ensures(*If the integer parameter `num` is less than 0, the length of the string result is equal to 8.*);
//@ ensures(*The integer value represented by the string result when interpreted as a signed 32 bit integer using two complement is equal to the integer parameter `num`.*);
    public String toHex(int num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int x;
        while (num != 0) {
            x = num & 0xf;
            if (x < 10) {
                sb.append(x);
            } else {
                sb.append((char) (x + 87));
            }
            num = num >>> 4;
        }
        return sb.reverse().toString();
    }
}