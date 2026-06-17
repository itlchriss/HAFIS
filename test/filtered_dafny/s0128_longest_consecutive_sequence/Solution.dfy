// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Hash_Table #Union_Find
// #Big_O_Time_O(N_log_N)_Space_O(1) #2022_06_23_Time_18_ms_(91.05%)_Space_64.8_MB_(63.58%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 100000.*);
// requires(*All the values in the integer array parameter `nums` are less than or equal to 1000000000 and are greater than or equal to -1000000000.*);
// ensures(*The integer result is equal to the length of the longest consecutive elements sequence in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [100,4,200,1,3,2], the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [0,3,7,2,5,8,4,6,0,1], the integer result is equal to 9.*);
    method longestConsecutive(nums: array<int>) returns (result: int)
    {
        if |nums| == 0 {
            result := 0;
            return;
        }
        // Arrays.sort()
        var max: int := -2147483648;
        var thsMax: int := 1;
        // invariant //@ maintaining 0 <= i <= nums.length || i == nums.length -1;
        for i := 0 to |nums| - 1
            invariant i >= 0
            invariant i <= |nums| - 1
        {
            if nums[i + 1] == nums[i] + 1 {
                thsMax := thsMax + 1;
                // continue
            }
            if nums[i + 1] == nums[i] {
                // continue
            }
            // Start of a new Sequene
            max := max(max, thsMax);
            thsMax := 1;
        }
        result := max(max, thsMax);
        return;
    }
