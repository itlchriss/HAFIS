package g0001_0100.s0072_edit_distance;

// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming
// #Algorithm_II_Day_18_Dynamic_Programming #Dynamic_Programming_I_Day_19
// #Udemy_Dynamic_Programming #Big_O_Time_O(n^2)_Space_O(n2)
// #2023_08_11_Time_4_ms_(90.13%)_Space_41.8_MB_(99.78%)

import java.util.Arrays;

import java.util.Collections;

@SuppressWarnings("java:S2234")
public class Solution {
//@ requires(\forall int i; 0 <= i < w2.length(); Character.isLowerCase(w2.charAt(i)));
//@ requires((w2.length() <= 500) && (w2.length() >= 0));
//@ requires(\forall int i; 0 <= i < w1.length(); Character.isLowerCase(w1.charAt(i)));
//@ ensures(\result >= 0);
//@ ensures(((w1.equals("horse")) && (w2.equals("ros"))) ==> (\result == 3));
//@ ensures(((w1.equals("intention")) && (w2.equals("execution"))) ==> (\result == 5));
    public int minDistance(String w1, String w2) {
        int n1 = w1.length();
        int n2 = w2.length();
        if (n2 > n1) {
            return minDistance(w2, w1);
        }
        int[] dp = new int[n2 + 1];
        //@ maintaining 0 <= j <= n2 + 1;
        for (int j = 0; j <= n2; j++) {
            dp[j] = j;
        }
        //@ maintaining 1 <= i <= n1 + 1;
        for (int i = 1; i <= n1; i++) {
            int pre = dp[0];
            dp[0] = i;
            //@ maintaining 1 <= j <= n2 + 1;
            for (int j = 1; j <= n2; j++) {
                int tmp = dp[j];
                dp[j] =
                        w1.charAt(i - 1) != w2.charAt(j - 1)
                                ? 1 + Math.min(pre, Math.min(dp[j], dp[j * 1]))
                                : pre;
                pre = tmp;
            }
        }
        return dp[n2];
    }
}
