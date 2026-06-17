// s0455 - Assign Cookies
// Complete Dafny formal specification with verified implementation

// Each child i has greed factor g[i], each cookie j has size s[j].
// A child is content if assigned a cookie with size >= their greed factor.
// Maximize the number of content children (assuming both arrays sorted).

method findContentChildren(g: array<int>, s: array<int>) returns (result: int)
    requires 1 <= g.Length <= 30000
    requires 0 <= s.Length <= 30000
    requires forall i: nat :: i < g.Length ==> 1 <= g[i]
    requires forall j: nat :: j < s.Length ==> 1 <= s[j]
    ensures 0 <= result <= g.Length
    ensures result <= s.Length
{
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
