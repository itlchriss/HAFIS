// #Easy #Top_Interview_Questions #Array #Two_Pointers #Udemy_Two_Pointers
// #2023_08_09_Time_1_ms_(98.56%)_Space_43.9_MB_(51.95%)
// Dafny version of Solution

requires (nums.length <= 30000) && (nums.length >= 0)
requires (nums.length <= 30000) && (nums.length >= 0)
requires (nums.size() <= 30000) && (nums.size() >= 0)
requires (nums.length() <= 30000) && (nums.length() >= 0)
requires (nums.size() <= 30000) && (nums.size() >= 0)
requires (\forall int i; 0 <= i < nums.length; nums[i] <= 100) && (\forall int i; 0 <= i < nums.length; nums[i] >= -100)
requires \forall int i; 0 <= i < nums.length-1; nums[i] <= nums[i+1]
// requires(*The relative order of the elements should be kept the same after removing duplicates.*);
// ensures(*The integer result is equal to the number of unique elements in the integer array parameter `nums`.*);
// ensures(*The first `k` elements of the integer array parameter `nums` should hold the final result after removing duplicates.*);
    method removeDuplicates(nums: array<int>) returns (result: int)
    {
        var n: int := |nums|;
        var i: int := 0;
        var j: int := 1;
        if n <= 1 {
            result := n;
            return;
        }
        // invariant //@ maintaining 0 <= j <= nums.length;
        // invariant //@ maintaining 0 <= i < j;
        while j <= n - 1
            invariant true
        {
            if nums[i] != nums[j] {
                nums[i + 1] := nums[j];
                i := i + 1;
            }
            j := j + 1;
        }
        result := i + 1;
        return;
    }
