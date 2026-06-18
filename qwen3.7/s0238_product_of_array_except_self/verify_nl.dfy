// s0238 - Product of Array Except Self
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `nums` is greater than or equal to 2 and is less than or equal to 100000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -30 and are less than or equal to 30.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer array result is not equal to the null literal.*);
// ensures(*The length of the integer array result is equal to the length of the integer array parameter `nums`.*);
// ensures(*For each non-negative integer `the int array parameter `nums`` from 0 to the length of the integer array parameter `nums` minus 1 the value at index `the int array parameter `nums`` of the integer array result is equal to the product of all values in the integer array parameter `nums` except the value at index `the int array parameter `nums`` of the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,4], the integer array result is equal to [24,12,8,6].*);
// ensures(*If the integer array parameter `nums` is equal to [-1,1,0,-3,3], the integer array result is equal to [0,0,9,0,0].*);
method productExceptSelf(nums: array<int>) returns (result: array<int>)
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
