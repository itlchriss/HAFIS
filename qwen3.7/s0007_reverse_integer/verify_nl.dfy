// s0007 - Reverse Integer
// Dafny formal specification (spec-only: digit reversal with overflow)

// requires(*The integer parameter `x` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*If reversing the digits of the integer parameter `x` causes the result to go outside the signed 32-bit integer range, the integer result is equal to 0.*);
// ensures(*If reversing the digits of the integer parameter `x` does not cause the result to go outside the signed 32-bit integer range, the integer result is equal to the reversed digits of the integer parameter `x`.*);
// ensures(*If the integer parameter `x` is equal to 123, the integer result is equal to 321.*);
// ensures(*If the integer parameter `x` is equal to -123, the integer result is equal to -321.*);
// ensures(*If the integer parameter `x` is equal to 120, the integer result is equal to 21.*);
// ensures(*If the integer parameter `x` is equal to 0, the integer result is equal to 0.*);
method reverse(x: int) returns (result: int)
{
    result := 0;
    assume false;  // spec-only
}
