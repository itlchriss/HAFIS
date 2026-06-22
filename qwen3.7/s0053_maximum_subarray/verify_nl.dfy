// s0053 - Maximum Subarray (Kadane's Algorithm)
// Dafny formal specification (spec-only: maximum contiguous subarray sum)

method maxSubArray(nums: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100000.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to -10000 and are less than or equal to 10000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to -10000 and is less than or equal to the product of 100000 and 10000.*);
    // ensures(*The integer result is the maximum sum of a contiguous subarray of the integer array parameter `nums`.*);
    // ensures(*If the integer array parameter `nums` is equal to [-2,1,-3,4,-1,2,1,-5,4], the integer result is equal to 6.*);
    // ensures(*If the integer array parameter `nums` is equal to [1], the integer result is equal to 1.*);
    // ensures(*If the integer array parameter `nums` is equal to [5,4,-1,7,8], the integer result is equal to 23.*);
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
