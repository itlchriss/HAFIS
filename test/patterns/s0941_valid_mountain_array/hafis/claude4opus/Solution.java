package g0901_1000.s0941_valid_mountain_array;

// #Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)

public class Solution {
//@ requires(*The length of the integer array parameter `arr` is greater than or equal to 1 and is less than or equal to 10000.*);
//@ requires(*All values in the integer array parameter `arr` are greater than or equal to 0 and are less than or equal to 10000.*);
//@ ensures(*If the boolean result is equal to the true literal, the length of the integer array parameter `arr` is greater than or equal to 3.*);
//@ ensures(*If the boolean result is equal to the false literal and the length of the integer array parameter `arr` is less than 3, the integer array parameter `arr` cannot form a valid mountain.*);
//@ ensures(*If the integer array parameter `arr` is equal to [2,1], the boolean result is equal to the false literal.*);
//@ ensures(*If the integer array parameter `arr` is equal to [3,5,5], the boolean result is equal to the false literal.*);
//@ ensures(*If the integer array parameter `arr` is equal to [0,3,2,1], the boolean result is equal to the true literal.*);
//@ ensures(*If the boolean result is equal to the true literal, there exists at least one value in the integer array parameter `arr` that is greater than both its adjacent values.*);
//@ ensures(*If the boolean result is equal to the true literal, the integer array parameter `arr` has a strictly increasing sequence followed by a strictly decreasing sequence.*);
    public boolean validMountainArray(int[] arr) {
        int i = 0;
        for (; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                return false;
            } else if (arr[i] > arr[i + 1]) {
                break;
            }
        }
        if (i == 0 || i >= arr.length - 1) {
            return false;
        }
        for (; i < arr.length - 1; i++) {
            if (arr[i] <= arr[i + 1]) {
                return false;
            }
        }
        return i == arr.length - 1;
    }
}