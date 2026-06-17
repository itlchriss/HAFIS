// s0454 - 4Sum II
// Dafny formal specification (spec-only)
method fourSumCount(nums1: array<int>, nums2: array<int>, nums3: array<int>, nums4: array<int>) returns (result: int)
    requires 1 <= nums1.Length <= 200
    requires nums1.Length == nums2.Length == nums3.Length == nums4.Length
    requires forall i: nat :: i < nums1.Length ==> -1000000000 <= nums1[i] <= 1000000000
    requires forall i: nat :: i < nums2.Length ==> -1000000000 <= nums2[i] <= 1000000000
    requires forall i: nat :: i < nums3.Length ==> -1000000000 <= nums3[i] <= 1000000000
    requires forall i: nat :: i < nums4.Length ==> -1000000000 <= nums4[i] <= 1000000000
    ensures result >= 0
{
    result := 0;
    assume false;
}
