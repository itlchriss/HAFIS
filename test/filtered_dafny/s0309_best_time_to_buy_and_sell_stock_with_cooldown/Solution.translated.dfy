// #Medium #Array #Dynamic_Programming #Dynamic_Programming_I_Day_8
// #2022_07_07_Time_0_ms_(100.00%)_Space_42.3_MB_(44.85%)
// Dafny version of Solution

requires (prices.length <= 5000) && (prices.length >= 1)
requires (prices.length <= 5000) && (prices.length >= 1)
requires (prices.size() <= 5000) && (prices.size() >= 1)
requires (prices.length() <= 5000) && (prices.length() >= 1)
requires (prices.size() <= 5000) && (prices.size() >= 1)
requires (\forall int i; 0 <= i < prices.length; prices[i] <= 1000) && (\forall int i; 0 <= i < prices.length; prices[i] >= 0)
ensures (\result <= 2147483647) && (\result >= -2147483648)
// ensures(*If the integer array parameter `prices` is equal to [1,2,3,0,2], the integer result is equal to 3.*);
// ensures(*If the integer array parameter `prices` is equal to [1], the integer result is equal to 0.*);
    method maxProfit(prices: array<int>) returns (result: int)
    {
        var sell: int := 0;
        var prevSell: int := 0;
        var buy: int := -2147483648;
        var prevBuy: int;
        for price in prices
        {
            prevBuy := buy;
            buy := max(prevSell - price, prevBuy);
            prevSell := sell;
            sell := max(prevBuy + price, prevSell);
        }
        result := sell;
        return;
    }
