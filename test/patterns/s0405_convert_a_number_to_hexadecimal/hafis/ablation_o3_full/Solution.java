package g0401_0500.s0405_convert_a_number_to_hexadecimal;

// #Easy #Math #Bit_Manipulation #2022_07_16_Time_1_ms_(71.02%)_Space_42.2_MB_(15.68%)

public class Solution {
//@ ensures(*If int `num` is greater than 0 then String result is the lowercase base sixteen representation of int `num` and String result contains no leading character "0"*);
//@ ensures(*If int `num` is less than 0 then String result is the lowercase base sixteen representation of the thirty two bit two’s complement value of int `num` and String result contains no leading character "0"*);
//@ ensures(*For every int `num` the length of String result is at least 1 and at most 8 characters*);
//@ ensures(*For every int `num` every character in String result belongs to the set "0123456789abcdef"*);
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