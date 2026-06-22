// s0888 - Fair Candy Swap
// Dafny formal specification (spec-only)
method fairCandySwap(aliceSizes: array<int>, bobSizes: array<int>) returns (result: array<int>)
{
    // requires(*The length of the integer array parameter `aliceSizes` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The length of the integer array parameter `bobSizes` is greater than or equal to 1 and is less than or equal to 10000.*);
    // requires(*The integer array parameter `aliceSizes` is not equal to the null literal.*);
    // requires(*The integer array parameter `bobSizes` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `aliceSizes` are greater than or equal to 1 and are less than or equal to 100000.*);
    // requires(*All values in the integer array parameter `bobSizes` are greater than or equal to 1 and are less than or equal to 100000.*);
    // ensures(*The integer array result is not equal to the null literal.*);
    // ensures(*The length of the integer array result is equal to 2.*);
    // ensures(*The first value of the integer array result is greater than or equal to 1 and is less than or equal to 100000.*);
    // ensures(*The second value of the integer array result is greater than or equal to 1 and is less than or equal to 100000.*);
    // ensures(*If the integer array parameter `aliceSizes` is equal to [1,1] and the integer array parameter `bobSizes` is equal to [2,2], the integer array result is equal to [1,2].*);
    // ensures(*If the integer array parameter `aliceSizes` is equal to [1,2] and the integer array parameter `bobSizes` is equal to [2,3], the integer array result is equal to [1,2].*);
    // ensures(*If the integer array parameter `aliceSizes` is equal to [2] and the integer array parameter `bobSizes` is equal to [1,3], the integer array result is equal to [2,3].*);
    result := new int[2];
    assume false;
}
