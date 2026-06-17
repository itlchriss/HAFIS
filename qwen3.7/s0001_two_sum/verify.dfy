// s0001 - Two Sum
// Dafny formal specification (spec-only: hash-based pair finding)

method twoSum(numbers: array<int>, target: int) returns (result: array<int>)
    requires 2 <= numbers.Length <= 10000
    requires forall i: nat :: i < numbers.Length ==> -1000000000 <= numbers[i] <= 1000000000
    requires -1000000000 <= target <= 1000000000
    ensures result.Length == 2
    ensures 0 <= result[0] < result[1] < numbers.Length
    ensures numbers[result[0]] + numbers[result[1]] == target
{
    result := new int[2];
    assume false;  // spec-only
}
