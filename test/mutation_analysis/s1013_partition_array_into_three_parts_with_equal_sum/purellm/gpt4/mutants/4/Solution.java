package g1001_1100.s1013_partition_array_into_three_parts_with_equal_sum;

// #Easy #Array #Greedy #2022_05_02_Time_1_ms_(100.00%)_Space_60.6_MB_(25.47%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires arr != null;
//@ requires 3 <= arr.length && arr.length <= 5 * 10000;
//@ requires (\forall int i; 0 <= i < arr.length; -10000 <= arr[i] <= 10000);
/*@ ensures \result <==> (
\exists int i, j; 0 <= i < j < arr.length &&
(\sum int k; 0 <= k <= i; arr[k]) == (\sum int k; i+1 <= k < j; arr[k]) &&
(\sum int k; j <= k < arr.length; arr[k]) == (\sum int k; 0 <= k <= i; arr[k])
); @*/
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
