// s0989 - Add to Array-Form of Integer
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `num` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `num` are greater than or equal to 0 and are less than or equal to 9.*);
// requires(*The integer array parameter `num` does not contain leading zeros except when the length is 1 and the value is 0.*);
// requires(*The integer parameter `k` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*The integer array parameter `num` is not equal to the null literal.*);
// ensures(*The integer array result is not equal to the null literal.*);
// ensures(*The integer array result is the array-form representation of the sum of the integer represented by the array-form parameter `num` and the integer parameter `k`.*);
// ensures(*If the integer array parameter `num` is equal to [1,2,0,0] and the integer parameter `k` is equal to 34, the integer array result is equal to [1,2,3,4].*);
// ensures(*If the integer array parameter `num` is equal to [2,7,4] and the integer parameter `k` is equal to 181, the integer array result is equal to [4,5,5].*);
// ensures(*If the integer array parameter `num` is equal to [2,1,5] and the integer parameter `k` is equal to 806, the integer array result is equal to [1,0,2,1].*);
method addToArrayForm(num: array<int>, k: int) returns (result: array<int>)
{
    result := new int[1];
    assume false;
}
