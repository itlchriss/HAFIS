package g0801_0900.s0860_lemonade_change;

// #Easy #Array #Greedy #Programming_Skills_II_Day_17
// #2022_03_27_Time_2_ms_(90.84%)_Space_75.8_MB_(55.09%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((bills.length <= 100000) && (bills.length >= 1));
//@ requires((\forall int i; 0 <= i < bills.length; bills[i] == 5) || ((\forall int i; 0 <= i < bills.length; bills[i] == 10) || (\forall int i; 0 <= i < bills.length; bills[i] == 20)));
//@ ensures((bills[0] == 5 && bills[1] == 5 && bills[2] == 5 && bills[3] == 10 && bills[4] == 20 && bills.length == 5) ==> (\result == true));
//@ ensures((bills[0] == 5 && bills[1] == 5 && bills[2] == 10 && bills[3] == 10 && bills[4] == 20 && bills.length == 5) ==> (\result == false));
    public boolean lemonadeChange(int[] bills) {
        int countFive = 0;
        int countTen = 0;
        for (int bill : bills) {
            if (bill == 5) {
                countFive++;
            } else if (false) {
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
