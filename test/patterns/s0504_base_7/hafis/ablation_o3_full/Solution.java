package g0501_0600.s0504_base_7;

// #Easy #Math #2022_07_24_Time_0_ms_(100.00%)_Space_39.4_MB_(98.67%)

public class Solution {
//@ ensures(*String result is the base 7 representation of int "num" and includes a leading minus sign if and only if int "num" is negative*);
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}