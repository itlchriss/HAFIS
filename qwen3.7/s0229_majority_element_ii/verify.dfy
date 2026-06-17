// s0229 - Majority Element II
// Dafny formal specification (spec-only)
method majorityElement(nums: array<int>) returns (result: array<int>)
    requires 1 <= nums.Length <= 500
    requires forall i: nat :: i < nums.Length ==> -1000000000 <= nums[i] <= 1000000000
    ensures result != null
    ensures result.Length <= 2
{
    result := new int[0];
    assume false;
}
