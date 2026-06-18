// s0860 - Lemonade Change
// Dafny formal specification (spec-only: greedy change-making)
// requires(*The length of the integer array parameter `bills` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*All values in the integer array parameter `bills` are either 5 or 10 or 20.*);
// requires(*The integer array parameter `bills` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, for every customer from index 0 to the length of the integer array parameter `bills` minus 1, the correct change can be provided using only the bills collected from customers before the boolean result customer.*);
// ensures(*If the boolean result is equal to the false literal, there exists at least one customer for the int array parameter `bills` the correct change cannot be provided using only the bills collected from customers before the boolean result customer.*);
// ensures(*If the integer array parameter `bills` is equal to [5,5,5,10,20], the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `bills` is equal to [5,5,10,10,20], the boolean result is equal to the false literal.*);
method lemonadeChange(bills: array<int>) returns (result: bool)
{
    result := false;
    assume false;
}
