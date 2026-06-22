// s0033 - Search in Rotated Sorted Array
// Dafny formal specification (spec-only: binary search on rotated array)

method search(nums: array<int>, target: int) returns (result: int)
{
    // requires(*The length of the integer array parameter `nums` is greater than or equal to 1 and is less than or equal to 5000.*);
    // requires(*All values in the integer array parameter `nums` are greater than or equal to -10000 and are less than or equal to 10000.*);
    // requires(*All values in the integer array parameter `nums` are unique.*);
    // requires(*The integer parameter `target` is greater than or equal to -10000 and is less than or equal to 10000.*);
    // requires(*The integer array parameter `nums` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to -1 and is less than the length of the integer array parameter `nums`.*);
    // ensures(*If the integer result is greater than or equal to 0, the value at index the integer result of the integer array parameter `nums` is equal to the integer parameter `target`.*);
    // ensures(*If the integer result is equal to -1, the integer parameter `target` is not contained in the integer array parameter `nums`.*);
    // ensures(*If the integer array parameter `nums` is equal to [4,5,6,7,0,1,2] and the integer parameter `target` is equal to 0, the integer result is equal to 4.*);
    // ensures(*If the integer array parameter `nums` is equal to [4,5,6,7,0,1,2] and the integer parameter `target` is equal to 3, the integer result is equal to -1.*);
    // ensures(*If the integer array parameter `nums` is equal to [1] and the integer parameter `target` is equal to 0, the integer result is equal to -1.*);
    result := -1;
    assume false;
}
