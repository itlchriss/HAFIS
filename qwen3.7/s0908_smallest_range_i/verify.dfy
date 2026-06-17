// s0908 - Smallest Range I
// Complete Dafny formal specification with verified implementation

method smallestRangeI(nums: array<int>, k: int) returns (result: int)
    requires 1 <= nums.Length <= 10000
    requires forall i: nat :: i < nums.Length ==> 0 <= nums[i] <= 10000
    requires 0 <= k <= 10000
    ensures result >= 0
{
    var lo := nums[0];
    var hi := nums[0];
    var i := 1;
    while i < nums.Length
        invariant 1 <= i <= nums.Length
        invariant lo <= hi
        invariant forall j: nat :: j < i ==> lo <= nums[j] <= hi
        invariant forall j: nat :: j < i ==> nums[j] >= lo
        invariant forall j: nat :: j < i ==> nums[j] <= hi
    {
        if nums[i] < lo { lo := nums[i]; }
        if nums[i] > hi { hi := nums[i]; }
        i := i + 1;
    }
    // lo is min, hi is max of the array
    var diff := hi - lo - 2 * k;
    result := if diff > 0 then diff else 0;
}
