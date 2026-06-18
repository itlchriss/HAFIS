// s0001 - Two Sum
// Dafny formal specification (spec-only: hash-based pair finding)

// requires(*Exactly one pair of distinct indices exists in the integer array parameter `numbers` the int array parameter `numbers` corresponding values sum to the integer parameter `target`.*);
// ensures(*The length of the integer array result is equal to 2.*);
// ensures(*The first value of the integer array result is not equal to the second value of the integer array result.*);
// ensures(*The sum between the value at the index equal to the first value of the integer array result of the integer array parameter `numbers` and the value at the index equal to the second value of the integer array result of the integer array parameter `numbers` is equal to the integer parameter `target`.*);
// ensures(*If the integer array parameter `numbers` is equal to [2,7,11,15] and the integer parameter `target` is equal to 9, the integer array result is equal to [0,1].*);
// ensures(*If the integer array parameter `numbers` is equal to [3,2,4] and the integer parameter `target` is equal to 6, the integer array result is equal to [1,2].*);
// ensures(*If the integer array parameter `numbers` is equal to [3,3] and the integer parameter `target` is equal to 6, the integer array result is equal to [0,1].*);
method twoSum(numbers: array<int>, target: int) returns (result: array<int>)
    requires (numbers.length >= 2) && (numbers.length <= 10000)
    requires (numbers.length >= 2) && (numbers.length <= 10000)
    requires (numbers.size() >= 2) && (numbers.size() <= 10000)
    requires (numbers.length() >= 2) && (numbers.length() <= 10000)
    requires (numbers.size() >= 2) && (numbers.size() <= 10000)
    requires (numbers.length >= 2) && (numbers.length <= 10000)
    requires (numbers.length >= 2) && (numbers.length <= 10000)
    requires (numbers.size() >= 2) && (numbers.length <= 10000)
    requires (numbers.length() >= 2) && (numbers.length <= 10000)
    requires (numbers.size() >= 2) && (numbers.length <= 10000)
    requires (\forall int i; 0 <= i < numbers.length; numbers[i] >= -1000000000) && (\forall int i; 0 <= i < numbers.length; numbers[i] <= 1000000000)
    requires (target >= -1000000000) && (target <= 1000000000)
    requires numbers != null
    ensures \result != null
    ensures (\forall int i; 0 <= i < \result.length; \result[i] >= 0) && (\forall int i; 0 <= i < \result.length; \result[i] < numbers.length)
    ensures (\forall int i; 0 <= i < \result.length; \result[i] >= 0) && (\forall int i; 0 <= i < \result.length; \result[i] < numbers.length)
    ensures (\forall int i; 0 <= i < \result.length; \result[i] >= 0) && (\forall int i; 0 <= i < \result.length; \result[i] < numbers.size())
    ensures (\forall int i; 0 <= i < \result.length; \result[i] >= 0) && (\forall int i; 0 <= i < \result.length; \result[i] < numbers.length())
    ensures (\forall int i; 0 <= i < \result.length; \result[i] >= 0) && (\forall int i; 0 <= i < \result.length; \result[i] < numbers.size())
    ensures \forall int i; 0 <= i < \result.length; (\forall int j; 0 <= j < \result.length && j != i; \result[j] != \result[i])
{
    result := new int[2];
    assume false;  // spec-only
}
