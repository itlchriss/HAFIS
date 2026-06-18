// s0462 - Minimum Moves to Equal Array Elements II
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is the minimum number of moves required to make all array elements equal where each move increments or decrements an element by 1.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [1,10,2,9], the integer result is equal to 16.*);
method minMoves2(nums: array<int>) returns (result: int)
{
    result := 0;
    assume false;
}
