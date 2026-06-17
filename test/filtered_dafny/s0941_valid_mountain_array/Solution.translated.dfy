// #Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)
// Dafny version of Solution

requires (arr.length <= 10000) && (arr.length >= 1)
requires (arr.length <= 10000) && (arr.length >= 1)
requires (arr.size() <= 10000) && (arr.size() >= 1)
requires (arr.length() <= 10000) && (arr.length() >= 1)
requires (arr.size() <= 10000) && (arr.size() >= 1)
requires (\forall int i; 0 <= i < arr.length; arr[i] <= 10000) && (\forall int i; 0 <= i < arr.length; arr[i] >= 0)
// ensures(*The boolean result is equal to true if and only if the integer array parameter `arr` is a valid mountain array.*);
// ensures(*The boolean result is equal to false if the integer array parameter `arr` is not a valid mountain array.*);
// ensures(*If the integer array parameter `arr` is equal to [2,1], the boolean result is equal to false.*);
// ensures(*If the integer array parameter `arr` is equal to [3,5,5], the boolean result is equal to false.*);
// ensures(*If the integer array parameter `arr` is equal to [0,3,2,1], the boolean result is equal to true.*);
    method validMountainArray(arr: array<int>) returns (result: bool)
    {
        var i: int := 0;
        // for (; i < arr.length - 1; i++) {
        if arr[i] == arr[i + 1] {
            result := false;
            return;
        } else if arr[i] > arr[i + 1] {
            break;
        }
        if i == 0 || i >= |arr| - 1 {
            result := false;
            return;
        }
        // for (; i < arr.length - 1; i++) {
        if arr[i] <= arr[i + 1] {
            result := false;
            return;
        }
        result := i == |arr| - 1;
        return;
    }
