// #Medium #String #Hash_Table #Counting #Level_1_Day_13_Hashmap
// #2022_07_06_Time_6_ms_(86.69%)_Space_42.7_MB_(72.27%)
// Dafny version of Solution

// requires(*The length of the string parameter `secret` is less than or equal to 1000 and is greater than or equal to 1.*);
// requires(*The length of the string parameter `guess` is less than or equal to 1000 and is greater than or equal to 1.*);
// requires(*The string parameter `secret` and `guess` have the same length.*);
// requires(*The string parameter `secret` and `guess` consist of digits only.*);
// ensures(*The string result is formatted as "xAyB" where x is the number of bulls and y is the number of cows.*);
// ensures(*If the string parameter `secret` is equal to "1807" and the string parameter `guess` is equal to "7810", the string result is equal to "1A3B".*);
// ensures(*If the string parameter `secret` is equal to "1123" and the string parameter `guess` is equal to "0111", the string result is equal to "1A1B".*);
// ensures(*If the string parameter `secret` is equal to "1" and the string parameter `guess` is equal to "0", the string result is equal to "0A0B".*);
// ensures(*If the string parameter `secret` is equal to "1" and the string parameter `guess` is equal to "1", the string result is equal to "1A0B".*);
    method getHint(secret: string, guess: string) returns (result: string)
    {
        var ans := new int[10];
        var bulls: int := 0;
        var cows: int := 0;
        for i := 0 to |secret|()
            invariant i >= 0
            invariant i <= |secret|()
        {
            var s: int := Character.getNumericValue(secret[i]);
            var g: int := Character.getNumericValue(guess[i]);
            if s == g {
                bulls := bulls + 1;
            } else {
                // digit s was already seen in guess, is being seen again in secret
                if ans[s] < 0 {
                    cows := cows + 1;
                }
                // digit was already seen in secret, now being seen again in guess
                if ans[g] > 0 {
                    cows := cows + 1;
                }
                // ans[s]++;
                // ans[g]--;
            }
        }
        result := bulls + "A" + cows + "B";
        return;
    }
