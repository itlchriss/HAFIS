// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 100000 and is greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `nums` are less than or equal to 1000000000 and are greater than or equal to -1000000000.*);
// ensures(*The integer result is less than or equal to the maximum value of java integer and is greater than or equal to the minimum value of java integer.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [1,10,2,9], the integer result is equal to 16.*);
    method minMoves2(nums: array<int>) returns (result: int)
    {
        // Arrays.sort()
        var median: int := (|nums| - 1) / 2;
        var ops: int := 0;
        for num in nums
        {
            if num != nums[median] {
                ops := ops + if nums[median] - num >= 0 then nums[median] - num else -nums[median] - num;
            }
        }
        result := ops;
        return;
    }
