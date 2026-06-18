// s0560 - Subarray Sum Equals K
// Dafny formal specification (spec-only: counting subarrays with sum k)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 20000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -1000 and are less than or equal to 1000.*);
// requires(*The integer parameter `k` is greater than or equal to -10000000 and is less than or equal to 10000000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is equal to the total number of contiguous subarrays the int array parameter `nums` sum equals the integer parameter `k`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,1,1] and the integer parameter `k` is equal to 2, the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3] and the integer parameter `k` is equal to 3, the integer result is equal to 2.*);
method subarraySum(nums: array<int>, k: int) returns (result: int)
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
