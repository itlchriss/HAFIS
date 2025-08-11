package g0901_1000.s0941_valid_mountain_array;

// #Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)

public class Solution {
//@ requires(*All values in the integer array parameter `arr` are less than or equal to 10000 and are greater than or equal to 0.*);
//@ ensures(*If boolean result is equal to the true literal, the length of the integer array parameter `arr` is greater than or equal to 3.*);
//@ ensures(*If boolean result is equal to the true literal, the position of the largest value in the integer array parameter `arr` is greater than 0 and is less than the length of the integer array parameter `arr` minus 1.*);
//@ ensures(*If boolean result is equal to the true literal, the largest value in the integer array parameter `arr` occurs exactly 1 time.*);
//@ ensures(*If boolean result is equal to the true literal, for every index that is smaller than the position of the largest value, the value at that index in the integer array parameter `arr` is strictly less than the value at the next index, and for every index that is greater than or equal to the position of the largest value and is less than the length of the integer array parameter `arr` minus 1, the value at that index in the integer array parameter `arr` is strictly greater than the value at the next index.*);
//@ ensures(*If the integer array parameter `arr` is equal to [2,1], the boolean result is equal to the false literal.*);
//@ ensures(*If the integer array parameter `arr` is equal to [3,5,5], the boolean result is equal to the false literal.*);
//@ ensures(*If the integer array parameter `arr` is equal to [0,3,2,1], the boolean result is equal to the true literal.*);
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