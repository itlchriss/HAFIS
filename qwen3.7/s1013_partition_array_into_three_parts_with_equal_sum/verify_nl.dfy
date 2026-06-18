// s1013 - Partition Array Into Three Parts With Equal Sum
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `arr` is greater than or equal to 3 and is less than or equal to 50000.*);
// requires(*All values in the integer array parameter `arr` are greater than or equal to -10000 and are less than or equal to 10000.*);
// requires(*The integer array parameter `arr` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, there exist non-negative integers `the int array parameter `arr`` and `j` such the boolean result `the int array parameter `arr`` plus 1 is less than `j` and `j` is less than the length of the integer array parameter `arr` and the sum of all values from index 0 to index `the int array parameter `arr`` of the integer array parameter `arr` is equal to the sum of all values from index `the int array parameter `arr`` plus 1 to index `j` minus 1 of the integer array parameter `arr` and the sum of all values from index `j` to the last index of the integer array parameter `arr` is equal to the sum of all values from index 0 to index `the int array parameter `arr`` of the integer array parameter `arr`.*);
// ensures(*If the boolean result is equal to the false literal, there do not exist non-negative integers `the int array parameter `arr`` and `j` such the boolean result `the int array parameter `arr`` plus 1 is less than `j` and `j` is less than the length of the integer array parameter `arr` and the sum of all values from index 0 to index `the int array parameter `arr`` of the integer array parameter `arr` is equal to the sum of all values from index `the int array parameter `arr`` plus 1 to index `j` minus 1 of the integer array parameter `arr` and the sum of all values from index `j` to the last index of the integer array parameter `arr` is equal to the sum of all values from index 0 to index `the int array parameter `arr`` of the integer array parameter `arr`.*);
// ensures(*If the integer array parameter `arr` is equal to [0,2,1,-6,6,-7,9,1,2,0,1], the boolean result is equal to the true literal.*);
// ensures(*If the integer array parameter `arr` is equal to [0,2,1,-6,6,7,9,-1,2,0,1], the boolean result is equal to the false literal.*);
// ensures(*If the integer array parameter `arr` is equal to [3,3,6,5,-2,2,5,1,-9,4], the boolean result is equal to the true literal.*);
method canThreePartsEqualSum(arr: array<int>) returns (result: bool)
{
    result := false;
    assume false;
}
