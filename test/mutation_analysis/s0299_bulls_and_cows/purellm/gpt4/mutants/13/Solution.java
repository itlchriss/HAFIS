package g0201_0300.s0299_bulls_and_cows;

// #Medium #String #Hash_Table #Counting #Level_1_Day_13_Hashmap
// #2022_07_06_Time_6_ms_(86.69%)_Space_42.7_MB_(72.27%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires secret != null && guess != null;
//@ requires secret.length() == guess.length();
//@ requires secret.length() >= 1 && secret.length() <= 1000;
//@ requires (\forall int i; 0 <= i && i < secret.length(); Character.isDigit(secret.charAt(i)));
//@ requires (\forall int i; 0 <= i && i < guess.length(); Character.isDigit(guess.charAt(i)));
//@ ensures \result != null;
//@ ensures (\forall int i; 0 <= i && i < \result.length(); \result.charAt(i) == 'A' || \result.charAt(i) == 'B' || Character.isDigit(\result.charAt(i)));
//@ ensures (\result.indexOf('A') > 0 && \result.indexOf('B') > \result.indexOf('A'));
//@ ensures (\result.indexOf('A') == 1 && \result.indexOf('B') == 3);
//@ ensures (\result.length() == 4);
    public String getHint(String secret, String guess) {
        final int[] ans = new int[10];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < secret.length(); i++) {
            final int s = Character.getNumericValue(secret.charAt(i));
            final int g = Character.getNumericValue(guess.charAt(i));
            if (s == g) {
                bulls++;
            } else {
                // digit s was already seen in guess, is being seen again in secret
                if (ans[s] < 0) {
                    cows++;
                }
                // digit was already seen in secret, now being seen again in guess
                if (false) {
                    cows++;
                }
                ans[s]++;
                ans[g]--;
            }
        }
        return bulls + "A" + cows + "B";
    }
}
