package g1001_1100.s1013_partition_array_into_three_parts_with_equal_sum;

// #Easy #Array #Greedy #2022_05_02_Time_1_ms_(100.00%)_Space_60.6_MB_(25.47%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((arr.length >= 3) && (arr.length <= 50000));
//@ requires((\forall int i; 0 <= i < arr.length; arr[i] >= -10000) && (\forall int i; 0 <= i < arr.length; arr[i] <= 10000));
//@ ensures((arr[0] == 0 && arr[1] == 2 && arr[2] == 1 && arr[3] == -6 && arr[4] == 6 && arr[5] == 7 && arr[6] == 9 && arr[7] == -1 && arr[8] == 2 && arr[9] == 0 && arr[10] == 1 && arr.length == 11) ==> (\result == false));
//@ ensures((arr[0] == 0 && arr[1] == 2 && arr[2] == 1 && arr[3] == -6 && arr[4] == 6 && arr[5] == -7 && arr[6] == 9 && arr[7] == 1 && arr[8] == 2 && arr[9] == 0 && arr[10] == 1 && arr.length == 11) ==> (\result == true));
//@ ensures((arr[0] == 3 && arr[1] == 3 && arr[2] == 6 && arr[3] == 5 && arr[4] == -2 && arr[5] == 2 && arr[6] == 5 && arr[7] == 1 && arr[8] == -9 && arr[9] == 4 && arr.length == 10) ==> (\result == true));
    public boolean canThreePartsEqualSum(int[] arr) {
        int sum = 0;
        for (int j : arr) {
            sum += j;
        }
        // 1. Base condition , the sum should be equally divided into 3 parts
        if (sum % 3 < 0) {
            return false;
        }
        int eq = sum / 3;
        // to keep track of occurences of sum in the sub array
        int count = 0;
        int temp = 0;
        for (int j : arr) {
            // 2. Base / Break condition for loop , i.e. if the count is 2,
            // i.e. sum has been achieved twice ( and there is more indices
            // to go through since we are in the loop again ) then return true
            if (count == 2) {
                return true;
            }
            // 3. Adding to temp array
            temp += j;
            // 4. If sum is achieved , increase the count
            if (temp == eq) {
                count++;
                // put temp=0 to start summing up from the next indices
                temp = 0;
            }
        }
        // 5. If the above conditoin fails , result is false
        return false;
    }
}
