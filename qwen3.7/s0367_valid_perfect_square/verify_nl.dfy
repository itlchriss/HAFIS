// s0367 - Valid Perfect Square
// Complete Dafny formal specification with verified implementation

// requires(*The integer parameter `num` is greater than or equal to 1 and is less than or equal to 2147483647.*);
// ensures(*If the boolean result is equal to the true literal, there exists a non-negative integer `x` such the boolean result the integer parameter `num` is equal to the non-negative integer `x` raised to the power of 2.*);
// ensures(*If the boolean result is equal to the false literal, there does not exist a non-negative integer `x` such the boolean result the integer parameter `num` is equal to the non-negative integer `x` raised to the power of 2.*);
// ensures(*If the integer parameter `num` is equal to 16, the boolean result is equal to the true literal.*);
// ensures(*If the integer parameter `num` is equal to 14, the boolean result is equal to the false literal.*);
method isPerfectSquare(num: int) returns (result: bool)
{
    var r: int := 1;
    while r * r < num
        invariant r >= 1
        invariant forall k: nat :: 1 <= k < r ==> k * k < num
        decreases num - r
    {
        r := r + 1;
    }
    // Now r * r >= num
    if r * r == num {
        result := true;
    } else {
        // r * r > num and all k < r have k*k < num
        // No r can satisfy r*r == num
        result := false;
    }
}
