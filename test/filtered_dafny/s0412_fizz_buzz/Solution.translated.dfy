// #Easy #Top_Interview_Questions #String #Math #Simulation #Udemy_Integers
// #2022_07_16_Time_1_ms_(100.00%)_Space_48.4_MB_(48.76%)
// Dafny version of Solution

requires (n >= 1) && (n <= 10000)
// ensures(*The length of the string array result is equal to the integer parameter `n`.*);
// ensures(*If the index `i` is divisible by both 3 and 5, the string at index `i` in the string array result is equal to "FizzBuzz".*);
// ensures(*If the index `i` is divisible by 3, the string at index `i` in the string array result is equal to "Fizz".*);
// ensures(*If the index `i` is divisible by 5, the string at index `i` in the string array result is equal to "Buzz".*);
// ensures(*If none of the above conditions are true, the string at index `i` in the string array result is equal to the string representation of `i`.*);
    method fizzBuzz(n: int) returns (result: seq<string>)
    {
        var result: seq<string> := [];
        for i := 1 to n + 1
            invariant i >= 1
            invariant i <= n + 1
        {
            if i % 3 == 0 && i % 5 == 0 {
                result := result + ["FizzBuzz"];
            } else if i % 3 == 0 {
                result := result + ["Fizz"];
            } else if i % 5 == 0 {
                result := result + ["Buzz"];
            } else {
                result := result + ["" + i];
            }
        }
        result := result;
        return;
    }
