// s0007 - Reverse Integer
// Dafny formal specification (spec-only: digit reversal with overflow)

method reverse(x: int) returns (result: int)
{
    // requires(*The integer parameter `x` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*If the integer parameter `x` is equal to 123, the integer result is equal to 321.*);
    // ensures(*If the integer parameter `x` is equal to -123, the integer result is equal to -321.*);
    // ensures(*If the integer parameter `x` is equal to 120, the integer result is equal to 21.*);
    // ensures(*If the integer parameter `x` is equal to 0, the integer result is equal to 0.*);
    // ensures(*If the reversed digits of the integer parameter `x` cause the value to go outside the signed 32-bit integer range, the integer result is equal to 0.*);
    result := 0;
    assume false;  // spec-only
}
