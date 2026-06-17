// s0238 - Product of Array Except Self
// Dafny formal specification (spec-only)
method productExceptSelf(nums: array<int>) returns (result: array<int>)
    requires 2 <= nums.Length <= 100000
    requires forall i: nat :: i < nums.Length ==> -30 <= nums[i] <= 30
    ensures result != null
    ensures result.Length == nums.Length
    ensures forall i: nat :: i < result.Length ==>
        result[i] == productExcept(nums, i)
{
    result := new int[nums.Length];
    assume false;
}

ghost function productExcept(nums: array<int>, skip: nat): int
    requires skip < nums.Length
    reads nums
{
    productExceptHelper(nums, skip, 0, 1)
}

ghost function productExceptHelper(nums: array<int>, skip: nat, i: nat, acc: int): int
    requires i <= nums.Length
    reads nums
    decreases nums.Length - i
{
    if i >= nums.Length then acc
    else if i == skip then productExceptHelper(nums, skip, i + 1, acc)
    else productExceptHelper(nums, skip, i + 1, acc * nums[i])
}
