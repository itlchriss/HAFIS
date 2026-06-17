// s0367 - Valid Perfect Square
// Complete Dafny formal specification with verified implementation

method isPerfectSquare(num: int) returns (result: bool)
    requires 1 <= num <= 2147483647
    ensures result <==> (exists r: nat :: r * r == num)
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
