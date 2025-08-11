package g0901_1000.s0941_valid_mountain_array;

// #Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)

public class Solution {
//@ requires(*All values in the integer array parameter `arr` are greater than or equal to 0 and are less than or equal to 10000.*);
//@ ensures(*If boolean result is equal to the true literal, the integer array parameter `arr` has a length greater than or equal to 3 and there exists an integer index i such that i is greater than 0 and is less than the length of the integer array parameter `arr` minus 1 and the subsequence of values from index 0 up to index i strictly increases and the subsequence of values from index i down to the last index strictly decreases.*);
//@ ensures(*If boolean result is equal to the false literal, the integer array parameter `arr` does not satisfy the mountain array condition.*);
//@ ensures(*If the integer array parameter `arr` is equal to [2,1], boolean result is equal to the false literal.*);
//@ ensures(*If the integer array parameter `arr` is equal to [3,5,5], boolean result is equal to the false literal.*);
//@ ensures(*If the integer array parameter `arr` is equal to [0,3,2,1], boolean result is equal to the true literal.*);
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