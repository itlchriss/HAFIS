// #Medium #Array #Hash_Table #Sorting #Binary_Search #Two_Pointers #Udemy_Arrays
// #2022_07_28_Time_13_ms_(58.23%)_Space_48.7_MB_(27.94%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*All the values in the integer array parameter `nums` are less than or equal to 10000000 and are greater than or equal to -10000000.*);
// requires(*The integer parameter `k` is less than or equal to 10000000 and is greater than or equal to 0.*);
// ensures(*The integer result is less than or equal to the length of the integer array parameter `nums`.*);
// ensures(*The integer result represents the number of unique k-diff pairs in the array.*);
// ensures(*If the integer array parameter `nums` is equal to [3,1,4,1,5] and the integer parameter `k` is equal to 2, the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,4,5] and the integer parameter `k` is equal to 1, the integer result is equal to 4.*);
// ensures(*If the integer array parameter `nums` is equal to [1,3,1,5,4] and the integer parameter `k` is equal to 0, the integer result is equal to 1.*);
    method findPairs(nums: array<int>, k: int) returns (result: int)
    {
        var res: int := 0;
        var set: set<int> := {};
        var twice: set<int> := {};
        for n in nums
        {
            if n in set {
                if k == 0 && !n in twice {
                    res := res + 1;
                    twice := twice + [n];
                } else {
                    // continue
                }
            } else {
                if n - k in set {
                    res := res + 1;
                }
                if n + k in set {
                    res := res + 1;
                }
            }
            set := set + [n];
        }
        result := res;
        return;
    }
