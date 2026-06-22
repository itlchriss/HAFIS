// s0229 - Majority Element II
// Dafny formal specification (spec-only)
method majorityElement(nums: array<int>) returns (result: array<int>)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 50000.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // ensures(*The list result is not equal to the null literal.*);
    // ensures(*All values in the list result are contained in the integer array parameter `nums`.*);
    // ensures(*If the integer array parameter `nums` is equal to [3,2,3], the list result is equal to [3].*);
    // ensures(*If the integer array parameter `nums` is equal to [1], the list result is equal to [1].*);
    // ensures(*If the integer array parameter `nums` is equal to [1,2], the list result is equal to [1,2].*);
    // ensures(*All values in the list result appear more than the quotient of the length of the integer array parameter `nums` divided by 3 times in the integer array parameter `nums`.*);
    result := new int[0];
    assume false;
}
