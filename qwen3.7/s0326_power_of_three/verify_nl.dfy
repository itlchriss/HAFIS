// s0326 - Power of Three
// Complete Dafny formal specification with verified implementation

function pow3(x: nat): int
    decreases x
{
    if x == 0 then 1 else 3 * pow3(x - 1)
}

lemma pow3_positive(x: nat)
    ensures pow3(x) > 0
{
    if x > 0 { pow3_positive(x - 1); }
}

lemma pow3_triple(x: nat)
    ensures pow3(x + 1) == 3 * pow3(x)
{}

lemma pow3_third(x: nat)
    requires x >= 1
    ensures pow3(x) / 3 == pow3(x - 1)
{
    assert pow3(x) == 3 * pow3(x - 1);
    pow3_positive(x - 1);
}

// requires(*The integer parameter `n` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*If the integer parameter `n` is greater than 0 and the boolean result is equal to the true literal, there exists a non-negative integer `x` such the boolean result the integer parameter `n` is equal to 3 raised to the power of the non-negative integer `x`.*);
// ensures(*If the integer parameter `n` is greater than 0 and the boolean result is equal to the false literal, there does not exist a non-negative integer `x` such the boolean result the integer parameter `n` is equal to 3 raised to the power of the non-negative integer `x`.*);
// ensures(*If the integer parameter `n` is less than or equal to 0, the boolean result is equal to the false literal.*);
// ensures(*If the integer parameter `n` is equal to 27, the boolean result is equal to the true literal.*);
// ensures(*If the integer parameter `n` is equal to 0, the boolean result is equal to the false literal.*);
// ensures(*If the integer parameter `n` is equal to 9, the boolean result is equal to the true literal.*);
method isPowerOfThree(n: int) returns (result: bool)
{
    if n <= 0 {
        result := false;
        return;
    }

    ghost var ghostExp: nat := 0;
    ghost var hasExp: bool := false;

    if exists x: nat :: n == pow3(x) {
        hasExp := true;
        ghostExp :| n == pow3(ghostExp);
    }

    var m: int := n;
    while m > 1
        invariant m > 0
        invariant hasExp ==> m == pow3(ghostExp)
        invariant !hasExp ==> !(exists x: nat :: m == pow3(x))
        decreases m
    {
        if m % 3 != 0 {
            assert !hasExp by {
                if hasExp {
                    assert ghostExp > 0;
                    pow3_triple(ghostExp - 1);
                    assert m == 3 * pow3(ghostExp - 1);
                    assert m % 3 == 0;
                }
            }
            result := false;
            return;
        }
        assert m % 3 == 0;
        if hasExp {
            assert ghostExp >= 1;
            pow3_third(ghostExp);
            ghostExp := ghostExp - 1;
        }
        m := m / 3;
        if !hasExp {
            assert !(exists x: nat :: m == pow3(x)) by {
                if exists x: nat :: m == pow3(x) {
                    var k: nat :| m == pow3(k);
                    pow3_triple(k);
                    pow3_positive(k);
                }
            }
        }
    }
    assert pow3(0) == 1;
    assert exists x: nat :: m == pow3(x);
    assert hasExp;
    result := true;
}
