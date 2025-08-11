package g0401_0500.s0412_fizz_buzz;

// #Easy #Top_Interview_Questions #String #Math #Simulation #Udemy_Integers
// #2022_07_16_Time_1_ms_(100.00%)_Space_48.4_MB_(48.76%)

import java.util.ArrayList;
import java.util.List;

public class Solution {
//@ requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 10000.*);
//@ ensures(*The length of the list result is equal to the integer parameter `n`.*);
//@ ensures(*If an element at index i in the list result is equal to "FizzBuzz", the value i+1 is divisible by 3 and the value i+1 is divisible by 5.*);
//@ ensures(*If an element at index i in the list result is equal to "Fizz", the value i+1 is divisible by 3 and the value i+1 is not divisible by 5.*);
//@ ensures(*If an element at index i in the list result is equal to "Buzz", the value i+1 is not divisible by 3 and the value i+1 is divisible by 5.*);
//@ ensures(*If an element at index i in the list result is not equal to "FizzBuzz" and is not equal to "Fizz" and is not equal to "Buzz", the element is equal to the string representation of i+1.*);
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