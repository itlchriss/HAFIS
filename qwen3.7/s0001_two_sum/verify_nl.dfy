// s0001 - Two Sum
// Dafny formal specification (spec-only: hash-based pair finding)

// requires(*The length of the integer array parameter `numbers` is greater than or equal to 2 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `numbers` are greater than or equal to -1000000000 and are less than or equal to 1000000000.*);
// requires(*The integer parameter `target` is greater than or equal to -1000000000 and is less than or equal to 1000000000.*);
// requires(*The integer array parameter `numbers` is not equal to the null literal.*);
// requires(*Exactly one pair of distinct indices exists in the integer array parameter `numbers` the int array parameter `numbers` corresponding values sum to the integer parameter `target`.*);
// ensures(*The integer array result is not equal to the null literal.*);
// ensures(*The length of the integer array result is equal to 2.*);
// ensures(*The first value of the integer array result is not equal to the second value of the integer array result.*);
// ensures(*Both values in the integer array result are greater than or equal to 0 and are less than the length of the integer array parameter `numbers`.*);
// ensures(*All values in the integer array result are unique.*);
// ensures(*The sum between the value at the index equal to the first value of the integer array result of the integer array parameter `numbers` and the value at the index equal to the second value of the integer array result of the integer array parameter `numbers` is equal to the integer parameter `target`.*);
// ensures(*If the integer array parameter `numbers` is equal to [2,7,11,15] and the integer parameter `target` is equal to 9, the integer array result is equal to [0,1].*);
// ensures(*If the integer array parameter `numbers` is equal to [3,2,4] and the integer parameter `target` is equal to 6, the integer array result is equal to [1,2].*);
// ensures(*If the integer array parameter `numbers` is equal to [3,3] and the integer parameter `target` is equal to 6, the integer array result is equal to [0,1].*);
method twoSum(numbers: array<int>, target: int) returns (result: array<int>)
{
    result := new int[2];
    assume false;  // spec-only
}
