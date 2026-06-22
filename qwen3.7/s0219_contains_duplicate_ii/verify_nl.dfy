// s0219 - Contains Duplicate II
// Dafny formal specification (spec-only: nearby duplicate detection)

method containsNearbyDuplicate(nums: array<int>, k: int) returns (result: bool)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100000.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
    // requires(*The integer parameter `k` is greater than or equal to 0 and is less than or equal to 100000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,2,3,1] and the integer parameter `k` is equal to 3, the boolean result is equal to the true literal.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,0,1,1] and the integer parameter `k` is equal to 1, the boolean result is equal to the true literal.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,2,3,1,2,3] and the integer parameter `k` is equal to 2, the boolean result is equal to the false literal.*);
    // ensures(*The boolean result is equal to the true literal if and only if there exist two distinct non-negative integers `i` and `j` such that the value at index `i` of the integer array parameter `nums` is equal to the value at index `j` of the integer array parameter `nums` and the absolute value of the difference between `i` and `j` is less than or equal to the integer parameter `k`.*);
    result := false;
    assume false;  // spec-only
}
