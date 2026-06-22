// s0454 - 4Sum II
// Dafny formal specification (spec-only)
method fourSumCount(nums1: array<int>, nums2: array<int>, nums3: array<int>, nums4: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums1` is greater than or equal to 1 and is less than or equal to 200.*);
    // requires(*The length of the integer array parameter `nums2` is greater than or equal to 1 and is less than or equal to 200.*);
    // requires(*The length of the integer array parameter `nums3` is greater than or equal to 1 and is less than or equal to 200.*);
    // requires(*The length of the integer array parameter `nums4` is greater than or equal to 1 and is less than or equal to 200.*);
    // requires(*The length of the integer array parameter `nums1` is equal to the length of the integer array parameter `nums2`.*);
    // requires(*The length of the integer array parameter `nums1` is equal to the length of the integer array parameter `nums3`.*);
    // requires(*The length of the integer array parameter `nums1` is equal to the length of the integer array parameter `nums4`.*);
    // requires(*The integer array parameter `nums1` is not equal to the null literal.*);
    // requires(*The integer array parameter `nums2` is not equal to the null literal.*);
    // requires(*The integer array parameter `nums3` is not equal to the null literal.*);
    // requires(*The integer array parameter `nums4` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `nums1` are greater than or equal to -268435456 and are less than or equal to 268435456.*);
    // requires(*All values in the integer array parameter `nums2` are greater than or equal to -268435456 and are less than or equal to 268435456.*);
    // requires(*All values in the integer array parameter `nums3` are greater than or equal to -268435456 and are less than or equal to 268435456.*);
    // requires(*All values in the integer array parameter `nums4` are greater than or equal to -268435456 and are less than or equal to 268435456.*);
    // ensures(*The integer result is greater than or equal to 0.*);
    // ensures(*The integer result is equal to the count of all tuples of non-negative integers `(i, j, k, l)` such that `i` is less than the length of the integer array parameter `nums1` and `j` is less than the length of the integer array parameter `nums2` and `k` is less than the length of the integer array parameter `nums3` and `l` is less than the length of the integer array parameter `nums4` and the sum of the value at index `i` of the integer array parameter `nums1`, the value at index `j` of the integer array parameter `nums2`, the value at index `k` of the integer array parameter `nums3`, and the value at index `l` of the integer array parameter `nums4` is equal to 0.*);
    // ensures(*If the integer array parameter `nums1` is equal to [1,2] and the integer array parameter `nums2` is equal to [-2,-1] and the integer array parameter `nums3` is equal to [-1,2] and the integer array parameter `nums4` is equal to [0,2], the integer result is equal to 2.*);
    // ensures(*If the integer array parameter `nums1` is equal to [0] and the integer array parameter `nums2` is equal to [0] and the integer array parameter `nums3` is equal to [0] and the integer array parameter `nums4` is equal to [0], the integer result is equal to 1.*);
    result := 0;
    assume false;
}
