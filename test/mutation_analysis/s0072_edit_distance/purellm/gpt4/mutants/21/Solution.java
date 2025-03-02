package g0001_0100.s0072_edit_distance;

// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming
// #Algorithm_II_Day_18_Dynamic_Programming #Dynamic_Programming_I_Day_19
// #Udemy_Dynamic_Programming #Big_O_Time_O(n^2)_Space_O(n2)
// #2023_08_11_Time_4_ms_(90.13%)_Space_41.8_MB_(99.78%)

import java.util.Arrays;

import java.util.Collections;

@SuppressWarnings("java:S2234")
public class Solution {
// requires word1 != null && word2 != null;
// requires word1.length() >= 0 && word1.length() <= 500;
// requires word2.length() >= 0 && word2.length() <= 500;
// requires (\forall int i; 0 <= i && i < word1.length(); Character.isLowerCase(word1.charAt(i)));
// requires (\forall int i; 0 <= i && i < word2.length(); Character.isLowerCase(word2.charAt(i)));

//@ ensures \result >= 0;
// ensures \result == (\min int ops; (\exists String[] transformations; transformations.length == ops + 1 && transformations[0].equals(word1) && transformations[ops].equals(word2) && (\forall int i; 0 <= i < ops; (transformations[i].length() == transformations[i+1].length() + 1 && (\exists int j; 0 <= j < transformations[i].length(); transformations[i].substring(0, j).equals(transformations[i+1].substring(0, j)) && transformations[i].substring(j+1).equals(transformations[i+1].substring(j))) ) || (transformations[i].length() + 1 == transformations[i+1].length() && (\exists int j; 0 <= j < transformations[i+1].length(); transformations[i].substring(0, j).equals(transformations[i+1].substring(0, j)) && transformations[i].substring(j).equals(transformations[i+1].substring(j+1)) )) || (transformations[i].length() == transformations[i+1].length() &&(\exists int j; 0 <= j < transformations[i].length(); transformations[i].substring(0, j).equals(transformations[i+1].substring(0, j)) && transformations[i].charAt(j) != transformations[i+1].charAt(j) && transformations[i].substring(j+1).equals(transformations[i+1].substring(j+1)))))));
    public int minDistance(String w1, String w2) {
        int n1 = w1.length();
        int n2 = w2.length();
        if (n2 > n1) {
            return minDistance(w2, w1);
        }
        int[] dp = new int[n2 + 1];
        // maintaining 0 <= j <= n2 + 1;
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
                        w1.charAt(i + 1) != w2.charAt(j - 1)
                                ? 1 + Math.min(pre, Math.min(dp[j], dp[j - 1]))
                                : pre;
                pre = tmp;
            }
        }
        return dp[n2];
    }
}
