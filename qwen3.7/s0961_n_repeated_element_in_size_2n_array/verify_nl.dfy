// s0961 - N-Repeated Element in Size 2N Array
// Dafny formal specification (spec-only: repeated element finding)

method repeatedNTimes(nums: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 4 and is less than or equal to 10000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // requires(*The length of the integer array parameter `nums` is even.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 10000.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to 10000.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,2,3,3], the integer result is equal to 3.*);
    // ensures(*If the integer array parameter `nums` is equal to [2,1,2,5,3,2], the integer result is equal to 2.*);
    // ensures(*If the integer array parameter `nums` is equal to [5,1,5,2,5,3,5,4], the integer result is equal to 5.*);
    result := -1;
    assume false;  // spec-only
}
