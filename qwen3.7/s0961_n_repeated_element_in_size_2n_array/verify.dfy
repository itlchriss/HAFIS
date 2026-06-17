// s0961 - N-Repeated Element in Size 2N Array
// Dafny formal specification (spec-only: repeated element finding)

method repeatedNTimes(nums: array<int>) returns (result: int)
    requires 4 <= nums.Length <= 10000
    requires nums.Length % 2 == 0
    requires forall i: nat :: i < nums.Length ==> 0 <= nums[i] <= 10000
    // The result is an element that appears at least twice
    ensures exists i: nat, j: nat :: i < j < nums.Length && nums[i] == result && nums[j] == result
    // Test cases
    ensures nums.Length == 4 && nums[0] == 1 && nums[1] == 2 && nums[2] == 3 && nums[3] == 3 ==> result == 3
{
    result := -1;
    assume false;  // spec-only
}
