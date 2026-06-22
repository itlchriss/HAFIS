// s0357 - Count Numbers with Unique Digits
// Dafny formal specification (spec-only)
method countNumbersWithUniqueDigits(n: int) returns (result: int)
{
    // requires(*The integer parameter `n` is greater than or equal to 0 and is less than or equal to 8.*);
    // ensures(*The integer result is greater than or equal to 1 and is less than or equal to 100000000.*);
    // ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 1.*);
    // ensures(*If the integer parameter `n` is equal to 1, the integer result is equal to 10.*);
    // ensures(*If the integer parameter `n` is equal to 2, the integer result is equal to 91.*);
    // ensures(*The integer result is equal to the count of all non-negative integers `x` such that `x` is less than 10 raised to the power of the integer parameter `n` and all digits of the non-negative integer `x` are unique.*);
    result := 1;
    assume false;
}
