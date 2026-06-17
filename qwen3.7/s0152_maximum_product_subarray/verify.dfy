// s0152 - Maximum Product Subarray
// Dafny formal specification (spec-only: maximum contiguous subarray product)

method maxProduct(arr: array<int>) returns (result: int)
    requires 1 <= arr.Length <= 20000
    requires forall i: nat :: i < arr.Length ==> -10 <= arr[i] <= 10
    // Result is the maximum contiguous subarray product
    ensures exists lo: nat, hi: nat :: 0 <= lo < hi <= arr.Length &&
        result == prodRange(arr, lo, hi) &&
        (forall lo2: nat, hi2: nat :: 0 <= lo2 < hi2 <= arr.Length ==>
            prodRange(arr, lo2, hi2) <= result)
{
    result := 0;
    assume false;  // spec-only
}

function prodRange(arr: array<int>, lo: nat, hi: nat): int
    requires 0 <= lo < hi <= arr.Length
    reads arr
{
    prodRangeHelper(arr, lo, hi, 1)
}

function prodRangeHelper(arr: array<int>, lo: nat, hi: nat, acc: int): int
    requires 0 <= lo <= hi <= arr.Length
    reads arr
    decreases hi - lo
{
    if lo == hi then acc
    else prodRangeHelper(arr, lo + 1, hi, acc * arr[lo])
}
