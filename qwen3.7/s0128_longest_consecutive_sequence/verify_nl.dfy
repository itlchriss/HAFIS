// s0128 - Longest Consecutive Sequence
// Dafny formal specification (spec-only)

method longestConsecutive(nums: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 0 and is less than or equal to 100000.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `nums`.*);
    // ensures(*The integer result is the maximum length of a consecutive elements sequence in the integer array parameter `nums`.*);
    // ensures(*If the integer array parameter `nums` is equal to [100,4,200,1,3,2], the integer result is equal to 4.*);
    // ensures(*If the integer array parameter `nums` is equal to [0,3,7,2,5,8,4,6,0,1], the integer result is equal to 9.*);
    result := 0;
    assume false;
}
