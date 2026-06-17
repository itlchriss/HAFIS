// s0026 - Remove Duplicates from Sorted Array
// Dafny formal specification (spec-only: two-pointer in-place deduplication)

method removeDuplicates(nums: array<int>) returns (k: int)
    requires 1 <= nums.Length <= 30000
    requires forall i: nat :: i < nums.Length ==> -100 <= nums[i] <= 100
    // Sorted in non-decreasing order
    requires forall i: nat, j: nat :: 0 <= i <= j < nums.Length ==> nums[i] <= nums[j]
    modifies nums
    ensures 1 <= k <= nums.Length
    // The first k elements are strictly increasing (unique and sorted)
    ensures forall i: nat, j: nat :: 0 <= i < j < k ==> nums[i] < nums[j]
{
    k := 1;
    assume false;  // spec-only
}
