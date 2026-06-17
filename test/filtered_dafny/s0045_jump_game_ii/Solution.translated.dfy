// #Medium #Top_100_Liked_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_13_Dynamic_Programming #Dynamic_Programming_I_Day_4
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(49.02%)_Space_44.7_MB_(52.72%)
// Dafny version of Solution

requires (nums.length <= 10000) && (nums.length >= 1)
requires (nums.length <= 10000) && (nums.length >= 1)
requires (nums.size() <= 10000) && (nums.size() >= 1)
requires (nums.length() <= 10000) && (nums.length() >= 1)
requires (nums.size() <= 10000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] >= 0) && (\forall int i; 0 <= i < nums.length; nums[i] <= 1000)
ensures \result >= 1
// ensures(*If the integer array parameter `nums` is equal to [2,3,1,1,4], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [2,3,0,1,4], the integer result is equal to 2.*);
    method jump(nums: array<int>) returns (result: int)
    {
        var length: int := 0;
        var maxLength: int := 0;
        var minJump: int := 0;
        // invariant 0 <= i <= nums.length - 1;
        // invariant //@ maintaining length <= nums.length - i - 1;
        for i := 0 to |nums| - 1
            invariant i >= 0
            invariant i <= |nums| - 1
        {
            length := length - 1;
            maxLength := maxLength - 1;
            maxLength := max(maxLength, nums[i]);
            if length <= 0 {
                length := maxLength;
                minJump := minJump + 1;
            }
            if length >= |nums| - i - 1 {
                result := minJump;
                return;
            }
        }
        result := minJump;
        return;
    }
