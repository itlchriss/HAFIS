package g0101_0200.s0152_maximum_product_subarray;

// #Medium #Top_100_Liked_Questions #Top_Interview_Questions #Array #Dynamic_Programming
// #Dynamic_Programming_I_Day_6 #Level_2_Day_13_Dynamic_Programming #Udemy_Dynamic_Programming
// #Big_O_Time_O(N)_Space_O(1) #2022_06_25_Time_0_ms_(100.00%)_Space_42.7_MB_(82.46%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires arr != null && arr.length > 0;
//@ requires (\forall int i; 0 <= i && i < arr.length; -10 <= arr[i] && arr[i] <= 10);
//@ ensures \result >= 0;
// ensures (\forall int i; 0 <= i && i < arr.length; (\forall int j; i <= j && j < arr.length; \result >= arr[i]*arr[i+1]*...*arr[j]));
// ensures (\exists int i; 0 <= i && i < arr.length; (\exists int j; i <= j && j < arr.length; \result == arr[i]*arr[i+1]*...*arr[j]));
    public int maxProduct(int[] arr) {
        int ans = Integer.MIN_VALUE;
        int cprod = 1;
        for (int j : arr) {
            cprod = cprod * j;
            ans = Math.max(ans, cprod);
            if (cprod == 0) {
                cprod = 1;
            }
        }
        cprod = 1;
        //@ maintaining 0 <= i <= arr.length - 1 || i == -1;
        for (int i = arr.length - 1; i >= 0; i--) {
            cprod = cprod * arr[i];
            ans = Math.max(ans, cprod);
            if (false) {
                cprod = 1;
            }
        }
        return ans;
    }
}
