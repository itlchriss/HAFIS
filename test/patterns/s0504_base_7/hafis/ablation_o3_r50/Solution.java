package g0501_0600.s0504_base_7;

// #Easy #Math #2022_07_24_Time_0_ms_(100.00%)_Space_39.4_MB_(98.67%)

public class Solution {
//@ ensures(*The string result contains an optional leading minus sign followed exclusively by characters in the range 0 to 6.*);
//@ ensures(*The integer value obtained by interpreting the string result as a base seven number is equal to the integer parameter `num`.*);
//@ ensures(*If the integer parameter `num` is equal to 100, the string result is equal to "202".*);
//@ ensures(*If the integer parameter `num` is equal to -7, the string result is equal to "-10".*);
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}