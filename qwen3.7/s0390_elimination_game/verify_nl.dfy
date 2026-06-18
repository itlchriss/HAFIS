// s0390 - Elimination Game
// Dafny formal specification (spec-only)
// requires(*The integer parameter `n` is greater than or equal to 1 and is less than or equal to 1000000000.*);
// ensures(*The integer result is greater than or equal to 1 and is less than or equal to the integer parameter `n`.*);
// ensures(*The integer result is the last number The integer result remains after applying the elimination algorithm on the list of all integers from 1 to the integer parameter `n`.*);
// ensures(*If the integer parameter `n` is equal to 9, the integer result is equal to 6.*);
// ensures(*If the integer parameter `n` is equal to 1, the integer result is equal to 1.*);
method lastRemaining(n: int) returns (result: int)
{
    result := 1;
    assume false;
}
