package g0401_0500.s0412_fizz_buzz;

// #Easy #Top_Interview_Questions #String #Math #Simulation #Udemy_Integers
// #2022_07_16_Time_1_ms_(100.00%)_Space_48.4_MB_(48.76%)

import java.util.ArrayList;
import java.util.List;

public class Solution {
//@ ensures(*The length of the list result is equal to the integer parameter `n`.*);
//@ ensures(*For every integer value v between 1 and the integer parameter `n` inclusive, if the integer value v mod 3 is equal to 0 and the integer value v mod 5 is equal to 0, the string at index v minus 1 in the list result is equal to the string literal FizzBuzz.*);
//@ ensures(*For every integer value v between 1 and the integer parameter `n` inclusive, if the integer value v mod 3 is equal to 0 and the integer value v mod 5 is not equal to 0, the string at index v minus 1 in the list result is equal to the string literal Fizz.*);
//@ ensures(*For every integer value v between 1 and the integer parameter `n` inclusive, if the integer value v mod 5 is equal to 0 and the integer value v mod 3 is not equal to 0, the string at index v minus 1 in the list result is equal to the string literal Buzz.*);
//@ ensures(*For every integer value v between 1 and the integer parameter `n` inclusive, if the integer value v mod 3 is not equal to 0 and the integer value v mod 5 is not equal to 0, the string at index v minus 1 in the list result is equal to the string representation of the integer value v.*);
//@ ensures(*If the integer parameter `n` is equal to 3, the list result is equal to ["1","2","Fizz"].*);
//@ ensures(*If the integer parameter `n` is equal to 5, the list result is equal to ["1","2","Fizz","4","Buzz"].*);
//@ ensures(*If the integer parameter `n` is equal to 15, the list result is equal to ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"].*);
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