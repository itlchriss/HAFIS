// #Easy #Array #Hash_Table #Stack #Monotonic_Stack #Programming_Skills_I_Day_5_Function
// #2022_07_21_Time_4_ms_(81.18%)_Space_43.7_MB_(77.46%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums1` is less than or equal to 1000 and is greater than or equal to 1.*);
// requires(*The length of the integer array parameter `nums2` is less than or equal to 1000 and is greater than or equal to the length of the integer array parameter `nums1`.*);
// requires(*All the values in the integer array parameter `nums1` are unique.*);
// requires(*All the values in the integer array parameter `nums2` are unique.*);
// requires(*All the values in the integer array parameter `nums1` also appear in the integer array parameter `nums2`.*);
// ensures(*The integer array result has a length equal to the length of the integer array parameter `nums1`.*);
// ensures(*If the integer array parameter `nums1` is equal to [4,1,2] and the integer array parameter `nums2` is equal to [1,3,4,2], the integer array result is equal to [-1,3,-1].*);
// ensures(*If the integer array parameter `nums1` is equal to [2,4] and the integer array parameter `nums2` is equal to [1,2,3,4], the integer array result is equal to [3,-1].*);
    method nextGreaterElement(nums1: array<int>, nums2: array<int>) returns (result: array<int>)
    {
        // Map<Integer, Integer> indexMap = new HashMap<>();
        // invariant //@ maintaining 0 <= i <= nums2.length;
        for i := 0 to |nums2|
            invariant i >= 0
            invariant i <= |nums2|
        {
            indexMap := indexMap[nums2[i] := i];
        }
        // invariant //@ maintaining 0 <= i <= nums1.length;
        for i := 0 to |nums1|
            invariant i >= 0
            invariant i <= |nums1|
        {
            var num: int := nums1[i];
            var index: int := indexMap[num];
            if index == |nums2| - 1 {
                nums1[i] := -1;
            } else {
                var found: bool := false;
                while index < |nums2|
                    invariant true
                {
                    if nums2[index] > num {
                        nums1[i] := nums2[index];
                        found := true;
                        break;
                    }
                    index := index + 1;
                }
                if !found {
                    nums1[i] := -1;
                }
            }
        }
        result := nums1;
        return;
    }
