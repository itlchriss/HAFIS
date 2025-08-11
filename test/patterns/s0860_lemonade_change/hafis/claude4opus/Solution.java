package g0801_0900.s0860_lemonade_change;

// #Easy #Array #Greedy #Programming_Skills_II_Day_17
// #2022_03_27_Time_2_ms_(90.84%)_Space_75.8_MB_(55.09%)

public class Solution {
//@ requires(*The length of the integer array parameter `bills` is greater than or equal to 1 and is less than or equal to 100000.*);
//@ requires(*All values in the integer array parameter `bills` are equal to 5,10, or 20.*);
//@ ensures(*If the boolean result is equal to the true literal, every customer receives correct change.*);
//@ ensures(*If the boolean result is equal to the false literal, at least one customer does not receive correct change.*);
//@ ensures(*If the integer array parameter `bills` is equal to [5,5,5,10,20], the boolean result is equal to the true literal.*);
//@ ensures(*If the integer array parameter `bills` is equal to [5,5,10,10,20], the boolean result is equal to the false literal.*);
//@ ensures(*If the first value in the integer array parameter `bills` is not equal to 5, the boolean result is equal to the false literal.*);
    public boolean lemonadeChange(int[] bills) {
        int countFive = 0;
        int countTen = 0;
        for (int bill : bills) {
            if (bill == 5) {
                countFive++;
            } else if (bill == 10) {
                if (countFive == 0) {
                    return false;
                }
                countFive--;
                countTen++;
            } else if (bill == 20) {
                if (countFive > 0 && countTen > 0) {
                    countFive--;
                    countTen--;
                } else if (countFive >= 3) {
                    countFive -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}