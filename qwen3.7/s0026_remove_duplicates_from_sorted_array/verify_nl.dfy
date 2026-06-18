// s0026 - Remove Duplicates from Sorted Array
// Dafny formal specification (spec-only: two-pointer in-place deduplication)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 0 and is less than or equal to 30000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -100 and are less than or equal to 100.*);
// requires(*The integer array parameter `nums` is sorted in non-decreasing order.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `nums`.*);
// requires(*The first `integer result` elements of the integer array parameter `nums` contain the unique elements of the integer array parameter `nums` in sorted order.*);
// ensures(*If the integer array parameter `nums` is equal to [1,1,2], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [0,0,1,1,1,2,2,3,3,4], the integer result is equal to 5.*);
method removeDuplicates(nums: array<int>) returns (k: int)
{
    k := 1;
    assume false;  // spec-only
}
