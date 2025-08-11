package g0801_0900.s0860_lemonade_change;

// #Easy #Array #Greedy #Programming_Skills_II_Day_17
// #2022_03_27_Time_2_ms_(90.84%)_Space_75.8_MB_(55.09%)

public class Solution {
//@ requires(*All values in the integer array parameter `bills` are equal to 5 or are equal to 10 or are equal to 20.*);
//@ ensures(*If boolean result is equal to the true literal, each customer represented by the integer array parameter `bills` receives correct change when the price of one lemonade is equal to 5.*);
//@ ensures(*If boolean result is equal to the false literal, at least one customer represented by the integer array parameter `bills` does not receive correct change when the price of one lemonade is equal to 5.*);
//@ ensures(*If the integer array parameter `bills` is equal to [5,5,5,10,20], boolean result is equal to the true literal.*);
//@ ensures(*If the integer array parameter `bills` is equal to [5,5,10,10,20], boolean result is equal to the false literal.*);
//@ ensures(*Boolean result is equal to the true literal only if for every processed prefix of the integer array parameter `bills` the cumulative value of collected bills minus five times the number of processed customers is greater than or equal to zero.*);
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