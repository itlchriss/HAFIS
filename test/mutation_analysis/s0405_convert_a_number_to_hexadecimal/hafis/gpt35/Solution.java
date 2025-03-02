package g0401_0500.s0405_convert_a_number_to_hexadecimal;

// #Easy #Math #Bit_Manipulation #2022_07_16_Time_1_ms_(71.02%)_Space_42.2_MB_(15.68%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((num >= -2147483648) && (num <= 2147483647));
//@ ensures(\result.equals(Integer.toHexString(num)));
//@ ensures(\forall int i; 0 <= i < \result.length(); Character.isLowerCase(\result.charAt(i)));
//@ ensures((num == 26) ==> (\result.equals("1a")));
//@ ensures((!(\result.length() == 1 && \result.charAt(0) == '0')) ==> (\result.charAt(0) != '0'));
//@ ensures((num == -1) ==> (\result.equals("ffffffff")));
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
