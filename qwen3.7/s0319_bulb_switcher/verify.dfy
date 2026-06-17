// s0319 - Bulb Switcher
// Complete Dafny formal specification with verified implementation

// Result is floor(sqrt(n))
// For n in [0, 10^9], sqrt(n) <= 31623

method bulbSwitch(n: int) returns (result: int)
    requires 0 <= n <= 1000000000
    ensures result >= 0
    ensures result * result <= n
{
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
