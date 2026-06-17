// #Hard #Array #Sorting #Bucket_Sort #Radix_Sort
// #2022_06_25_Time_48_ms_(53.59%)_Space_84.1_MB_(20.66%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 100000 and is greater than or equal to 1.*);
// requires(*All values in the integer array parameter `nums` are less than or equal to 1000000000 and are greater than or equal to 0.*);
// ensures(*The integer result is less than or equal to the maximum difference between two successive elements in the sorted form of the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [3,6,9,1], the integer result is equal to 3.*);
// ensures(*If the integer array parameter `nums` is equal to [10], the integer result is equal to 0.*);
    method maximumGap(nums: array<int>) returns (result: int)
    {
        if |nums| < 2 {
            result := 0;
            return;
        }
        var ret: int := -2147483648;
        // Arrays.sort()
        // invariant //@ maintaining 0 <= i <= nums.length;
        for i := 0 to |nums| - 1
            invariant i >= 0
            invariant i <= |nums| - 1
        {
            if (nums[i + 1] - nums[i]) > ret {
                ret := (nums[i + 1] - nums[i]);
            }
        }
        result := ret;
        return;
    }
