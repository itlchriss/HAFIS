// s1013 - Partition Array Into Three Parts With Equal Sum
// Dafny formal specification (spec-only)
method canThreePartsEqualSum(arr: array<int>) returns (result: bool)
{
    // requires(*The length of the integer array parameter `arr` is greater than or equal to 3 and is less than or equal to 50000.*);
    // requires(*The integer array parameter `arr` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `arr` are greater than or equal to -10000 and are less than or equal to 10000.*);
    // ensures(*The boolean result is equal to the true literal if and only if the integer array parameter `arr` can be partitioned into three contiguous subarrays with equal sums.*);
    // ensures(*If the integer array parameter `arr` is equal to [0,2,1,-6,6,-7,9,1,2,0,1], the boolean result is equal to the true literal.*);
    // ensures(*If the integer array parameter `arr` is equal to [3,3,6,5,-2,2,5,1,-9,4], the boolean result is equal to the true literal.*);
    // ensures(*If the integer array parameter `arr` is equal to [1,-1,1,-1], the boolean result is equal to the false literal.*);
    result := false;
    assume false;
}
