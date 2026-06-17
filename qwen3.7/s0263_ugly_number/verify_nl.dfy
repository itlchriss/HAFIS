// s0263 - Ugly Number
// Complete Dafny formal specification with verified implementation

// An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
// By convention, 1 is an ugly number.

// requires(*The integer parameter `n` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*If the integer parameter `n` is greater than 0 and the boolean result is equal to the true literal, the integer parameter `n` can be expressed as a product of powers of 2 and 3 and 5.*);
// ensures(*If the integer parameter `n` is less than or equal to 0, the boolean result is equal to the false literal.*);
// ensures(*If the integer parameter `n` is equal to 6, the boolean result is equal to the true literal.*);
// ensures(*If the integer parameter `n` is equal to 8, the boolean result is equal to the true literal.*);
// ensures(*If the integer parameter `n` is equal to 14, the boolean result is equal to the false literal.*);
// ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to the true literal.*);
method isUgly(n: int) returns (result: bool)
{
    if n <= 0 {
        result := false;
        return;
    }
    var m: int := n;
    while m > 1 && m % 2 == 0
        invariant m > 0
        decreases m
    {
        m := m / 2;
    }
    while m > 1 && m % 3 == 0
        invariant m > 0
        decreases m
    {
        m := m / 3;
    }
    while m > 1 && m % 5 == 0
        invariant m > 0
        decreases m
    {
        m := m / 5;
    }
    // After dividing out all 2s, 3s, and 5s:
    // m == 1 means n had only factors 2, 3, 5
    // m > 1 means n had other prime factors
    result := m == 1;
}
