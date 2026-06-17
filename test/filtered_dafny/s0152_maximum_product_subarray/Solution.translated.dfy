// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Dynamic_Programming_I_Day_6 #Level_2_Day_13_Dynamic_Programming #Udemy_Dynamic_Programming
// #Big_O_Time_O(N)_Space_O(1) #2022_06_25_Time_0_ms_(100.00%)_Space_42.7_MB_(82.46%)
// Dafny version of Solution

requires (arr.length <= 20000) && (arr.length >= 1)
requires (arr.length <= 20000) && (arr.length >= 1)
requires (arr.size() <= 20000) && (arr.size() >= 1)
requires (arr.length() <= 20000) && (arr.length() >= 1)
requires (arr.size() <= 20000) && (arr.size() >= 1)
requires (\forall int i; 0 <= i < arr.length; arr[i] <= 10) && (\forall int i; 0 <= i < arr.length; arr[i] >= -10)
ensures (\result <= 2147483647) && (\result >= -2147483648)
// ensures(*If the integer array parameter `arr` is equal to [2,3,-2,4], the integer result is equal to 6.*);
// ensures(*If the integer array parameter `arr` is equal to [-2,0,-1], the integer result is equal to 0.*);
    method maxProduct(arr: array<int>) returns (result: int)
    {
        var ans: int := -2147483648;
        var cprod: int := 1;
        for j in arr
        {
            cprod := cprod * j;
            ans := max(ans, cprod);
            if cprod == 0 {
                cprod := 1;
            }
        }
        cprod := 1;
        // invariant //@ maintaining 0 <= i <= arr.length - 1 || i == -1;
        var i := |arr| - 1;
        while i >= 0
            invariant true
        {
            cprod := cprod * arr[i];
            ans := max(ans, cprod);
            if cprod == 0 {
                cprod := 1;
            }
            i := i - 1;
        }
        result := ans;
        return;
    }
