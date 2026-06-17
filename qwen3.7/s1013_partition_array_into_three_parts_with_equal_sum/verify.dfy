// s1013 - Partition Array Into Three Parts With Equal Sum
// Dafny formal specification (spec-only)
method canThreePartsEqualSum(arr: array<int>) returns (result: bool)
    requires 3 <= arr.Length <= 100000
    requires forall i: nat :: i < arr.Length ==> -10000 <= arr[i] <= 10000
{
    result := false;
    assume false;
}
