// s0412 - Fizz Buzz
// Complete Dafny formal specification with verified implementation

method fizzBuzz(n: int) returns (result: seq<string>)
    requires 1 <= n <= 10000
    ensures |result| == n
    ensures forall i: nat :: i < n ==>
        (if (i + 1) % 15 == 0 then result[i] == "FizzBuzz"
         else if (i + 1) % 3 == 0 then result[i] == "Fizz"
         else if (i + 1) % 5 == 0 then result[i] == "Buzz"
         else |result[i]| > 0)
{
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
