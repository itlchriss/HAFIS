// s0860 - Lemonade Change
// Dafny formal specification (spec-only: greedy change-making)
method lemonadeChange(bills: array<int>) returns (result: bool)
{
    // requires(*The length of the integer array parameter `bills` is greater than or equal to 1 and is less than or equal to 100000.*);
    // requires(*The integer array parameter `bills` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `bills` are 5 or 10 or 20.*);
    // ensures(*The boolean result is equal to the true literal if and only if correct change can be provided to every customer.*);
    // ensures(*If the integer array parameter `bills` is equal to [5,5,5,10,20], the boolean result is equal to the true literal.*);
    // ensures(*If the integer array parameter `bills` is equal to [5,5,10,10,20], the boolean result is equal to the false literal.*);
    result := false;
    assume false;
}
