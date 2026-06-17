// #Medium #Array #Math #Sorting #2022_07_19_Time_7_ms_(31.31%)_Space_46.7_MB_(6.63%)
// Dafny version of Solution

requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (nums.length() <= 100000) && (nums.length() >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000000000)
ensures (\result <= 2147483647) && (\result >= -2147483648)
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
