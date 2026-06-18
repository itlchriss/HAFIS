// s0387 - First Unique Character in a String
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 100000.*);
// requires(*The string parameter `s` consists of only lowercase English letters.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*The integer result is greater than or equal to -1 and is less than the length of the string parameter `s`.*);
// ensures(*If the integer result is greater than or equal to 0, the character at the integer result index of the string parameter `s` appears exactly 1 time in the string parameter `s`.*);
// ensures(*If the integer result is greater than or equal to 0, every character at an index smaller than the integer result in the string parameter `s` appears more than 1 time in the string parameter `s`.*);
// ensures(*If the integer result is equal to -1, every character in the string parameter `s` appears more than 1 time in the string parameter `s`.*);
// ensures(*If the string parameter `s` is equal to "leetcode", the integer result is equal to 0.*);
// ensures(*If the string parameter `s` is equal to "loveleetcode", the integer result is equal to 2.*);
// ensures(*If the string parameter `s` is equal to "aabb", the integer result is equal to -1.*);
method firstUniqChar(s: string) returns (result: int)
{
    result := -1;
    assume false;
}
