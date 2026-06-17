// #Easy #Array #Math #Sorting #2022_03_21_Time_2_ms_(99.90%)_Space_55.5_MB_(5.19%)
// Dafny version of Solution

requires (nums.length <= 10000) && (nums.length >= 3)
requires (nums.length <= 10000) && (nums.length >= 3)
requires (nums.size() <= 10000) && (nums.size() >= 3)
requires (nums.length() <= 10000) && (nums.length() >= 3)
requires (nums.size() <= 10000) && (nums.size() >= 3)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 1000) && (\forall int i; 0 <= i < nums.length; nums[i] >= -1000)
// ensures(*The integer result is less than or equal to the product of the three largest values in the integer array parameter `nums` and is greater than or equal to the product of the two smallest values in the integer array parameter `nums` multiplied by the largest value in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3], the integer result is equal to 6.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,4], the integer result is equal to 24.*);
// ensures(*If the integer array parameter `nums` is equal to [-1,-2,-3], the integer result is equal to -6.*);
    method maximumProduct(nums: array<int>) returns (result: int)
    {
        var min1: int := 2147483647;
        var min2: int := 2147483647;
        var max1: int := -2147483648;
        var max2: int := -2147483648;
        var max3: int := -2147483648;
        for i in nums
        {
            if i > max1 {
                max3 := max2;
                max2 := max1;
                max1 := i;
            } else if i > max2 {
                max3 := max2;
                max2 := i;
            } else if i > max3 {
                max3 := i;
            }
            if i < min1 {
                min2 := min1;
                min1 := i;
            } else if i < min2 {
                min2 := i;
            }
        }
        result := max(min1 * min2 * max1, max1 * max2 * max3);
        return;
    }
