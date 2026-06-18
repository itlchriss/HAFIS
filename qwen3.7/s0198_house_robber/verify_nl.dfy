// s0198 - House Robber
// Dafny formal specification (spec-only: maximum non-adjacent sum)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 400.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is the maximum sum of a subset of non-adjacent elements in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,1], the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [2,7,9,3,1], the integer result is equal to 12.*);
method rob(nums: array<int>) returns (result: int)
{
    result := 0;
    assume false;  // spec-only
}

function sumAll(nums: array<int>): int
    reads nums
{
    sumAllHelper(nums, 0, 0)
}

function sumAllHelper(nums: array<int>, i: nat, acc: int): int
    requires i <= nums.Length
    reads nums
    decreases nums.Length - i
{
    if i >= nums.Length then acc
    else sumAllHelper(nums, i + 1, acc + nums[i])
}

ghost function sumSet(nums: array<int>, s: set<nat>): int
    reads nums
    requires forall i: nat :: i in s ==> i < nums.Length
{
    if s == {} then 0
    else
        var i :| i in s;
        nums[i] + sumSet(nums, s - {i})
}
