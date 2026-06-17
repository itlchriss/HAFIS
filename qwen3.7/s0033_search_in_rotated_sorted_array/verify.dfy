// s0033 - Search in Rotated Sorted Array
// Dafny formal specification (spec-only: binary search on rotated array)

method search(nums: array<int>, target: int) returns (result: int)
    requires 1 <= nums.Length <= 5000
    requires forall i: nat, j: nat :: 0 <= i < j < nums.Length ==> nums[i] != nums[j]
    requires forall i: nat :: i < nums.Length ==> -10000 <= nums[i] <= 10000
    requires -10000 <= target <= 10000
    ensures -1 <= result < nums.Length
    ensures result >= 0 ==> nums[result] == target
    ensures result == -1 ==> (forall i: nat :: i < nums.Length ==> nums[i] != target)
{
    result := -1;
    assume false;
}
