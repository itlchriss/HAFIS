package g0201_0300.s0202_happy_number;

// #Easy #Top_Interview_Questions #Hash_Table #Math #Two_Pointers #Algorithm_II_Day_21_Others
// #Programming_Skills_I_Day_4_Loop #Level_2_Day_1_Implementation/Simulation
// #2022_06_28_Time_1_ms_(98.59%)_Space_41_MB_(64.25%)

public class Solution {
//@ requires(*The integer parameter `n` is less than or equal to 2147483647 and is greater than or equal to 1.*);
//@ ensures(*If the boolean result is equal to the true literal, the integer parameter `n` is a happy number.*);
//@ ensures(*If the boolean result is equal to the false literal, the integer parameter `n` is not a happy number.*);
//@ ensures(*If the integer parameter `n` is equal to 19, the boolean result is equal to the true literal.*);
//@ ensures(*If the integer parameter `n` is equal to 2, the boolean result is equal to the false literal.*);
    public boolean isHappy(int n) {
        boolean happy;
        int a = n;
        int rem;
        int sum = 0;
        if (a == 1 || a == 7) {
            happy = true;
        } else if (a > 1 && a < 10) {
            happy = false;
        } else {
            while (a != 0) {
                rem = a % 10;
                sum = sum + (rem * rem);
                a = a / 10;
            }
            if (sum != 1) {
                happy = isHappy(sum);
            } else {
                happy = true;
            }
        }
        return happy;
    }
}