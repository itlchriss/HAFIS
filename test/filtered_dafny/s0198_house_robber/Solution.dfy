// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Algorithm_I_Day_12_Dynamic_Programming #Dynamic_Programming_I_Day_3
// #Level_2_Day_12_Dynamic_Programming #Udemy_Dynamic_Programming #Big_O_Time_O(n)_Space_O(n)
// #2022_06_28_Time_0_ms_(100.00%)_Space_39.9_MB_(85.30%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 100 and is greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `nums` are less than or equal to 400 and are greater than or equal to 0.*);
// ensures(*The integer result is less than or equal to the sum of the values in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,1], the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [2,7,9,3,1], the integer result is equal to 12.*);
    method rob(nums: array<int>) returns (result: int)
    {
        if |nums| == 0 {
            result := 0;
            return;
        }
        if |nums| == 1 {
            result := nums[0];
            return;
        }
        if |nums| == 2 {
            result := max(nums[0], nums[1]);
            return;
        }
        var profit := new int[|nums|];
        profit[0] := nums[0];
        profit[1] := max(nums[1], nums[0]);
        // invariant //@ maintaining 2 <= i <= nums.length;
        for i := 2 to |nums|
            invariant i >= 2
            invariant i <= |nums|
        {
            profit[i] := max(profit[i - 1], nums[i] + profit[i - 2]);
        }
        result := profit[|nums| - 1];
        return;
    }
