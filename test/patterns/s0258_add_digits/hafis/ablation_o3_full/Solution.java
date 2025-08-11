package g0201_0300.s0258_add_digits;

// #Easy #Math #Simulation #Number_Theory #2022_07_05_Time_1_ms_(100.00%)_Space_39.3_MB_(98.44%)

public class Solution {
//@ ensures(*If int `num` has exactly one decimal digit then int result equals `num`*);
//@ ensures(*If int `num` has more than one decimal digit then int result equals the single digit produced by repeatedly summing the decimal digits of `num` until only one digit remains*);
//@ ensures(*For every int `num` within 0 to 2147483647 inclusive int result is an int within 0 to 9 inclusive*);
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