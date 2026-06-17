// #Easy #Array #2022_07_04_Time_0_ms_(100.00%)_Space_42.7_MB_(15.43%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `nums` is less than or equal to 20 and is greater than or equal to 0.*);
// requires(*All the values in the integer array parameter `nums` are unique.*);
// requires(*The integer array parameter `nums` is sorted in ascending order.*);
// requires(*Each range in the list should be in the format "a->b" if a is not equal to b, and in the format "a" if a is equal to b.*);
// requires(*The ranges cover all the numbers in the array exactly.*);
// requires(*Each element of the integer array parameter `nums` is covered by exactly one of the ranges.*);
// requires(*There is no integer x such that x is in one of the ranges but not in `nums`.*);
    method summaryRanges(nums: array<int>) returns (result: seq<string>)
    {
        var ranges: seq<string> := [];
        if |nums| == 0 {
            result := ranges;
            return;
        }
        // size of array
        var n: int := |nums|;
        // start of range
        var a: int := nums[0];
        // end of range
        var b: int := a;
        var strB: StringBuilder := new StringBuilder();
        // invariant //@ maintaining 1 <= i <= n;
        for i := 1 to n
            invariant i >= 1
            invariant i <= n
        {
            // we need to make a decision if the next element
            // will expand the range
            // i starts at 1, not 0, because 1 is the next
            // candidate for expanding the range
            if nums[i] != b + 1 {
                // only when our next element does not expand the range
                // do we add the range a->b to our list of ranges
                // strB.append(a)
                if a != b {
                    // strB.append("->").append(b)
                }
                ranges := ranges + [strB.toString()];
                // since nums[i] is not accounted for by our range a->b
                // because nums[i] is not b+1, we need to set a and b
                // to this new range start point of bigger than b+1
                // maybe it is b+2? b+3? b+4? all we know is it is not b+1
                a := nums[i];
                b := a;
                // Reset string builder
                // strB.setLength(0)
            } else {
                // if the next element expands our range we do so
                b := b + 1;
            }
        }
        // the only range that is not accounted for at this point is the last range
        // if our a and b are not equal then we add the range accordingly
        // strB.append(a)
        if a != b {
            // strB.append("->").append(b)
        }
        ranges := ranges + [strB.toString()];
        result := ranges;
        return;
    }
