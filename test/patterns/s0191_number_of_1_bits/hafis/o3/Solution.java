package g0101_0200.s0191_number_of_1_bits;

// #Easy #Top_Interview_Questions #Bit_Manipulation #Algorithm_I_Day_13_Bit_Manipulation
// #Programming_Skills_I_Day_2_Operator #Udemy_Bit_Manipulation
// #2022_06_28_Time_1_ms_(84.87%)_Space_41.8_MB_(10.40%)

public class Solution {
//@ ensures(*The integer result is greater than or equal to 0 and is less than or equal to 32.*);
//@ ensures(*The integer result is equal to the number of bits equal to 1 in the 32-bit binary representation of the integer parameter `n`.*);
//@ ensures(*If the integer parameter `n` is equal to 11, the integer result is equal to 3.*);
//@ ensures(*If the integer parameter `n` is equal to 128, the integer result is equal to 1.*);
//@ ensures(*If the integer parameter `n` is equal to -3, the integer result is equal to 31.*);
    public int hammingWeight(int n) {
        int sum = 0;
        boolean flag = false;
        if (n < 0) {
            flag = true;
            n = n - Integer.MIN_VALUE;
        }
        while (n > 0) {
            int k = n % 2;
            sum += k;
            n /= 2;
        }
        return flag ? sum + 1 : sum;
    }
}