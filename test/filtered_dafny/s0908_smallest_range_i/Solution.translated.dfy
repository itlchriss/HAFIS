// #Easy #Array #Math #2022_03_28_Time_2_ms_(88.84%)_Space_41.9_MB_(99.76%)
// Dafny version of Solution

// requires(*The integer array parameter `nums` has a length less than or equal to 10000 and greater than or equal to 1.*);
// ensures(*The integer result is less than or equal to the difference between the maximum value in the integer array parameter `nums` and the minimum value in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1] and the integer parameter `k` is equal to 0, the integer result is equal to 0.*);
// ensures(*If the integer array parameter `nums` is equal to [0,10] and the integer parameter `k` is equal to 2, the integer result is equal to 6.*);
// ensures(*If the integer array parameter `nums` is equal to [1,3,6] and the integer parameter `k` is equal to 3, the integer result is equal to 0.*);
    method smallestRangeI(nums: array<int>, k: int) returns (result: int)
    requires forall i:int :: 0 <= i < nums.Length ==> ((forall i :: 0 <= i < nums.Length ==> nums[i] <= 10000) && (forall i :: 0 <= i < nums.Length ==> nums[i] >= 0))
    requires forall i:int :: 0 <= i < nums.Length ==> ((forall i :: 0 <= i < |nums| ==> nums[i] <= 10000) && (forall i :: 0 <= i < |nums| ==> nums[i] >= 0))
    requires forall i:int :: 0 <= i < nums.Length ==> ((forall i :: 0 <= i < |nums| ==> nums[i] <= 10000) && (forall i :: 0 <= i < |nums| ==> nums[i] >= 0))
    requires (k <= 10000) && (k >= 0)
    {
        var min: int := 2147483647;
        var max: int := -2147483648;
        for num in nums
        {
            min := min(min, num);
            max := max(max, num);
        }
        if min + k >= max - k {
            result := 0;
            return;
        }
        result := (max - k) - (min + k);
        return;
    }
