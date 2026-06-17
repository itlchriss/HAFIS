// #Easy #Array #Hash_Table #2022_03_31_Time_1_ms_(87.33%)_Space_54.1_MB_(66.98%)
// Dafny version of Solution

requires (nums.length <= 10000) && (nums.length >= 4)
requires (nums.length <= 10000) && (nums.length >= 4)
requires (nums.size() <= 10000) && (nums.size() >= 4)
requires (nums.length() <= 10000) && (nums.length() >= 4)
requires (nums.size() <= 10000) && (nums.size() >= 4)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= 0)
// requires(*The integer array parameter `nums` contains n + 1 unique elements.*);
// requires(*Exactly one element in the integer array parameter `nums` is repeated n times.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,3], the integer result is equal to 3.*);
// ensures(*If the integer array parameter `nums` is equal to [2,1,2,5,3,2], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [5,1,5,2,5,3,5,4], the integer result is equal to 5.*);
    method repeatedNTimes(nums: array<int>) returns (result: int)
    {
        var set: set<int> := {};
        for num in nums
        {
            if !set.add(num) {
                result := num;
                return;
            }
        }
        result := -1;
        return;
    }
