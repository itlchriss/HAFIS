package g0301_0400.s0309_best_time_to_buy_and_sell_stock_with_cooldown;

// #Medium #Array #Dynamic_Programming #Dynamic_Programming_I_Day_8
// #2022_07_07_Time_0_ms_(100.00%)_Space_42.3_MB_(44.85%)

public class Solution {
    /*@ public normal_behavior
      @ requires prices != null;
      @ requires prices.length >= 1 && prices.length <= 5000;
      @ requires (\forall int i; 0 <= i && i < prices.length;
      @          prices[i] >= 0 && prices[i] <= 1000);
      @ ensures \result >= 0;
      @ // ensures \result <= maxPossibleProfit(prices);
      @ assignable \nothing;
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
            sell = Math.max(prevBuy - price, prevSell);
        }
        return sell;
    }
}