package g0201_0300.s0258_add_digits;

// #Easy #Math #Simulation #Number_Theory #2022_07_05_Time_1_ms_(100.00%)_Space_39.3_MB_(98.44%)

public class Solution {
//@ requires(*The integer parameter `num` is greater than or equal to 0 and is less than or equal to 2147483647.*);
//@ ensures(*The integer result is greater than or equal to 0 and is less than or equal to 9.*);
//@ ensures(*If the integer parameter `num` is equal to 0, the integer result is equal to 0.*);
//@ ensures(*If the integer parameter `num` is equal to 38, the integer result is equal to 2.*);
//@ ensures(*If the integer parameter `num` is less than 10 and is greater than 0, the integer result is equal to the integer parameter `num`.*);
//@ ensures(*If the integer result is greater than 0, the integer result is equal to 1 plus the remainder of the integer parameter `num` minus 1 divided by 9.*);
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }
        if (num % 9 == 0) {
            return 9;
        }
        return num % 9;
    }
}