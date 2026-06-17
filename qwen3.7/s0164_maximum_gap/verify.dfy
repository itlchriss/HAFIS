// s0164 - Maximum Gap
// Dafny formal specification (spec-only)
method maximumGap(nums: array<int>) returns (result: int)
    requires 2 <= nums.Length <= 100000
    requires forall i: nat :: i < nums.Length ==> 0 <= nums[i] <= 1000000000
    ensures result >= 0
    ensures nums.Length < 2 ==> result == 0
{
    result := 0;
    assume false;
}
