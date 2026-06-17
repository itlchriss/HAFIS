// #Medium #Top_100_Liked_Questions #Array #Hash_Table #Prefix_Sum #Data_Structure_II_Day_5_Array
// #Big_O_Time_O(n)_Space_O(n) #2022_08_03_Time_21_ms_(98.97%)_Space_46.8_MB_(88.27%)
// Dafny version of Solution

requires (nums.length <= 20000) && (nums.length >= 1)
requires (nums.length <= 20000) && (nums.length >= 1)
requires (nums.size() <= 20000) && (nums.size() >= 1)
requires (nums.length() <= 20000) && (nums.length() >= 1)
requires (nums.size() <= 20000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 1000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000)
requires (k <= 10000000) && (k >= -10000000)
// ensures(*The integer result is equal to the total number of continuous subarrays whose sum equals the integer parameter `k`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,1,1] and the integer parameter `k` is equal to 2, the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3] and the integer parameter `k` is equal to 3, the integer result is equal to 2.*);
    method subarraySum(nums: array<int>, k: int) returns (result: int)
    {
        var tempSum: int := 0;
        var ret: int := 0;
        // Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount := sumCount[0 := 1];
        for i in nums
        {
            tempSum := tempSum + i;
            if tempSum - k in sumCount {
                ret := ret + sumCount[tempSum - k];
            }
            if sumCount[tempSum] != null {
                sumCount := sumCount[tempSum := sumCount[tempSum] + 1];
            } else {
                sumCount := sumCount[tempSum := 1];
            }
        }
        result := ret;
        return;
    }
