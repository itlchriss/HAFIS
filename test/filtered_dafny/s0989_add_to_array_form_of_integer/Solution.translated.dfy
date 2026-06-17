// #Easy #Array #Math #Programming_Skills_II_Day_5
// #2022_03_31_Time_7_ms_(65.92%)_Space_62.4_MB_(29.05%)
// Dafny version of Solution

requires (num.length <= 10000) && (num.length >= 1)
requires (num.length <= 10000) && (num.length >= 1)
requires (num.size() <= 10000) && (num.size() >= 1)
requires (num.length() <= 10000) && (num.length() >= 1)
requires (num.size() <= 10000) && (num.size() >= 1)
requires (\forall int i; 0 <= i < num.length; num[i] <= 9) && (\forall int i; 0 <= i < num.length; num[i] >= 0)
requires (!(num.length == 1 && num[0] == 0)) ==> (num[0] != 0)
requires (k <= 10000) && (k >= 1)
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
