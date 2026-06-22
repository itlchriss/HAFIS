// s0532 - K-diff Pairs in an Array
// Dafny formal specification (spec-only)
method findPairs(nums: array<int>, k: int) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to -10000000 and are less than or equal to 10000000.*);
    // requires(*The integer parameter `k` is greater than or equal to 0 and is less than or equal to 10000000.*);
    // ensures(*The integer result is greater than or equal to 0.*);
    // ensures(*If the integer array parameter `nums` is equal to [3,1,4,1,5] and the integer parameter `k` is equal to 2, the integer result is equal to 2.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,2,3,4,5] and the integer parameter `k` is equal to 1, the integer result is equal to 4.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,3,1,5,4] and the integer parameter `k` is equal to 0, the integer result is equal to 1.*);
    result := 0;
    assume false;
}
