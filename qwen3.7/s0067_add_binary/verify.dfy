// s0067 - Add Binary
// Dafny formal specification (spec-only)

method addBinary(a: string, b: string) returns (result: string)
    requires 1 <= |a| <= 10000
    requires 1 <= |b| <= 10000
    requires forall i: nat :: i < |a| ==> a[i] == '0' || a[i] == '1'
    requires forall i: nat :: i < |b| ==> b[i] == '0' || b[i] == '1'
    ensures |result| >= 1
    ensures forall i: nat :: i < |result| ==> result[i] == '0' || result[i] == '1'
{
    result := "0";
    assume false;
}
