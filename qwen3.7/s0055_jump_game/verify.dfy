// s0055 - Jump Game
// Dafny formal specification (spec-only: greedy reachability)

method canJump(nums: array<int>) returns (result: bool)
    requires 1 <= nums.Length <= 10000
    requires forall i: nat :: i < nums.Length ==> 0 <= nums[i] <= 100000
    // If true, the last index is reachable from index 0
    ensures result <==> canReachEnd(nums)
{
    result := false;
    assume false;  // spec-only
}

ghost predicate canReachEnd(nums: array<int>)
    reads nums
{
    // The maximum reachable index from greedy scan covers the last index
    maxReach(nums) >= nums.Length - 1
}

ghost function maxReach(nums: array<int>): nat
    reads nums
{
    maxReachHelper(nums, 0, 0)
}

ghost function maxReachHelper(nums: array<int>, i: nat, reach: nat): nat
    requires i <= nums.Length
    reads nums
    decreases nums.Length - i
{
    if i >= nums.Length then reach
    else if i > reach then reach  // can't reach index i
    else maxReachHelper(nums, i + 1, if i + nums[i] > reach then i + nums[i] else reach)
}
