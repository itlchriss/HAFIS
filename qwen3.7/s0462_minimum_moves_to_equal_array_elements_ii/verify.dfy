// s0462 - Minimum Moves to Equal Array Elements II
// Dafny formal specification (spec-only)
method minMoves2(nums: array<int>) returns (result: int)
    requires 1 <= nums.Length <= 100000
    requires forall i: nat :: i < nums.Length ==> -1000000000 <= nums[i] <= 1000000000
    ensures result >= 0
{
    result := 0;
    assume false;
}
