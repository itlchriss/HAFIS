// #Easy #Top_Interview_Questions #Array #Hash_Table #Sorting #Data_Structure_I_Day_1_Array
// #Programming_Skills_I_Day_11_Containers_and_Libraries #Udemy_Arrays
// #2022_07_02_Time_6_ms_(96.68%)_Space_54.4_MB_(94.38%)
// Dafny version of Solution

requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (nums.length() <= 100000) && (nums.length() >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 1000000000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000000000)
ensures (\result == true) ==> (!(\forall int i; 0 <= i < nums.length; (\forall int j; 0 <= j < nums.length && j != i; nums[j] != nums[i])))
ensures (\result == false) ==> (\forall int i; 0 <= i < nums.length; (\forall int j; 0 <= j < nums.length && j != i; nums[j] != nums[i]))
    method containsDuplicate(nums: array<int>) returns (result: bool)
    {
        var set: set<int> := {};
        for n in nums
        {
            if n in set {
                result := true;
                return;
            }
            set := set + [n];
        }
        result := false;
        return;
    }
