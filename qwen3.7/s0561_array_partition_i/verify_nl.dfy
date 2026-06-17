// s0561 - Array Partition I
// Dafny formal specification: assumes sorted input, sum elements at even indices

// requires(*The length of the integer array parameter `nums` is greater than or equal to 2 and is less than or equal to 20000.*);
// requires(*The length of the integer array parameter `nums` is even.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -10000 and are less than or equal to 10000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is equal to the maximum possible sum of the minimum values of each pair when the integer array parameter `nums` is grouped into pairs.*);
// ensures(*If the integer array parameter `nums` is equal to [1,4,3,2], the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [6,2,6,5,1,2], the integer result is equal to 9.*);
method arrayPairSum(nums: array<int>) returns (result: int)
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
