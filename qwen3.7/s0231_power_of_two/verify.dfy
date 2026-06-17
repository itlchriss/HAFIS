// s0231 - Power of Two
// Complete Dafny formal specification with verified implementation

// Ghost function: 2^x
function pow2(x: nat): int
    decreases x
{
    if x == 0 then 1 else 2 * pow2(x - 1)
}

lemma pow2_positive(x: nat)
    ensures pow2(x) > 0
{
    if x > 0 { pow2_positive(x - 1); }
}

lemma pow2_even_for_positive(x: nat)
    requires x >= 1
    ensures pow2(x) % 2 == 0
{
    assert pow2(x) == 2 * pow2(x - 1);
}

lemma pow2_double(x: nat)
    ensures pow2(x + 1) == 2 * pow2(x)
{}

lemma pow2_half(x: nat)
    requires x >= 1
    ensures pow2(x) / 2 == pow2(x - 1)
{
    assert pow2(x) == 2 * pow2(x - 1);
    pow2_positive(x - 1);
}

lemma pow2_strictly_increasing(x: nat, y: nat)
    requires x < y
    ensures pow2(x) < pow2(y)
    decreases y - x
{
    if y == x + 1 {
        pow2_positive(x);
    } else {
        pow2_strictly_increasing(x, y - 1);
        pow2_strictly_increasing(y - 1, y);
    }
}

method isPowerOfTwo(n: int) returns (result: bool)
    ensures n > 0 ==> (result <==> (exists x: nat :: n == pow2(x)))
    ensures n <= 0 ==> !result
{
    if n <= 0 {
        result := false;
        return;
    }

    // Use ghost variable to track the exponent relationship
    ghost var ghostExp: nat := 0;
    ghost var hasExp: bool := false;

    // If n is a power of two, find its exponent
    if exists x: nat :: n == pow2(x) {
        hasExp := true;
        ghostExp :| n == pow2(ghostExp);
    }

    var m: int := n;
    while m > 1
        invariant m > 0
        invariant hasExp ==> m == pow2(ghostExp)
        invariant !hasExp ==> !(exists x: nat :: m == pow2(x))
        decreases m
    {
        if m % 2 != 0 {
            // m is odd and > 1, so m cannot be a power of two
            assert !(exists x: nat :: m == pow2(x)) by {
                forall x: nat | x >= 1 {
                    pow2_even_for_positive(x);
                }
            }
            result := false;
            return;
        }
        // m is even, divide by 2
        assert m % 2 == 0;

        if hasExp {
            // m = pow2(ghostExp), ghostExp >= 1 (since m > 1)
            assert ghostExp >= 1;
            pow2_half(ghostExp);
            ghostExp := ghostExp - 1;
        }
        m := m / 2;

        // After division, if !hasExp, m/2 is also not a power of two
        if !hasExp {
            assert !(exists x: nat :: m == pow2(x)) by {
                // If m/2 = pow2(k), then old_m = m*2 = pow2(k+1), contradiction
                if exists x: nat :: m == pow2(x) {
                    var k: nat :| m == pow2(k);
                    pow2_double(k);
                    pow2_positive(k);
                    // old_m = m * 2 = pow2(k) * 2 = pow2(k+1)
                    // But we know old_m was not a power of two, contradiction
                }
            }
        }
    }
    // m == 1 here
    assert m == 1;
    // Since pow2(0) = 1 = m, the existential holds, so !hasExp leads to contradiction
    assert pow2(0) == 1;
    assert exists x: nat :: m == pow2(x);
    // Therefore hasExp must be true
    assert hasExp;
    assert m == pow2(ghostExp);
    result := true;
}
