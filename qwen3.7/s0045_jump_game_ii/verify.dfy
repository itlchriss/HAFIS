// s0045 - Jump Game II
// Dafny formal specification (spec-only: minimum jumps to end)

method jump(nums: array<int>) returns (result: int)
    requires 1 <= nums.Length <= 10000
    requires forall i: nat :: i < nums.Length ==> 0 <= nums[i] <= 1000
    ensures nums.Length == 1 ==> result == 0
    ensures nums.Length > 1 ==> result >= 1
{
    result := 0;
    assume false;
}
