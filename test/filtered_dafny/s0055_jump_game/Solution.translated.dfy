// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming #Greedy
// #Algorithm_II_Day_12_Dynamic_Programming #Dynamic_Programming_I_Day_4 #Udemy_Arrays
// #Big_O_Time_O(n)_Space_O(1) #2023_08_11_Time_2_ms_(79.47%)_Space_44.8_MB_(22.14%)
// Dafny version of Solution

requires (nums.length <= 10000) && (nums.length >= 1)
requires (nums.length <= 10000) && (nums.length >= 1)
requires (nums.size() <= 10000) && (nums.size() >= 1)
requires (nums.length() <= 10000) && (nums.length() >= 1)
requires (nums.size() <= 10000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 100000) && (\forall int i; 0 <= i < nums.length; nums[i] >= 0)
// ensures(*If the boolean result is true, it means that it is possible to reach the last index of the integer array parameter `nums`.*);
// ensures(*If the boolean result is false, it means that it is not possible to reach the last index of the integer array parameter `nums`.*);
    method canJump(nums: array<int>) returns (result: bool)
    {
        var sz: int := |nums|;
        // we set 1 so it won't break on the first iteration
        var tmp: int := 1;
        // invariant 0 <= i <= sz;
        for i := 0 to sz
            invariant i >= 0
            invariant i <= sz
        {
            // we always deduct tmp for every iteration
            tmp := tmp - 1;
            if tmp < 0 {
                // if from previous iteration tmp is already 0, it will be <0 here
                // leading to false value
                result := false;
                return;
            }
            // we get the maximum value because this value is supposed
            // to be our iterator, if both values are 0, then the next
            // iteration we will return false
            // if either both or one of them are not 0 then we will keep doing this and check.
            // We can stop the whole iteration with this condition. without this condition the code
            // runs in 2ms 79.6%, adding this condition improves the performance into 1ms 100%
            // because if the test case jump value is quite large, instead of just iterate, we can
            // just check using this condition
            // example: [10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] -> we can just jump to the end without
            // iterating whole array
            tmp := max(tmp, nums[i]);
            if i + tmp >= sz - 1 {
                result := true;
                return;
            }
        }
        // we can just return true at the end, because if tmp is 0 on previous
        // iteration,
        // even though the next iteration index is the last one, it will return false under the
        // tmp<0 condition
        result := true;
        return;
    }
