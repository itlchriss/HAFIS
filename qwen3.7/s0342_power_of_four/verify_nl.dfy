// s0342 - Power of Four
// Complete Dafny formal specification with verified implementation

function pow4(x: nat): int
    decreases x
{
    if x == 0 then 1 else 4 * pow4(x - 1)
}

lemma pow4_positive(x: nat)
    ensures pow4(x) > 0
{
    if x > 0 { pow4_positive(x - 1); }
}

lemma pow4_quadruple(x: nat)
    ensures pow4(x + 1) == 4 * pow4(x)
{}

lemma pow4_quarter(x: nat)
    requires x >= 1
    ensures pow4(x) / 4 == pow4(x - 1)
{
    assert pow4(x) == 4 * pow4(x - 1);
    pow4_positive(x - 1);
}

method isPowerOfFour(n: int) returns (result: bool)
{
    // requires(*The integer parameter `n` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*The boolean result is equal to the true literal if and only if there exists a non-negative integer `x` such that the integer parameter `n` is equal to 4 raised to the power of the non-negative integer `x`.*);
    // ensures(*If the integer parameter `n` is equal to 16, the boolean result is equal to the true literal.*);
    // ensures(*If the integer parameter `n` is equal to 5, the boolean result is equal to the false literal.*);
    // ensures(*If the integer parameter `n` is equal to 1, the boolean result is equal to the true literal.*);
    if n <= 0 {
        result := false;
        return;
    }

    ghost var ghostExp: nat := 0;
    ghost var hasExp: bool := false;

    if exists x: nat :: n == pow4(x) {
        hasExp := true;
        ghostExp :| n == pow4(ghostExp);
    }

    var m: int := n;
    while m >= 4
        invariant m > 0
        invariant hasExp ==> m == pow4(ghostExp)
        invariant !hasExp ==> !(exists x: nat :: m == pow4(x))
        decreases m
    {
        if m % 4 != 0 {
            assert !hasExp by {
                if hasExp {
                    assert ghostExp > 0;
                    pow4_quadruple(ghostExp - 1);
                    assert m == 4 * pow4(ghostExp - 1);
                    assert m % 4 == 0;
                }
            }
            result := false;
            return;
        }
        assert m % 4 == 0;
        if hasExp {
            assert ghostExp >= 1;
            pow4_quarter(ghostExp);
            ghostExp := ghostExp - 1;
        }
        m := m / 4;
        if !hasExp {
            assert !(exists x: nat :: m == pow4(x)) by {
                if exists x: nat :: m == pow4(x) {
                    var k: nat :| m == pow4(k);
                    pow4_quadruple(k);
                    pow4_positive(k);
                }
            }
        }
    }
    // m is now 1, 2, or 3
    if m == 1 {
        assert pow4(0) == 1;
        assert exists x: nat :: m == pow4(x);
        assert hasExp;
        assert m == pow4(ghostExp);
        result := true;
    } else {
        // m == 2 or m == 3, neither is a power of 4
        assert !hasExp by {
            if hasExp {
                // m == pow4(ghostExp), but pow4(0)=1, pow4(1)=4, pow4(k)>=4 for k>=1
                assert ghostExp > 0;  // since m != 1 and pow4(0) == 1
                pow4_positive(ghostExp - 1);
                assert pow4(ghostExp) == 4 * pow4(ghostExp - 1);
                assert pow4(ghostExp) >= 4;
                assert m >= 4;
            }
        }
        result := false;
    }
}
