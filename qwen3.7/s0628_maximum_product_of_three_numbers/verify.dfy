// s0628 - Maximum Product of Three Numbers
// Dafny formal specification (spec-only)
method maximumProduct(nums: array<int>) returns (result: int)
    requires 3 <= nums.Length <= 10000
    requires forall i: nat :: i < nums.Length ==> -1000 <= nums[i] <= 1000
{
    result := 0;
    assume false;
}
