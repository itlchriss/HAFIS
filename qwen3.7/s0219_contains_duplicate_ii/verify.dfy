// s0219 - Contains Duplicate II
// Dafny formal specification (spec-only: nearby duplicate detection)

method containsNearbyDuplicate(nums: array<int>, k: int) returns (result: bool)
    requires 1 <= nums.Length <= 100000
    requires forall i: nat :: i < nums.Length ==> -1000000000 <= nums[i] <= 1000000000
    requires 0 <= k <= 100000
    // Biconditional: result is true iff there exist nearby duplicates
    ensures result <==> (exists i: nat, j: nat ::
        i < j < nums.Length && nums[i] == nums[j] && j - i <= k)
{
    result := false;
    assume false;  // spec-only
}
