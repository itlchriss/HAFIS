// #Easy #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Divide_and_Conquer #Data_Structure_I_Day_1_Array #Dynamic_Programming_I_Day_5
// #Udemy_Famous_Algorithm #Big_O_Time_O(n)_Space_O(1)
// #2023_08_11_Time_1_ms_(100.00%)_Space_57.7_MB_(90.58%)
// Dafny version of Solution

requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (nums.length() <= 100000) && (nums.length() >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -10000)
ensures (\result <= 2147483647) && (\result >= -2147483648)
// ensures(*If the integer array parameter `nums` is equal to [-2,1,-3,4,-1,2,1,-5,4], the integer result is equal to 6.*);
// ensures(*If the integer array parameter `nums` is equal to [1], the integer result is equal to 1.*);
// ensures(*If the integer array parameter `nums` is equal to [5,4,-1,7,8], the integer result is equal to 23.*);
    method maxSubArray(nums: array<int>) returns (result: int)
    {
        var maxi: int := -2147483648;
        var sum: int := 0;
        for num in nums
        {
            // calculating sub-array sum
            sum := sum + num;
            maxi := max(sum, maxi);
            if sum < 0 {
                // there is no point to carry a -ve subarray sum. hence setting to 0
                sum := 0;
            }
        }
        result := maxi;
        return;
    }
