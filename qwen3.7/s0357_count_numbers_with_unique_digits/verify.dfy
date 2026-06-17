// s0357 - Count Numbers with Unique Digits
// Dafny formal specification (spec-only)
method countNumbersWithUniqueDigits(n: int) returns (result: int)
    requires 0 <= n <= 8
    ensures result >= 1
    ensures n == 0 ==> result == 1
    ensures n == 1 ==> result == 10
{
    result := 1;
    assume false;
}
