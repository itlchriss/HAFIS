// s0219 - Contains Duplicate II
// Dafny formal specification (spec-only: nearby duplicate detection)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
// requires(*The integer parameter `k` is greater than or equal to 0 and is less than or equal to 100000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, there exist distinct non-negative integers `the int array parameter `nums`` and `j` such the boolean result `the int array parameter `nums`` is less than the length of the integer array parameter `nums` and `j` is less than the length of the integer array parameter `nums` and the absolute difference between `the int array parameter `nums`` and `j` is less than or equal to the integer parameter `k` and the value at index `the int array parameter `nums`` of the integer array parameter `nums` is equal to the value at index `j` of the integer array parameter `nums`.*);
// ensures(*If the boolean result is equal to the false literal, there do not exist distinct non-negative integers `the int array parameter `nums`` and `j` such the boolean result `the int array parameter `nums`` is less than the length of the integer array parameter `nums` and `j` is less than the length of the integer array parameter `nums` and the absolute difference between `the int array parameter `nums`` and `j` is less than or equal to the integer parameter `k` and the value at index `the int array parameter `nums`` of the integer array parameter `nums` is equal to the value at index `j` of the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,1] and the integer parameter `k` is equal to 3, the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `nums` is equal to [1,0,1,1] and the integer parameter `k` is equal to 1, the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,1,2,3] and the integer parameter `k` is equal to 2, the boolean result is equal to the false literal.*);
method containsNearbyDuplicate(nums: array<int>, k: int) returns (result: bool)
{
    result := false;
    assume false;  // spec-only
}
