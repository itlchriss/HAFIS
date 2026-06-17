// s0198 - House Robber
// Dafny formal specification (spec-only: maximum non-adjacent sum)

method rob(nums: array<int>) returns (result: int)
    requires 1 <= nums.Length <= 100
    requires forall i: nat :: i < nums.Length ==> 0 <= nums[i] <= 400
    ensures result >= 0
    ensures result <= sumAll(nums)
    // Result is the maximum sum of non-adjacent elements
    ensures exists chosen: set<nat> ::
        (forall i: nat :: i in chosen ==> i < nums.Length) &&
        (forall i: nat, j: nat :: i in chosen && j in chosen && i != j ==>
            i + 1 != j && j + 1 != i) &&
        result == sumSet(nums, chosen) &&
        (forall other: set<nat> ::
            (forall i: nat :: i in other ==> i < nums.Length) &&
            (forall i: nat, j: nat :: i in other && j in other && i != j ==>
                i + 1 != j && j + 1 != i) ==>
            sumSet(nums, other) <= result)
{
    result := 0;
    assume false;  // spec-only
}

function sumAll(nums: array<int>): int
    reads nums
{
    sumAllHelper(nums, 0, 0)
}

function sumAllHelper(nums: array<int>, i: nat, acc: int): int
    requires i <= nums.Length
    reads nums
    decreases nums.Length - i
{
    if i >= nums.Length then acc
    else sumAllHelper(nums, i + 1, acc + nums[i])
}

ghost function sumSet(nums: array<int>, s: set<nat>): int
    reads nums
    requires forall i: nat :: i in s ==> i < nums.Length
{
    if s == {} then 0
    else
        var i :| i in s;
        nums[i] + sumSet(nums, s - {i})
}
