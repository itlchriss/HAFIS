// s0164 - Maximum Gap
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 1000000000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*If the length of the integer array parameter `nums` is less than 2, the integer result is equal to 0.*);
// ensures(*If the length of the integer array parameter `nums` is greater than or equal to 2, the integer result is equal to the maximum difference between two successive elements in the sorted form of the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [3,6,9,1], the integer result is equal to 3.*);
// ensures(*If the integer array parameter `nums` is equal to [10], the integer result is equal to 0.*);
method maximumGap(nums: array<int>) returns (result: int)
{
    result := 0;
    assume false;
}
