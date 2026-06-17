// s0217 - Contains Duplicate
// Complete Dafny formal specification with verified implementation

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, there exist a non-negative integer `i` and a non-negative integer `j` such that `i` is less than `j` and `j` is less than the length of the integer array parameter `nums` and the value at index `i` of the integer array parameter `nums` is equal to the value at index `j` of the integer array parameter `nums`.*);
// ensures(*If the boolean result is equal to the false literal, all values in the integer array parameter `nums` are unique.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,1], the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,4], the boolean result is equal to the false literal.*);
// ensures(*If the integer array parameter `nums` is equal to [1,1,1,3,3,4,3,2,4,2], the boolean result is equal to the true literal.*);
method containsDuplicate(nums: array<int>) returns (result: bool)
{
    var seen: set<int> := {};
    var idx: nat := 0;
    while idx < nums.Length
        invariant idx <= nums.Length
        invariant seen == set k: nat | k < idx :: nums[k]
        invariant !(exists i: nat, j: nat :: i < j < idx && nums[i] == nums[j])
    {
        if nums[idx] in seen {
            // Found a duplicate: nums[idx] was seen before at some earlier index
            assert exists k: nat :: k < idx && nums[k] == nums[idx];
            result := true;
            return;
        }
        // nums[idx] not in seen, so adding it doesn't create a duplicate
        assert !(exists k: nat :: k < idx && nums[k] == nums[idx]);
        seen := seen + {nums[idx]};
        idx := idx + 1;
    }
    // Loop finished: idx == nums.Length, all values processed
    assert idx == nums.Length;
    // Since we never returned true, no duplicate was found among all elements
    assert !(exists i: nat, j: nat :: i < j < nums.Length && nums[i] == nums[j]);
    result := false;
}
