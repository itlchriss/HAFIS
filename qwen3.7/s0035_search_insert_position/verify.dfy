// s0035 - Search Insert Position
// Complete Dafny formal specification with verified implementation

method searchInsert(nums: array<int>, target: int) returns (result: int)
    requires 1 <= nums.Length <= 10000
    // Sorted in ascending order
    requires forall i: nat, j: nat :: 0 <= i < j < nums.Length ==> nums[i] < nums[j]
    ensures 0 <= result <= nums.Length
    // All elements before result are less than target
    ensures forall k: nat :: k < result ==> nums[k] < target
    // All elements from result onward are >= target
    ensures forall k: nat :: result <= k < nums.Length ==> nums[k] >= target
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
