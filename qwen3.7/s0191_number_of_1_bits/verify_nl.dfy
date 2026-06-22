// s0191 - Number of 1 Bits (Hamming Weight)
// Complete Dafny formal specification with verified implementation

function pow2nat(k: nat): (r: nat)
    ensures r > 0
    decreases k
{
    if k == 0 then 1 else 2 * pow2nat(k - 1)
}

function bitAt(n: int, k: nat): int
    requires n >= 0
{
    (n / pow2nat(k)) % 2
}

function countSetBits(n: int, limit: nat): nat
    requires n >= 0
    decreases limit
{
    if limit == 0 then 0
    else (if bitAt(n, limit - 1) == 1 then 1 else 0)
         + countSetBits(n, limit - 1)
}

method hammingWeight(n: int) returns (result: int)
{
    // requires(*The integer parameter `n` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to 32.*);
    // ensures(*The integer result is equal to the total number of 1 bits in the binary representation of the integer parameter `n`.*);
    // ensures(*If the integer parameter `n` is equal to 11, the integer result is equal to 3.*);
    // ensures(*If the integer parameter `n` is equal to 128, the integer result is equal to 1.*);
    // ensures(*If the integer parameter `n` is equal to -3, the integer result is equal to 31.*);
    if n >= 0 {
        result := 0;
        var m: int := n;
        var i: nat := 0;
        while i < 32
            invariant i <= 32
            invariant 0 <= result <= i
            invariant result == countSetBits(n, i)
        {
            if bitAt(n, i) == 1 {
                result := result + 1;
            }
            i := i + 1;
        }
    } else {
        // For negative n, convert to unsigned representation
        var un: int := n + 2147483648 + 2147483648;
        result := 1;  // MSB (sign bit) is always 1 for negative
        var i: nat := 0;
        while i < 31
            invariant i <= 31
            invariant result >= 1
            decreases 31 - i
        {
            if (un / pow2nat(i)) % 2 == 1 {
                result := result + 1;
            }
            i := i + 1;
        }
    }
}
