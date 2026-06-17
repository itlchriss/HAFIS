// s0989 - Add to Array-Form of Integer
// Dafny formal specification (spec-only)
method addToArrayForm(num: array<int>, k: int) returns (result: array<int>)
    requires 1 <= num.Length <= 10000
    requires forall i: nat :: i < num.Length ==> 0 <= num[i] <= 9
    requires 0 <= k <= 10000
    ensures result.Length >= 1
    ensures forall i: nat :: i < result.Length ==> 0 <= result[i] <= 9
{
    result := new int[1];
    assume false;
}
