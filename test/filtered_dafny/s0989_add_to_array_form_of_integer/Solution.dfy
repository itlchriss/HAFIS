// #Easy #Array #Math #Programming_Skills_II_Day_5
// #2022_03_31_Time_7_ms_(65.92%)_Space_62.4_MB_(29.05%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `num` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*All values in the integer array parameter `num` are less than or equal to 9 and are greater than or equal to 0.*);
// requires(*The integer array parameter `num` does not contain any leading zeros except for the zero itself.*);
// requires(*The integer parameter `k` is less than or equal to 10000 and is greater than or equal to 1.*);
// ensures(*The length of the integer array result is equal to the maximum length between the length of the integer array parameter `num` and the number of digits in the integer parameter `k` plus 1.*);
// ensures(*The integer array result represents the array form of the sum between the integer array parameter `num` and the integer parameter `k`.*);
    method addToArrayForm(num: array<int>, k: int) returns (result: seq<int>)
    {
        var result: seq<int> := [];
        var carry: int := 0;
        var i := |num| - 1;
        while i >= 0
            invariant true
        {
            var temp: int := num[i] + k % 10 + carry;
            result := result + [temp % 10];
            carry := temp / 10;
            k := k / 10;
            i := i - 1;
        }
        while k > 0
            invariant true
        {
            var t: int := k % 10 + carry;
            result := result + [t % 10];
            carry := t / 10;
            k := k / 10;
        }
        if carry == 1 {
            result := result + [1];
        }
        // reverse Collections
        result := result;
        return;
    }
