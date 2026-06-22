ghost function max_subarray_sum(nums: array<int>): int
    requires nums != null
    requires nums.Length > 0
{
    var maxSoFar := nums[0];
    var maxEndingHere := nums[0];
    for i := 1 to nums.Length {
        maxEndingHere := max(maxEndingHere + nums[i], nums[i]);
        maxSoFar := max(maxSoFar, maxEndingHere);
    }
    maxSoFar
}

ghost function max(a: int, b: int): int
{
    if a >= b then a else b
}
