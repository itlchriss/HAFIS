// s0532 - K-diff Pairs in an Array
// Dafny formal specification (spec-only)
method findPairs(nums: array<int>, k: int) returns (result: int)
    requires 1 <= nums.Length <= 10000
    requires 0 <= k <= 1000000000
    requires forall i: nat :: i < nums.Length ==> -10000000 <= nums[i] <= 10000000
    ensures result >= 0
{
    result := 0;
    assume false;
}
