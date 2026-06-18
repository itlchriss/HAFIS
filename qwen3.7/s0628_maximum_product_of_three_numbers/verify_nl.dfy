// s0628 - Maximum Product of Three Numbers
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `nums` is greater than or equal to 3 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -1000 and are less than or equal to 1000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is equal to the maximum product of any three values in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3], the integer result is equal to 6.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,4], the integer result is equal to 24.*);
// ensures(*If the integer array parameter `nums` is equal to [-1,-2,-3], the integer result is equal to -6.*);
method maximumProduct(nums: array<int>) returns (result: int)
{
    result := 0;
    assume false;
}
