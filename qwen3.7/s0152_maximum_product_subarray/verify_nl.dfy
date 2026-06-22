// s0152 - Maximum Product Subarray
// Dafny formal specification (spec-only: maximum contiguous subarray product)

method maxProduct(arr: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `arr` is greater than or equal to 1 and is less than or equal to 20000.*);
    // requires(*All values in the integer array parameter `arr` are greater than or equal to -10 and are less than or equal to 10.*);
    // requires(*The integer array parameter `arr` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
    // ensures(*The integer result is the maximum product of a contiguous non-empty subarray of the integer array parameter `arr`.*);
    // ensures(*If the integer array parameter `arr` is equal to [2,3,-2,4], the integer result is equal to 6.*);
    // ensures(*If the integer array parameter `arr` is equal to [-2,0,-1], the integer result is equal to 0.*);
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
