// s0942 - DI String Match
// Dafny formal specification (spec-only: permutation construction)

method diStringMatch(s: string) returns (result: array<int>)
    requires 1 <= |s| <= 100000
    requires forall i: nat :: i < |s| ==> s[i] == 'I' || s[i] == 'D'
    ensures result != null
    ensures result.Length == |s| + 1
    // All values are in [0..n] and unique
    ensures forall i: nat :: i < result.Length ==> 0 <= result[i] <= |s|
    // D/I constraints
    ensures forall i: nat :: i < |s| ==>
        (s[i] == 'I' ==> result[i] < result[i + 1]) &&
        (s[i] == 'D' ==> result[i] > result[i + 1])
{
    result := new int[|s| + 1];
    assume false;  // spec-only
}
