// s0461 - Hamming Distance
// Complete Dafny formal specification with verified implementation

// The Hamming distance between two integers is the number of positions
// at which the corresponding bits are different.

function pow2(k: nat): int
    decreases k
{
    if k == 0 then 1 else 2 * pow2(k - 1)
}

function pow2nat(k: nat): (r: nat)
    ensures r > 0
    decreases k
{
    if k == 0 then 1 else 2 * pow2nat(k - 1)
}

lemma pow2_positive(k: nat)
    ensures pow2(k) > 0
{
    if k > 0 { pow2_positive(k - 1); }
}

// Get bit at position k of integer n
function bitAt(n: int, k: nat): int
    requires n >= 0
{
    (n / pow2nat(k)) % 2
}

// Count differing bits in positions 0..limit-1 between x and y
function countDiffBits(x: int, y: int, limit: nat): nat
    requires x >= 0
    requires y >= 0
    decreases limit
{
    if limit == 0 then 0
    else (if bitAt(x, limit - 1) != bitAt(y, limit - 1) then 1 else 0)
         + countDiffBits(x, y, limit - 1)
}

method hammingDistance(x: int, y: int) returns (result: int)
    requires 0 <= x <= 2147483647
    requires 0 <= y <= 2147483647
    ensures 0 <= result <= 31
    ensures result == countDiffBits(x, y, 31)
{
    result := 0;
    var i: nat := 0;
    while i < 31
        invariant i <= 31
        invariant 0 <= result <= i
        invariant result == countDiffBits(x, y, i)
    {
        if bitAt(x, i) != bitAt(y, i) {
            result := result + 1;
        }
        i := i + 1;
    }
}
