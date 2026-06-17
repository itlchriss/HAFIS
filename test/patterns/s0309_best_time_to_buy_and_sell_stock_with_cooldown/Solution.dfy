// #Medium #Array #Dynamic_Programming #Dynamic_Programming_I_Day_8
// #2022_07_07_Time_0_ms_(100.00%)_Space_42.3_MB_(44.85%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `prices` is less than or equal to 5000 and is greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `prices` are less than or equal to 1000 and are greater than or equal to 0.*);
// ensures(*The integer result is less than or equal to the maximum value of java integer and is greater than or equal to the minimum value of java integer.*);
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
