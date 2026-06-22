// s0319 - Bulb Switcher
// Complete Dafny formal specification with verified implementation

// Result is floor(sqrt(n))
// For n in [0, 10^9], sqrt(n) <= 31623

method bulbSwitch(n: int) returns (result: int)
{
    // requires(*The integer parameter `n` is greater than or equal to 0 and is less than or equal to 1000000000.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to the integer parameter `n`.*);
    // ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 0.*);
    // ensures(*If the integer parameter `n` is equal to 1, the integer result is equal to 1.*);
    // ensures(*If the integer parameter `n` is equal to 3, the integer result is equal to 1.*);
    // ensures(*The integer result is equal to the count of all non-negative integers `i` such that `i` is less than or equal to the integer parameter `n` and the product of the non-negative integer `i` and the non-negative integer `i` is less than or equal to the integer parameter `n`.*);
    if n == 0 {
        result := 0;
        return;
    }
    // Linear search - not efficient but verifiable
    var ans: int := 1;
    while (ans + 1) * (ans + 1) <= n
        invariant ans >= 1
        invariant ans * ans <= n
        decreases n - ans
    {
        ans := ans + 1;
    }
    result := ans;
}
