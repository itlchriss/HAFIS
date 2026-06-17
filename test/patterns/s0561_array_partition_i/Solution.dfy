// #Easy #Array #Sorting #Greedy #Counting_Sort
// #2022_08_03_Time_14_ms_(84.99%)_Space_44.2_MB_(95.29%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*All values in the integer array parameter `nums` are less than or equal to 10000 and are greater than or equal to -10000.*);
// requires(*The integer array parameter `nums` has an even length.*);
// ensures(*The integer result is equal to the sum of the minimum value between each pair of integers in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,4,3,2], the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [6,2,6,5,1,2], the integer result is equal to 9.*);
    method arrayPairSum(nums: array<int>) returns (result: int)
    {
        // Arrays.sort()
        var sum: int := 0;
        for i := 0 to |nums| - 1
            invariant i >= 0
            invariant i <= |nums| - 1
        {
            sum := sum + min(nums[i], nums[i + 1]);
        }
        result := sum;
        return;
    }
