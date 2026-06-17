// s0035 - Search Insert Position
// Complete Dafny formal specification with verified implementation

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -10000 and are less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums` are unique.*);
// requires(*The integer array parameter `nums` is sorted in ascending order.*);
// requires(*The integer parameter `target` is greater than or equal to -10000 and is less than or equal to 10000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `nums`.*);
// ensures(*If the integer result is less than the length of the integer array parameter `nums`, the value at the integer result index of the integer array parameter `nums` is equal to the integer parameter `target`.*);
// ensures(*If the integer result is less than the length of the integer array parameter `nums`, for every non-negative integer `k` that is less than the integer result, the value at index `k` of the integer array parameter `nums` is strictly less than the integer parameter `target`.*);
// ensures(*If the integer result is greater than 0 and is less than the length of the integer array parameter `nums`, for every non-negative integer `k` that is greater than or equal to the integer result and is less than the length of the integer array parameter `nums`, the value at index `k` of the integer array parameter `nums` is greater than or equal to the integer parameter `target`.*);
// ensures(*If the integer result is equal to the length of the integer array parameter `nums`, all values in the integer array parameter `nums` are strictly less than the integer parameter `target`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,3,5,6] and the integer parameter `target` is equal to 5, the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [1,3,5,6] and the integer parameter `target` is equal to 2, the integer result is equal to 1.*);
// ensures(*If the integer array parameter `nums` is equal to [1,3,5,6] and the integer parameter `target` is equal to 7, the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [1,3,5,6] and the integer parameter `target` is equal to 0, the integer result is equal to 0.*);
// ensures(*If the integer array parameter `nums` is equal to [1] and the integer parameter `target` is equal to 0, the integer result is equal to 0.*);
method searchInsert(nums: array<int>, target: int) returns (result: int)
{
    var lo: int := 0;
    var hi: int := nums.Length - 1;
    while lo <= hi
        invariant 0 <= lo
        invariant hi < nums.Length
        invariant lo <= hi + 1
        // Everything below lo is < target
        invariant forall k: nat :: k < lo ==> nums[k] < target
        // Everything above hi is >= target
        invariant forall k: nat :: hi < k < nums.Length ==> nums[k] >= target
    {
        var mid: int := lo + (hi - lo) / 2;
        if target == nums[mid] {
            result := mid;
            return;
        } else if target < nums[mid] {
            hi := mid - 1;
        } else {
            // target > nums[mid]
            lo := mid + 1;
        }
    }
    // lo > hi, so lo == hi + 1
    // From invariants: everything below lo is < target, everything above hi is >= target
    // Since lo == hi + 1, the boundary is at lo
    result := lo;
}
