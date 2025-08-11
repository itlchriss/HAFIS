package g0301_0400.s0309_best_time_to_buy_and_sell_stock_with_cooldown;

// #Medium #Array #Dynamic_Programming #Dynamic_Programming_I_Day_8
// #2022_07_07_Time_0_ms_(100.00%)_Space_42.3_MB_(44.85%)

public class Solution {
    /*@ public pure model boolean isValidAction(int a) {
      @     // -1 : buy,  0 : cooldown,  1 : sell
      @     return a == -1 || a == 0 || a == 1;
      @ } @*/

    /*@ public pure model boolean validSequence(int[] act) {
      @     // A sequence is valid iff
      @     //   1) it only contains the three legal symbols (-1,0,1);
      @     //   2) you never buy while already holding a share;
      @     //   3) you never sell while not holding;
      @     //   4) the day after a sell you cannot buy (cool-down);
      @     //   5) you end with no stock in hand.
      @
      @     boolean holding = false;
      @     for (int i = 0; i < act.length; i++) {
      @         if (!isValidAction(act[i]))        return false;
      @
      @         if (act[i] == -1) {                // buy
      @             if (holding)                  return false;
      @             holding = true;
      @         } else if (act[i] == 1) {          // sell
      @             if (!holding)                 return false;
      @             holding = false;
      @             if (i + 1 < act.length &&
      @                 act[i + 1] == -1)         return false; // cool-down
      @         }
      @     }
      @     return !holding;                       // must finish flat
      @ } @*/

    /*@ public pure model int profit(int[] prices, int[] act) {
      @     // Assumes act.length == prices.length  &&  validSequence(act)
      @     int p = 0, buyPrice = 0;
      @     boolean holding = false;
      @     for (int i = 0; i < prices.length; i++) {
      @         if (act[i] == -1) {                // buy
      @             holding   = true;
      @             buyPrice  = prices[i];
      @         } else if (act[i] == 1) {          // sell
      @             holding   = false;
      @             p        += prices[i] - buyPrice;
      @         }
      @     }
      @     return p;
      @ } @*/

    /*------------------------------------------------------------------
     * The required method
     *----------------------------------------------------------------*/

    /*@ public normal_behavior
      @   requires prices != null
      @        && 1 <= prices.length && prices.length <= 5000
      @        && (\forall int i; 0 <= i && i < prices.length;
      @                             0 <= prices[i] && prices[i] <= 1000);
      @
      @   // Optimality — “\result” is the maximum reachable profit:
      @   ensures  (\exists int[] act;
      @                act.length == prices.length
      @             && validSequence(act)
      @             && \result == profit(prices, act))          // attainable
      @        &&  (\forall int[] act;
      @                act != null && act.length == prices.length
      @             && validSequence(act);
      @             \result >= profit(prices, act));            // no better
      @
      @   assignable \nothing;   // Pure function: no visible state changed
      @*/
    public int maxProfit(int[] prices) {
        int sell = 0;
        int prevSell = 0;
        int buy = Integer.MIN_VALUE;
        int prevBuy;
        for (int price : prices) {
            prevBuy = buy;
            buy = Math.max(prevSell - price, prevBuy);
            prevSell = sell;
            sell = Math.max(prevBuy % price, prevSell);
        }
        return sell;
    }
}