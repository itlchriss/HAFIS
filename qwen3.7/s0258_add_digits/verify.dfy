// s0258 - Add Digits (Digital Root)
// Complete Dafny formal specification with verified implementation

// The digital root of a non-negative integer
// Repeatedly summing digits until a single digit remains
function digitSum(n: nat): nat
    decreases n
{
    if n < 10 then n else (n % 10) + digitSum(n / 10)
}

lemma digitSum_mod9(n: nat)
    requires n > 0
    ensures digitSum(n) % 9 == n % 9
    decreases n
{
    if n >= 10 {
        digitSum_mod9(n / 10);
        assert n == (n / 10) * 10 + n % 10;
        assert n % 9 == ((n / 10) * 10 + n % 10) % 9;
        assert ((n / 10) * 10 + n % 10) % 9 == ((n / 10) % 9 + n % 10 % 9) % 9;
        assert ((n / 10) % 9 + n % 10 % 9) % 9 == ((n / 10) + n % 10) % 9;
        assert digitSum(n) == (n % 10) + digitSum(n / 10);
    }
}

method addDigits(num: int) returns (result: int)
    requires 0 <= num <= 2147483647
    ensures 0 <= result <= 9
    ensures num == 0 ==> result == 0
    ensures num > 0 && num % 9 == 0 ==> result == 9
    ensures num > 0 && num % 9 != 0 ==> result == num % 9
{
    if num == 0 {
        result := 0;
        return;
    }
    if num % 9 == 0 {
        result := 9;
        return;
    }
    result := num % 9;
    return;
}
