package g0801_0900.s0860_lemonade_change;

// #Easy #Array #Greedy #Programming_Skills_II_Day_17
// #2022_03_27_Time_2_ms_(90.84%)_Space_75.8_MB_(55.09%)

public class Solution {
    /*@ public normal_behavior
      @ requires bills != null;
      @ requires bills.length >= 1 && bills.length <= 100000;
      @ requires (\forall int i; 0 <= i && i < bills.length;
      @           bills[i] == 5 || bills[i] == 10 || bills[i] == 20);
      @ // ensures \result <==> canMakeChangeForAll(bills);
      @ pure
      @*/
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
                if (countTen > 0) {
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