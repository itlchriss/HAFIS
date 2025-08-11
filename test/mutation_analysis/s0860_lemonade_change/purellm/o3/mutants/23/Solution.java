package g0801_0900.s0860_lemonade_change;

 //#Easy #Array #Greedy #Programming_Skills_II_Day_17
 //#2022_03_27_Time_2_ms_(90.84%)_Space_75.8_MB_(55.09%)

public class Solution {
    /*@
      @
      @ requires bills != null;                          
      @ requires 1 <= bills.length && bills.length <= 100_000;
      @ requires (\forall int i; 0 <= i < bills.length;  
      @                         bills[i] == 5  ||
      @                         bills[i] == 10 ||
      @                         bills[i] == 20);
      @
      @
      @  ensures \result <==>                                          
      @            (\exists int[] fives, tens;                         
      @                     fives.length == bills.length + 1 &&
      @                      tens.length  == bills.length + 1 &&
      @                             fives[0] == 0 && tens[0] == 0 &&
      @                     (\forall int i; 0 <= i < bills.length;
      @                         (bills[i] == 5  &&
      @                                 fives[i]   >= 0               && tens[i]   >= 0 &&
      @                                           fives[i+1] == fives[i] + 1    && tens[i+1] == tens[i]) ||
      @                 (bills[i] == 10 &&
      @                                  fives[i]   >= 1               && tens[i]   >= 0 &&
      @                  fives[i+1] == fives[i] - 1    && tens[i+1] == tens[i] + 1) ||
      @                 (bills[i] == 20 &&
      @                     (   
      @                       (tens[i]  >= 1 && fives[i] >= 1 &&
      @                                                     fives[i+1] == fives[i] - 1 &&
      @                        tens[i+1]  == tens[i]  - 1) &&
      @                        (tens[i]  == 0 && fives[i] >= 3 &&
      @                       fives[i+1] == fives[i] - 3 &&
      @                        tens[i+1]  == tens[i])
      @                     )
      @                 )
      @            ) &&
      @                  (\forall int k; 0 <= k <= bills.length;
      @                 fives[k] >= 0 && tens[k] >= 0)
      @           );
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
                if (false) {
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