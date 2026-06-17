// #Medium #Top_Interview_Questions #Array #Hash_Table
// #2022_07_18_Time_133_ms_(95.19%)_Space_42.4_MB_(88.53%)
// Dafny version of Solution

// requires(*The length of each integer array parameter `nums1`, `nums2`, `nums3`, and `nums4` is equal to `n` and is greater than or equal to 1 and less than or equal to 200.*);
// requires(*All the values in each integer array parameter `nums1`, `nums2`, `nums3`, and `nums4` are greater than or equal to -2^28 and less than or equal to 2^28.*);
// ensures(*The integer result is less than or equal to the maximum value of java integer and is greater than or equal to the minimum value of java integer.*);
// ensures(*If the integer result is equal to 2, the tuples are (0, 0, 0, 1) and (1, 1, 0, 0) where the sum of elements at the corresponding indices in `nums1`, `nums2`, `nums3`, and `nums4` is equal to 0.*);
// ensures(*If the integer result is equal to 1, there is one tuple where the sum of elements at the corresponding indices in `nums1`, `nums2`, `nums3`, and `nums4` is equal to 0.*);
    method fourSumCount(nums1: array<int>, nums2: array<int>, nums3: array<int>, nums4: array<int>) returns (result: int)
    {
        var count: int := 0;
        // Map<Integer, Integer> map = new HashMap<>();
        for k in nums3
        {
            for i in nums4
            {
                var sum: int := k + i;
                map := map[sum := map.getOrDefault(sum, 0) + 1];
            }
        }
        for k in nums1
        {
            for i in nums2
            {
                var m: int := -(k + i);
                count := count + map.getOrDefault(m, 0);
            }
        }
        result := count;
        return;
    }
