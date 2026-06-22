// s0233 - Number of Digit One
// Dafny formal specification (spec-only)
method countDigitOne(n: int) returns (result: int)
{
    // requires(*The integer parameter `n` is greater than or equal to 0 and is less than or equal to 1000000000.*);
    // ensures(*The integer result is greater than or equal to 0.*);
    // ensures(*If the integer parameter `n` is equal to 13, the integer result is equal to 6.*);
    // ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 0.*);
    // ensures(*The integer result is equal to the total number of digit 1 appearing in all non-negative integers less than or equal to the integer parameter `n`.*);
    result := 0;
    assume false;
}
