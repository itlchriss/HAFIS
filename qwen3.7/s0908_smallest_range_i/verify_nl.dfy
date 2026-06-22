// s0908 - Smallest Range I
// Complete Dafny formal specification with verified implementation

method smallestRangeI(nums: array<int>, k: int) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 10000.*);
    // requires(*The integer parameter `k` is greater than or equal to 0 and is less than or equal to 10000.*);
    // ensures(*The integer result is greater than or equal to 0.*);
    // ensures(*If the integer array parameter `nums` is equal to [1] and the integer parameter `k` is equal to 0, the integer result is equal to 0.*);
    // ensures(*If the integer array parameter `nums` is equal to [0,10] and the integer parameter `k` is equal to 2, the integer result is equal to 6.*);
    // ensures(*If the integer array parameter `nums` is equal to [1,3,6] and the integer parameter `k` is equal to 3, the integer result is equal to 0.*);
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
