// s0228 - Summary Ranges
// Dafny formal specification (spec-only)
// requires(*The length of the integer array parameter `nums` is greater than or equal to 0 and is less than or equal to 20.*);
// requires(*All values in the integer array parameter `nums` are greater than or equal to -2147483648 and are less than or equal to 2147483647.*);
// requires(*All values in the integer array parameter `nums` are unique.*);
// requires(*The integer array parameter `nums` is sorted in ascending order.*);
// requires(*The integer array parameter `nums` is not equal to the null literal.*);
// ensures(*The list result is not equal to the null literal.*);
// ensures(*The list result is the smallest sorted list of ranges The list result cover all the numbers in the integer array parameter `nums` exactly.*);
// ensures(*If the integer array parameter `nums` is equal to [0,1,2,4,5,7], the list result is equal to ["0->2","4->5","7"].*);
// ensures(*If the integer array parameter `nums` is equal to [0,2,3,4,6,8,9], the list result is equal to ["0","2->4","6","8->9"].*);
// ensures(*If the integer array parameter `nums` is empty, the list result is empty.*);
// ensures(*If the integer array parameter `nums` is equal to [-1], the list result is equal to ["-1"].*);
// ensures(*If the integer array parameter `nums` is equal to [0], the list result is equal to ["0"].*);
method summaryRanges(nums: array<int>) returns (result: seq<string>)
{
    result := [];
    assume false;
}
