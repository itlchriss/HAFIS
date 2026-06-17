// s0560 - Subarray Sum Equals K
// Dafny formal specification (spec-only: counting subarrays with sum k)

method subarraySum(nums: array<int>, k: int) returns (result: int)
    requires 1 <= nums.Length <= 20000
    requires forall i: nat :: i < nums.Length ==> -1000 <= nums[i] <= 1000
    requires -10000000 <= k <= 10000000
    ensures result >= 0
    // Result equals the count of contiguous subarrays whose sum equals k
    ensures result == countSubarraysWithSumK(nums, k)
{
    result := 0;
    assume false;  // spec-only
}

ghost function countSubarraysWithSumK(nums: array<int>, k: int): int
    reads nums
{
    countSubarraysHelper(nums, k, 0, 0)
}

ghost function countSubarraysHelper(nums: array<int>, k: int, start: nat, count: int): int
    requires 0 <= start <= nums.Length
    reads nums
    decreases nums.Length - start + 1
{
    if start >= nums.Length then count
    else countSubarraysHelper(nums, k, start + 1, count + countFromStart(nums, k, start, 0, start))
}

ghost function countFromStart(nums: array<int>, k: int, start: nat, acc: int, end: nat): int
    requires 0 <= start <= end <= nums.Length
    reads nums
    decreases nums.Length - end + 1
{
    if end > nums.Length then 0
    else (if acc == k then 1 else 0) +
         (if end < nums.Length then countFromStart(nums, k, start, acc + nums[end], end + 1) else 0)
}
