// s0128 - Longest Consecutive Sequence
// Dafny formal specification (spec-only)

method longestConsecutive(nums: array<int>) returns (result: int)
    requires 0 <= nums.Length <= 100000
    requires forall i: nat :: i < nums.Length ==> -1000000000 <= nums[i] <= 1000000000
    ensures result >= 0
    ensures nums.Length == 0 ==> result == 0
{
    result := 0;
    assume false;
}
