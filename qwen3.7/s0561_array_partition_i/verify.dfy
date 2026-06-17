// s0561 - Array Partition I
// Dafny formal specification: assumes sorted input, sum elements at even indices

method arrayPairSum(nums: array<int>) returns (result: int)
    requires 2 <= nums.Length <= 10000
    requires nums.Length % 2 == 0
    requires forall i: nat :: i < nums.Length ==> -10000 <= nums[i] <= 10000
    // Sorted in non-decreasing order
    requires forall i: nat, j: nat :: 0 <= i < j < nums.Length ==> nums[i] <= nums[j]
    // Result is sum of min of each pair (which equals sum of elements at even indices)
    ensures result == sumEvenIndices(nums)
{
    var s := 0;
    var i := 0;
    while i < nums.Length
        invariant 0 <= i <= nums.Length
        invariant i % 2 == 0
        invariant s == sumEvenIndicesUpTo(nums, i)
    {
        s := s + nums[i];
        i := i + 2;
    }
    result := s;
}

// Sum of elements at even indices from 0 to n (exclusive)
function sumEvenIndicesUpTo(nums: array<int>, n: int): int
    requires 0 <= n <= nums.Length
    requires n % 2 == 0
    reads nums
    decreases n
{
    if n == 0 then 0
    else sumEvenIndicesUpTo(nums, n - 2) + nums[n - 2]
}

// Total sum of elements at even indices
function sumEvenIndices(nums: array<int>): int
    reads nums
{
    sumEvenIndicesUpTo(nums, nums.Length - (nums.Length % 2))
}
