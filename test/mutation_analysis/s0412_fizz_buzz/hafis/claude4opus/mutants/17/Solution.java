package g0401_0500.s0412_fizz_buzz;

// #Easy #Top_Interview_Questions #String #Math #Simulation #Udemy_Integers
// #2022_07_16_Time_1_ms_(100.00%)_Space_48.4_MB_(48.76%)

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((n >= 1) && (n <= 10000));
//@ ensures(\result.size() == n);
//@ ensures((n == 5) ==> (\result.get(0) == "1"  && \result.get(1) ==  "2"  && \result.get(2) ==  "Fizz"  && \result.get(3) ==  "4"  && \result.get(4) ==  "Buzz"));
//@ ensures((n == 15) ==> (\result.get(0) == "1"  && \result.get(1) ==  "2"  && \result.get(2) ==  "Fizz"  && \result.get(3) ==  "4"  && \result.get(4) ==  "Buzz"  && \result.get(5) ==  "Fizz"  && \result.get(6) ==  "7"  && \result.get(7) ==  "8"  && \result.get(8) ==  "Fizz"  && \result.get(9) ==  "Buzz"  && \result.get(10) ==  "11"  && \result.get(11) ==  "Fizz"  && \result.get(12) ==  "13"  && \result.get(13) ==  "14"  && \result.get(14) ==  "FizzBuzz"));
//@ ensures((n == 3) ==> (\result.get(0) == "1"  && \result.get(1) ==  "2"  && \result.get(2) ==  "Fizz"));
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 == (i % 5 == 0)) {
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
