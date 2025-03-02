package g0301_0400.s0390_elimination_game;

// #Medium #Math #Recursion #2022_07_13_Time_4_ms_(81.16%)_Space_42_MB_(71.14%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires 1 <= n && n <= 1000000000;
// ensures \result == (\exists int[] arr; (\forall int i; 0 <= i && i < n; arr[i] == i + 1) && (\forall int step; step >= 0; (\forall int i; 0 <= i && i < arr.length; arr[i] == (step % 2 == 0 ? arr[2 * i + 1] : arr[arr.length - 2 * i - 1]))); arr.length == 1; \result == arr[0]);
    public int lastRemaining(int n) {
        return n <= 1 ? 1 : 2 * (n / 2 - lastRemaining(n / 2) + 1);
    }
}
