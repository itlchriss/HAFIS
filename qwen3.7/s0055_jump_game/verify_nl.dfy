// s0055 - Jump Game
// Dafny formal specification (spec-only: greedy reachability)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 100000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, there exists a sequence of jumps starting from index 0 such the boolean result each jump from index `the int array parameter `nums`` is at most the value at index `the int array parameter `nums`` of the integer array parameter `nums` and the sequence reaches the last index of the integer array parameter `nums`.*);
// ensures(*If the boolean result is equal to the false literal, there does not exist a sequence of jumps starting from index 0 such the boolean result each jump from index `the int array parameter `nums`` is at most the value at index `the int array parameter `nums`` of the integer array parameter `nums` and the sequence reaches the last index of the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [2,3,1,1,4], the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `nums` is equal to [3,2,1,0,4], the boolean result is equal to the false literal.*);
method canJump(nums: array<int>) returns (result: bool)
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
