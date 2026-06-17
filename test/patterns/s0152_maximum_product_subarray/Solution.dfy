// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Dynamic_Programming_I_Day_6 #Level_2_Day_13_Dynamic_Programming #Udemy_Dynamic_Programming
// #Big_O_Time_O(N)_Space_O(1) #2022_06_25_Time_0_ms_(100.00%)_Space_42.7_MB_(82.46%)
// Dafny version of Solution

// requires(*The length of the integer array parameter `arr` is less than or equal to 20000 and is greater than or equal to 1.*);
// requires(*All values in the integer array parameter `arr` are less than or equal to 10 and are greater than or equal to -10.*);
// ensures(*The integer result is less than or equal to the maximum value of a 32-bit integer and is greater than or equal to the minimum value of a 32-bit integer.*);
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
