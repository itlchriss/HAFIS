// s0415 - Add Strings
// Dafny formal specification (spec-only)
method addStrings(num1: string, num2: string) returns (result: string)
    requires 1 <= |num1| <= 10000
    requires 1 <= |num2| <= 10000
    requires forall i: nat :: i < |num1| ==> num1[i] >= '0' && num1[i] <= '9'
    requires forall i: nat :: i < |num2| ==> num2[i] >= '0' && num2[i] <= '9'
    ensures |result| >= 1
    ensures forall i: nat :: i < |result| ==> result[i] >= '0' && result[i] <= '9'
{
    result := "0";
    assume false;
}
