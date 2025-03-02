package g0901_1000.s0941_valid_mountain_array;

// #Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((arr.length <= 10000) && (arr.length >= 1));
//@ requires((\forall int i; 0 <= i < arr.length; arr[i] <= 10000) && (\forall int i; 0 <= i < arr.length; arr[i] >= 0));
//@ ensures((arr[0] == 3 && arr[1] == 5 && arr[2] == 5 && arr.length == 3) ==> (\result == false));
//@ ensures((arr[0] == 2 && arr[1] == 1 && arr.length == 2) ==> (\result == false));
//@ ensures((arr[0] == 0 && arr[1] == 3 && arr[2] == 2 && arr[3] == 1 && arr.length == 4) ==> (\result == true));
    public boolean validMountainArray(int[] arr) {
        int i = 0;
        for (; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                return false;
            } else if (arr[i] != arr[i + 1]) {
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
