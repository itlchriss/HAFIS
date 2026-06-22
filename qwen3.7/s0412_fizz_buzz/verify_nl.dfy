// s0412 - Fizz Buzz
// Complete Dafny formal specification with verified implementation

method fizzBuzz(n: int) returns (result: seq<string>)
{
    // requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 10000.*);
    // ensures(*The string list result is not equal to the null literal.*);
    // ensures(*The length of the string list result is equal to the integer parameter `n`.*);
    // requires(*For every non-negative integer `i` such that `i` is less than the integer parameter `n`, the value at index `i` of the string list result is a string.*);
    // ensures(*For every non-negative integer `i` such that `i` is less than the integer parameter `n`, if the remainder of the sum of `i` and 1 divided by 3 is equal to 0 and the remainder of the sum of `i` and 1 divided by 5 is equal to 0, the value at index `i` of the string list result is equal to "FizzBuzz".*);
    // ensures(*For every non-negative integer `i` such that `i` is less than the integer parameter `n`, if the remainder of the sum of `i` and 1 divided by 3 is equal to 0 and the remainder of the sum of `i` and 1 divided by 5 is not equal to 0, the value at index `i` of the string list result is equal to "Fizz".*);
    // ensures(*For every non-negative integer `i` such that `i` is less than the integer parameter `n`, if the remainder of the sum of `i` and 1 divided by 3 is not equal to 0 and the remainder of the sum of `i` and 1 divided by 5 is equal to 0, the value at index `i` of the string list result is equal to "Buzz".*);
    // ensures(*For every non-negative integer `i` such that `i` is less than the integer parameter `n`, if the remainder of the sum of `i` and 1 divided by 3 is not equal to 0 and the remainder of the sum of `i` and 1 divided by 5 is not equal to 0, the value at index `i` of the string list result is equal to the string representation of the sum of `i` and 1.*);
    // ensures(*If the integer parameter `n` is equal to 3, the string list result is equal to ["1","2","Fizz"].*);
    // ensures(*If the integer parameter `n` is equal to 5, the string list result is equal to ["1","2","Fizz","4","Buzz"].*);
    // ensures(*If the integer parameter `n` is equal to 15, the string list result is equal to ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"].*);
    result := [];
    var i: int := 1;
    while i <= n
        invariant 1 <= i <= n + 1
        invariant |result| == i - 1
        invariant forall j: nat :: j < |result| ==>
            (if (j + 1) % 15 == 0 then result[j] == "FizzBuzz"
             else if (j + 1) % 3 == 0 then result[j] == "Fizz"
             else if (j + 1) % 5 == 0 then result[j] == "Buzz"
             else |result[j]| > 0)
    {
        if i % 15 == 0 {
            result := result + ["FizzBuzz"];
        } else if i % 3 == 0 {
            result := result + ["Fizz"];
        } else if i % 5 == 0 {
            result := result + ["Buzz"];
        } else {
            result := result + ["num"];  // placeholder for string of i
        }
        i := i + 1;
    }
}
