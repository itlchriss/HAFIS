// s0309 - Best Time to Buy and Sell Stock with Cooldown
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `prices` is greater than or equal to 1 and is less than or equal to 5000.*);
// requires(*All values in the integer array parameter `prices` are greater than or equal to 0 and are less than or equal to 1000.*);
// requires(*The integer array parameter `prices` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is the maximum profit achievable by buying and selling stocks with a cooldown period of one day after each sell.*);
// ensures(*If the integer array parameter `prices` is equal to [1,2,3,0,2], the integer result is equal to 3.*);
// ensures(*If the integer array parameter `prices` is equal to [1], the integer result is equal to 0.*);
method maxProfit(prices: array<int>) returns (result: int)
{
    result := 0;
    assume false;
}
