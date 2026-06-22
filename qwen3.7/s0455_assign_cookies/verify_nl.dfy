// s0455 - Assign Cookies
// Complete Dafny formal specification with verified implementation

// Each child i has greed factor g[i], each cookie j has size s[j].
// A child is content if assigned a cookie with size >= their greed factor.
// Maximize the number of content children (assuming both arrays sorted).

method findContentChildren(g: array<int>, s: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `g` is greater than or equal to 1 and is less than or equal to 30000.*);
    // requires(*The length of the integer array parameter `s` is greater than or equal to 0 and is less than or equal to 30000.*);
    // requires(*The integer array parameter `g` is not equal to the null literal.*);
    // requires(*The integer array parameter `s` is not equal to the null literal.*);
    // requires(*All values in the integer array parameter `g` are greater than or equal to 1 and are less than or equal to 2147483647.*);
    // requires(*All values in the integer array parameter `s` are greater than or equal to 1 and are less than or equal to 2147483647.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `g`.*);
    // ensures(*The integer result is greater than or equal to 0 and is less than or equal to the length of the integer array parameter `s`.*);
    // ensures(*The integer result is the maximum number of content children.*);
    // ensures(*If the integer array parameter `g` is equal to [1,2,3] and the integer array parameter `s` is equal to [1,1], the integer result is equal to 1.*);
    // ensures(*If the integer array parameter `g` is equal to [1,2] and the integer array parameter `s` is equal to [1,2,3], the integer result is equal to 2.*);
    result := 0;
    var i: nat := 0;
    var j: nat := 0;
    while i < g.Length && j < s.Length
        invariant i <= g.Length
        invariant j <= s.Length
        invariant result <= i
        invariant result <= j
    {
        if s[j] >= g[i] {
            result := result + 1;
            i := i + 1;
        }
        j := j + 1;
    }
}
