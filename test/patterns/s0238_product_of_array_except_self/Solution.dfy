// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Prefix_Sum
// #Data_Structure_II_Day_5_Array #Udemy_Arrays #Big_O_Time_O(n^2)_Space_O(n)
// #2022_07_04_Time_1_ms_(100.00%)_Space_50.8_MB_(85.60%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 100000 and is greater than or equal to 2.*);
// requires(*All the values in the integer array parameter `nums` are less than or equal to 30 and are greater than or equal to -30.*);
// ensures(*The length of the integer array result is equal to the length of the integer array parameter `nums`.*);
// ensures(*The product of all elements in the integer array result, except the element at index `i`, is equal to the product of all elements in the integer array parameter `nums`, except the element at index `i`.*);
// ensures(*The product of any prefix or suffix of the integer array result is guaranteed to fit in a 32-bit integer.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,4], the integer array result is equal to [24,12,8,6].*);
// ensures(*If the integer array parameter `nums` is equal to [-1,1,0,-3,3], the integer array result is equal to [0,0,9,0,0].*);
    method productExceptSelf(nums: array<int>) returns (result: array<int>)
    {
        var product: int := 1;
        var ans := new int[|nums|];
        for num in nums
        {
            product := product * num;
        }
        // invariant //@ maintaining 0 <= i <= nums.length;
        for i := 0 to |nums|
            invariant i >= 0
            invariant i <= |nums|
        {
            if nums[i] != 0 {
                ans[i] := product / nums[i];
            } else {
                var p: int := 1;
                // invariant //@ maintaining 0 <= j <= nums.length;
                for j := 0 to |nums|
                    invariant j >= 0
                    invariant j <= |nums|
                {
                    if j != i {
                        p := p * nums[j];
                    }
                }
                ans[i] := p;
            }
        }
        result := ans;
        return;
    }
