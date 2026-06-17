// #Easy #Array #Greedy #2022_05_02_Time_1_ms_(100.00%)_Space_60.6_MB_(25.47%)
// Dafny version of Solution

requires (arr.length <= 50000) && (arr.length >= 3)
requires (arr.length <= 50000) && (arr.length >= 3)
requires (arr.size() <= 50000) && (arr.size() >= 3)
requires (arr.length() <= 50000) && (arr.length() >= 3)
requires (arr.size() <= 50000) && (arr.size() >= 3)
requires (\forall int i; 0 <= i < arr.length; arr[i] <= 10000) && (\forall int i; 0 <= i < arr.length; arr[i] >= -10000)
// ensures(*The boolean result is true if we can find indexes `i + 1 < j` such that the sum of the elements from index 0 to i is equal to the sum of the elements from index i+1 to j-1 and is equal to the sum of the elements from index j to the end of the array.*);
// ensures(*If the integer array parameter `arr` is equal to [0,2,1,-6,6,-7,9,1,2,0,1], the boolean result is true.*);
// ensures(*If the integer array parameter `arr` is equal to [0,2,1,-6,6,7,9,-1,2,0,1], the boolean result is false.*);
// ensures(*If the integer array parameter `arr` is equal to [3,3,6,5,-2,2,5,1,-9,4], the boolean result is true.*);
    method canThreePartsEqualSum(arr: array<int>) returns (result: bool)
    {
        var sum: int := 0;
        for j in arr
        {
            sum := sum + j;
        }
        // 1. Base condition , the sum should be equally divided into 3 parts
        if sum % 3 != 0 {
            result := false;
            return;
        }
        var eq: int := sum / 3;
        // to keep track of occurences of sum in the sub array
        var count: int := 0;
        var temp: int := 0;
        for j in arr
        {
            // 2. Base / Break condition for loop , i.e. if the count is 2,
            // i.e. sum has been achieved twice ( and there is more indices
            // to go through since we are in the loop again ) then return true
            if count == 2 {
                result := true;
                return;
            }
            // 3. Adding to temp array
            temp := temp + j;
            // 4. If sum is achieved , increase the count
            if temp == eq {
                count := count + 1;
                // put temp=0 to start summing up from the next indices
                temp := 0;
            }
        }
        // 5. If the above conditoin fails , result is false
        result := false;
        return;
    }
