package g0401_0500.s0412_fizz_buzz;

// #Easy #Top_Interview_Questions #String #Math #Simulation #Udemy_Integers
// #2022_07_16_Time_1_ms_(100.00%)_Space_48.4_MB_(48.76%)

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ @ requires 1 <= n && n <= 10000;
//@ @ ensures \result.length == n;
//@ @ ensures (\forall int i; 1 <= i && i <= n;
//@ @     (\result[i-1].equals("FizzBuzz") <==> (i % 3 == 0 && i % 5 == 0)) &&
//@ @     (\result[i-1].equals("Fizz") <==> (i % 3 == 0 && i % 5 != 0)) &&
//@ @     (\result[i-1].equals("Buzz") <==> (i % 5 == 0 && i % 3 != 0)) &&
//@ @     (\result[i-1].equals(Integer.toString(i)) <==> (i % 3 != 0 && i % 5 != 0))
//@ @ );
//@ @*/
//@ public String[] fizzBuzz(int n) {
//@ // Method implementation goes here
//@ }
//@ ```
//@ 
//@ ### Explanation:
//@ 
//@ - **Precondition (`requires`)**: The input `n` must be between 1 and 10,000 inclusive, as specified by the problem constraints.
//@ 
//@ - **Postcondition (`ensures`)**:
//@ - The length of the result array must be `n`.
//@ - For each index `i` (1-indexed in the problem, but 0-indexed in Java arrays), the following conditions must hold:
//@ - If `i` is divisible by both 3 and 5, the result at index `i-1` should be "FizzBuzz".
//@ - If `i` is divisible by 3 but not by 5, the result at index `i-1` should be "Fizz".
//@ - If `i` is divisible by 5 but not by 3, the result at index `i-1` should be "Buzz".
//@ - If `i` is not divisible by either 3 or 5, the result at index `i-1` should be the string representation of `i`.
//@ 
//@ This JML specification describes the expected behavior of the `fizzBuzz` method, ensuring that it produces the correct output for any valid input `n`.
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
