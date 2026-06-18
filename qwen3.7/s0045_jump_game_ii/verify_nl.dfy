// s0045 - Jump Game II
// Dafny formal specification (spec-only: minimum jumps to end)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 1000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to 0.*);
// ensures(*The integer result is the minimum number of jumps to reach the last index of the integer array parameter `nums` from the first index of the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [2,3,1,1,4], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [2,3,0,1,4], the integer result is equal to 2.*);
method jump(nums: array<int>) returns (result: int)
{
    result := 0;
    assume false;
}
