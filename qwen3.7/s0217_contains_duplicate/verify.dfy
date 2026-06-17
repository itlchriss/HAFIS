// s0217 - Contains Duplicate
// Complete Dafny formal specification with verified implementation

method containsDuplicate(nums: array<int>) returns (result: bool)
    requires 1 <= nums.Length <= 100000
    ensures result <==> (exists i: nat, j: nat :: i < j < nums.Length && nums[i] == nums[j])
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
