// s0496 - Next Greater Element I
// Dafny formal specification (spec-only)
method nextGreaterElement(nums1: array<int>, nums2: array<int>) returns (result: array<int>)
    requires 1 <= nums1.Length <= nums2.Length <= 1000
    requires forall i: nat :: i < nums1.Length ==> 0 <= nums1[i] <= 10000
    requires forall i: nat :: i < nums2.Length ==> 0 <= nums2[i] <= 10000
    ensures result.Length == nums1.Length
    ensures forall i: nat :: i < result.Length ==> result[i] == -1 || result[i] > nums1[i]
{
    result := new int[nums1.Length];
    assume false;
}
