// s0941 - Valid Mountain Array
// Dafny formal specification (spec-only: structural array property)

// requires(*The length of the integer array parameter `arr` is greater than or equal to 1 and is less than or equal to 10000.*);
// requires(*All values in the integer array parameter `arr` are greater than or equal to 0 and are less than or equal to 10000.*);
// requires(*The integer array parameter `arr` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, there exists a non-negative integer `the int array parameter `arr`` the boolean result is greater than 0 and is less than the length of the integer array parameter `arr` minus 1 such the boolean result all values from index 0 to index `the int array parameter `arr`` of the integer array parameter `arr` are strictly increasing and all values from index `the int array parameter `arr`` to the last index of the integer array parameter `arr` are strictly decreasing.*);
// ensures(*If the boolean result is equal to the false literal, there does not exist a non-negative integer `the int array parameter `arr`` the boolean result is greater than 0 and is less than the length of the integer array parameter `arr` minus 1 such the boolean result all values from index 0 to index `the int array parameter `arr`` of the integer array parameter `arr` are strictly increasing and all values from index `the int array parameter `arr`` to the last index of the integer array parameter `arr` are strictly decreasing.*);
// ensures(*If the integer array parameter `arr` is equal to [2,1], the boolean result is equal to the false literal.*);
// ensures(*If the integer array parameter `arr` is equal to [3,5,5], the boolean result is equal to the false literal.*);
// ensures(*If the integer array parameter `arr` is equal to [0,3,2,1], the boolean result is equal to the true literal.*);
method validMountainArray(arr: array<int>) returns (result: bool)
{
    result := false;
    assume false;  // spec-only
}
