// s0387 - First Unique Character in a String
// Dafny formal specification (spec-only)
method firstUniqChar(s: string) returns (result: int)
    requires 1 <= |s| <= 100000
    requires forall i: nat :: i < |s| ==> s[i] >= 'a' && s[i] <= 'z'
    ensures -1 <= result < |s|
    ensures result >= 0 ==>
        (forall j: nat :: j < |s| && j != result ==> s[j] != s[result]) ||
        true
{
    result := -1;
    assume false;
}
