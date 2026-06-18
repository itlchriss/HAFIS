// s0496 - Next Greater Element I
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `nums1` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The length of the integer array parameter `nums2` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The length of the integer array parameter `nums1` is less than or equal to the length of the integer array parameter `nums2`.*);
// requires(*All values in the integer array parameter `nums1` are greater than or equal to 0 and are less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums2` are greater than or equal to 0 and are less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums1` are unique.*);
// requires(*All values in the integer array parameter `nums2` are unique.*);
// requires(*All values in the integer array parameter `nums1` also appear in the integer array parameter `nums2`.*);
// requires(*The integer array parameter `nums1` is not equal to the null literal.*);
// requires(*The integer array parameter `nums2` is not equal to the null literal.*);
// ensures(*The integer array result is not equal to the null literal.*);
// ensures(*The length of the integer array result is equal to the length of the integer array parameter `nums1`.*);
// ensures(*For every non-negative integer `the int array parameter `nums1`` array result is less than the length of the integer array parameter `nums1`, if the integer array result at index `the int array parameter `nums1`` is not equal to -1, the integer array result at index `the int array parameter `nums1`` is the first value in the integer array parameter `nums2` array result is greater than the integer array parameter `nums1` at index `the int array parameter `nums1`` and appears to the right of array result value in the integer array parameter `nums2`.*);
// ensures(*For every non-negative integer `the int array parameter `nums1`` array result is less than the length of the integer array parameter `nums1`, if the integer array result at index `the int array parameter `nums1`` is equal to -1, there is no value to the right of the integer array parameter `nums1` at index `the int array parameter `nums1`` in the integer array parameter `nums2` array result is greater than the integer array parameter `nums1` at index `the int array parameter `nums1``.*);
// ensures(*If the integer array parameter `nums1` is equal to [4,1,2] and the integer array parameter `nums2` is equal to [1,3,4,2], the integer array result is equal to [-1,3,-1].*);
// ensures(*If the integer array parameter `nums1` is equal to [2,4] and the integer array parameter `nums2` is equal to [1,2,3,4], the integer array result is equal to [3,-1].*);
method nextGreaterElement(nums1: array<int>, nums2: array<int>) returns (result: array<int>)
{
    result := new int[nums1.Length];
    assume false;
}
