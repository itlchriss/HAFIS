package g0401_0500.s0412_fizz_buzz;

// #Easy #Top_Interview_Questions #String #Math #Simulation #Udemy_Integers
// #2022_07_16_Time_1_ms_(100.00%)_Space_48.4_MB_(48.76%)

import java.util.ArrayList;
import java.util.List;

public class Solution {
//@ ensures(*result List<String> contains exactly int `n` elements and is conceptually indexed starting from 1*);
//@ ensures(*For every index position in result List<String> that is divisible by 3 and 5 result element equals the string value FizzBuzz*);
//@ ensures(*For every index position in result List<String> that is divisible by 3 but not divisible by 5 result element equals the string value Fizz*);
//@ ensures(*For every index position in result List<String> that is divisible by 5 but not divisible by 3 result element equals the string value Buzz*);
//@ ensures(*For every index position in result List<String> that is not divisible by 3 or 5 result element equals the string representation of that index*);
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                result.add("FizzBuzz");
            } else if (i % 3 == 0) {
                result.add("Fizz");
            } else if (i % 5 == 0) {
                result.add("Buzz");
            } else {
                result.add(Integer.toString(i));
            }
        }
        return result;
    }
}