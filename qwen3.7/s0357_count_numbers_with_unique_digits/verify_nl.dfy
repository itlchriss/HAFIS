// s0357 - Count Numbers with Unique Digits
// Dafny formal specification (spec-only)
// requires(*The integer parameter `n` is greater than or equal to 0 and is less than or equal to 8.*);
// ensures(*The integer result is greater than or equal to 1.*);
// ensures(*The integer result is equal to the count of all numbers with unique digits `x` where 0 is less than or equal to `x` and `x` is less than 10 raised to the power of the integer parameter `n`.*);
// ensures(*If the integer parameter `n` is equal to 2, the integer result is equal to 91.*);
// ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 1.*);
method countNumbersWithUniqueDigits(n: int) returns (result: int)
{
    result := 1;
    assume false;
}
