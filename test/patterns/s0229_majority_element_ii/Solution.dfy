// #Medium #Array #Hash_Table #Sorting #Counting
// #2022_07_04_Time_2_ms_(92.96%)_Space_50.2_MB_(35.08%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 50000 and is greater than or equal to 1.*);
// requires(*All values in the integer array parameter `nums` are less than or equal to 1000000000 and are greater than or equal to -1000000000.*);
// ensures(*If the list result is not empty, all values in the list result are elements that appear more than `⌊ n/3 ⌋` times in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [3,2,3], the list result is equal to [3].*);
// ensures(*If the integer array parameter `nums` is equal to [1], the list result is equal to [1].*);
// ensures(*If the integer array parameter `nums` is equal to [1,2], the list result is equal to [1,2].*);
// ensures(*The list result does not contain duplicate elements.*);
    method majorityElement(nums: array<int>) returns (result: seq<int>)
    {
        var results: seq<int> := [];
        var len: int := |nums|;
        var first: int := 0;
        var second: int := 1;
        var count1: int := 0;
        var count2: int := 0;
        // now we have two candidates(any integer can be chosed as),and their votes are
        // zero.
        for temp in nums
        {
            if temp == first {
                count1 := count1 + 1;
            } else if temp == second {
                count2 := count2 + 1;
            } else if count1 == 0 {
                first := temp;
                count1 := count1 + 1;
            } else if count2 == 0 {
                second := temp;
                count2 := count2 + 1;
            } else {
                // otherwise,if one of the vote is zero,that's meaning that
                // we only have or even don't have a candidate.So we set the number to the
                // candidate.
                count1 := count1 - 1;
                count2 := count2 - 1;
            }
            // where we have two candidates whose votes bigger than zero,
            // but the current number is not one of them.Votes decrease by 1 and
            // the current number complete its "mission" and is skipped at the same time.
            // once the cycle finished,the target is left after all the counteraction,as its
            // count is bigger than n/3.
        }
        count1 := 0;
        count2 := 0;
        for temp in nums
        {
            // check both of them is bigger than n/3.Becasue we may have only one satisfying
            // the demand.
            if temp == first {
                count1 := count1 + 1;
            }
            if temp == second {
                count2 := count2 + 1;
            }
        }
        if count1 > len / 3 {
            results := results + [first];
        }
        if count2 > len / 3 {
            results := results + [second];
        }
        result := results;
        return;
    }
