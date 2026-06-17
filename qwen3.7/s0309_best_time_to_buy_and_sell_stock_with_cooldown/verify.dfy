// s0309 - Best Time to Buy and Sell Stock with Cooldown
// Dafny formal specification (spec-only)
method maxProfit(prices: array<int>) returns (result: int)
    requires 1 <= prices.Length <= 5000
    requires forall i: nat :: i < prices.Length ==> 0 <= prices[i] <= 1000
    ensures result >= 0
{
    result := 0;
    assume false;
}
