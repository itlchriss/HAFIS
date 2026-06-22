// s0941 - Valid Mountain Array
// Dafny formal specification (spec-only: structural array property)

method validMountainArray(arr: array<int>) returns (result: bool)
{
    // requires(*The length of the integer array parameter `arr` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The integer array parameter `arr` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `arr` are greater than or equal to 0 and are less than or equal to 10000.*);
    // ensures(*The boolean result is equal to the true literal if and only if the integer array parameter `arr` is a valid mountain array.*);
    // ensures(*If the integer array parameter `arr` is equal to [2,1], the boolean result is equal to the false literal.*);
    // ensures(*If the integer array parameter `arr` is equal to [3,5,5], the boolean result is equal to the false literal.*);
    // ensures(*If the integer array parameter `arr` is equal to [0,3,2,1], the boolean result is equal to the true literal.*);
    result := false;
    assume false;  // spec-only
}
