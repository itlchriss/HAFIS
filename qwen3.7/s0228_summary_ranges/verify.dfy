// s0228 - Summary Ranges
// Dafny formal specification (spec-only)
method summaryRanges(nums: array<int>) returns (result: seq<string>)
    requires 0 <= nums.Length <= 20
    requires forall i: nat, j: nat :: 0 <= i < j < nums.Length ==> nums[i] < nums[j]
{
    result := [];
    assume false;
}
