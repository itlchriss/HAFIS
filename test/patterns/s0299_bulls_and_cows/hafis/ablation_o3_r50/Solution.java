package g0201_0300.s0299_bulls_and_cows;

// #Medium #String #Hash_Table #Counting #Level_1_Day_13_Hashmap
// #2022_07_06_Time_6_ms_(86.69%)_Space_42.7_MB_(72.27%)

public class Solution {
//@ requires(*The length of the String parameter `guess` is greater than or equal to 1 and is less than or equal to 1000.*);
//@ requires(*The length of the String parameter `secret` is equal to the length of the String parameter `guess`.*);
//@ requires(*The String parameter `secret` consists of digits only.*);
//@ requires(*The String parameter `guess` consists of digits only.*);
//@ ensures(*The String result consists of one or more decimal digits followed by the uppercase letter A followed by one or more decimal digits followed by the uppercase letter B.*);
//@ ensures(*The integer value represented by the digits that appear before the uppercase letter A in the String result is greater than or equal to 0 and is less than or equal to the length of the String parameter `secret`.*);
//@ ensures(*The integer value represented by the digits that appear between the uppercase letters A and B in the String result is greater than or equal to 0 and is less than or equal to the length of the String parameter `secret`.*);
//@ ensures(*The sum of the integer values represented before the uppercase letter A and between the uppercase letters A and B in the String result is less than or equal to the length of the String parameter `secret`.*);
//@ ensures(*The integer value represented by the digits that appear before the uppercase letter A in the String result is equal to the count of indices where the character at that index in the String parameter `secret` is equal to the character at that index in the String parameter `guess`.*);
//@ ensures(*The integer value represented by the digits that appear between the uppercase letters A and B in the String result is equal to the count of digits that are present in both the String parameter `secret` and the String parameter `guess` excluding the positions counted as bulls.*);
//@ ensures(*If the String parameter `secret` is equal to "1807" and the String parameter `guess` is equal to "7810", the String result is equal to "1A3B".*);
//@ ensures(*If the String parameter `secret` is equal to "1123" and the String parameter `guess` is equal to "0111", the String result is equal to "1A1B".*);
//@ ensures(*If the String parameter `secret` is equal to "1" and the String parameter `guess` is equal to "0", the String result is equal to "0A0B".*);
//@ ensures(*If the String parameter `secret` is equal to "1" and the String parameter `guess` is equal to "1", the String result is equal to "1A0B".*);
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
                if (ans[g] > 0) {
                    cows++;
                }
                ans[s]++;
                ans[g]--;
            }
        }
        return bulls + "A" + cows + "B";
    }
}