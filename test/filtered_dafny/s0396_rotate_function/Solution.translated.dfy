// #Medium #Array #Dynamic_Programming #Math #2022_07_15_Time_4_ms_(81.33%)_Space_86_MB_(54.94%)
// Dafny version of Solution

requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.length <= 100000) && (nums.length >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (nums.length() <= 100000) && (nums.length() >= 1)
requires (nums.size() <= 100000) && (nums.size() >= 1)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 100) && (\forall int i; 0 <= i < nums.length; nums[i] >= -100)
ensures (\result <= 2147483647) && (\result >= -2147483648)
// ensures(*If the integer array parameter `nums` is equal to [4,3,2,6], the integer result is equal to 26.*);
// ensures(*If the integer array parameter `nums` is equal to [100], the integer result is equal to 0.*);
    method maxRotateFunction(nums: array<int>) returns (result: int)
    {
        var allSum: int := 0;
        var len: int := |nums|;
        var f: int := 0;
        // invariant //@ maintaining 0 <= i <= nums.length;
        for i := 0 to len
            invariant i >= 0
            invariant i <= len
        {
            f := f + i * nums[i];
            allSum := allSum + nums[i];
        }
        var max: int := f;
        // invariant //@ maintaining 0 <= i <= nums.length - 1 || i == -1;
        var i := len - 1;
        while i >= 1
            invariant true
        {
            f := f + allSum - len * nums[i];
            max := max(f, max);
            i := i - 1;
        }
        result := max;
        return;
    }
