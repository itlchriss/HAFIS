// s0053 - Maximum Subarray (Kadane's Algorithm)
// Dafny formal specification (spec-only: maximum contiguous subarray sum)

method maxSubArray(nums: array<int>) returns (result: int)
    requires 1 <= nums.Length <= 100000
    requires forall i: nat :: i < nums.Length ==> -10000 <= nums[i] <= 10000
    // Result is the maximum contiguous subarray sum
    ensures exists lo: nat, hi: nat :: 0 <= lo <= hi <= nums.Length &&
        result == sumRange(nums, lo, hi) &&
        (forall lo2: nat, hi2: nat :: 0 <= lo2 <= hi2 <= nums.Length ==>
            sumRange(nums, lo2, hi2) <= result)
{
    result := 0;
    assume false;  // spec-only
}

function sumRange(nums: array<int>, lo: nat, hi: nat): int
    requires 0 <= lo <= hi <= nums.Length
    reads nums
{
    sumRangeHelper(nums, lo, hi, 0)
}

function sumRangeHelper(nums: array<int>, lo: nat, hi: nat, acc: int): int
    requires 0 <= lo <= hi <= nums.Length
    reads nums
    decreases hi - lo
{
    if lo == hi then acc
    else sumRangeHelper(nums, lo + 1, hi, acc + nums[lo])
}
