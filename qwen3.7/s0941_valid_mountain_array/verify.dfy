// s0941 - Valid Mountain Array
// Dafny formal specification (spec-only: structural array property)

method validMountainArray(arr: array<int>) returns (result: bool)
    requires 1 <= arr.Length <= 10000
    requires forall i: nat :: i < arr.Length ==> 0 <= arr[i] <= 10000
    ensures arr.Length < 3 ==> !result
    // Mountain: strictly increasing then strictly decreasing, with peak not at ends
    ensures arr.Length >= 3 ==> (result <==>
        (exists peak: nat :: 0 < peak < arr.Length - 1 &&
            (forall i: nat :: i < peak ==> arr[i] < arr[i + 1]) &&
            (forall i: nat :: peak <= i < arr.Length - 1 ==> arr[i] > arr[i + 1])))
{
    result := false;
    assume false;  // spec-only
}
