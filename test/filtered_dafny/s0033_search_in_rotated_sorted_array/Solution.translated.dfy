// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Binary_Search
// #Algorithm_II_Day_1_Binary_Search #Binary_Search_I_Day_11 #Level_2_Day_8_Binary_Search
// #Udemy_Binary_Search #Big_O_Time_O(log_n)_Space_O(1)
// #2023_08_09_Time_0_ms_(100.00%)_Space_40.6_MB_(92.43%)
// Dafny version of Solution

requires (nums.length <= 5000) && (nums.length >= 1)
requires (nums.length <= 5000) && (nums.length >= 1)
requires (nums.size() <= 5000) && (nums.size() >= 1)
requires (nums.length() <= 5000) && (nums.length() >= 1)
requires (nums.size() <= 5000) && (nums.size() >= 1)
// requires(*All values in the integer array parameter `nums` are unique.*);
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 10000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -10000)
requires (target <= 10000) && (target >= -10000)
// ensures(*If the integer result is equal to 4 and the integer array parameter `nums` is equal to [4,5,6,7,0,1,2] and the integer parameter `target` is equal to 0, the integer result is equal to 4.*);
// ensures(*If the integer result is equal to -1 and the integer array parameter `nums` is equal to [4,5,6,7,0,1,2] and the integer parameter `target` is equal to 3, the integer result is equal to -1.*);
// ensures(*If the integer result is equal to -1 and the integer array parameter `nums` is equal to [1] and the integer parameter `target` is equal to 0, the integer result is equal to -1.*);
    method search(nums: array<int>, target: int) returns (result: int)
    {
        var mid: int;
        var lo: int := 0;
        var hi: int := |nums| - 1;
        // maintaining 0 <= hi < nums.length;
        // invariant //@ maintaining 0 <= lo <= hi < nums.length || lo == hi + 1;
        while lo <= hi
            invariant true
        {
            mid := ((hi - lo) >> 1) + lo;
            if target == nums[mid] {
                result := mid;
                return;
            }
            // if this is true, then the possible rotation can only be in the second half
            if nums[lo] <= nums[mid] {
                // the target is in the first half only if it's
                if nums[lo] <= target && target <= nums[mid] {
                    // included
                    hi := mid - 1;
                } else {
                    // between nums[lo] and nums[mid]
                    lo := mid + 1;
                }
                // otherwise, the possible rotation can only be in the first half
            } else if nums[mid] <= target && target <= nums[hi] {
                // the target is in the second half only if it's included
                lo := mid + 1;
            } else {
                // between nums[hi] and nums[mid]
                hi := mid - 1;
            }
        }
        result := -1;
        return;
    }
