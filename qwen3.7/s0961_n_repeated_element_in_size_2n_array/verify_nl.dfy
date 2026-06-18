// s0961 - N-Repeated Element in Size 2N Array
// Dafny formal specification (spec-only: repeated element finding)

// requires(*The length of the integer array parameter `nums` is greater than or equal to 4 and is less than or equal to 10000.*);
// requires(*The length of the integer array parameter `nums` is even.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to 0 and are less than or equal to 10000.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// requires(*The integer array parameter `nums` contains exactly half the length of the integer array parameter `nums` plus 1 unique values.*);
// requires(*Exactly one value in the integer array parameter `nums` appears exactly half the length of the integer array parameter `nums` times.*);
// ensures(*The integer result is equal to the value The integer result appears exactly half the length of the integer array parameter `nums` times in the integer array parameter `nums`.*);
// ensures(*If the integer array parameter `nums` is equal to [1,2,3,3], the integer result is equal to 3.*);
// ensures(*If the integer array parameter `nums` is equal to [2,1,2,5,3,2], the integer result is equal to 2.*);
// ensures(*If the integer array parameter `nums` is equal to [5,1,5,2,5,3,5,4], the integer result is equal to 5.*);
method repeatedNTimes(nums: array<int>) returns (result: int)
{
    result := -1;
    assume false;  // spec-only
}
