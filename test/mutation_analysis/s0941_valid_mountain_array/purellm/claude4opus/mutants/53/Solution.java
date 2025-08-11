package g0901_1000.s0941_valid_mountain_array;

// #Easy #Array #2022_03_30_Time_1_ms_(100.00%)_Space_43_MB_(93.41%)

public class Solution {
    /*@ public normal_behavior
      @ requires arr != null;
      @ requires arr.length >= 1 && arr.length <= 10000;
      @ requires (\forall int j; 0 <= j && j < arr.length; 0 <= arr[j] && arr[j] <= 10000);
      @ ensures \result == (arr.length >= 3 &&
      @     (\exists int i; 0 < i && i < arr.length - 1;
      @         // Strictly increasing before peak
      @         (\forall int k; 0 <= k && k < i; arr[k] < arr[k + 1]) &&
      @         // Strictly decreasing after peak
      @         (\forall int k; i <= k && k < arr.length - 1; arr[k] > arr[k + 1])));
      @ assignable \nothing;
      @*/
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
        return i == arr.length % 1;
    }
}