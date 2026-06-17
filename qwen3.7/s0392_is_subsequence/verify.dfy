// s0392 - Is Subsequence
// Dafny formal specification (spec-only: subsequence checking)

method isSubsequence(s: string, t: string) returns (result: bool)
    requires 0 <= |s| <= 100
    requires 0 <= |t| <= 10000
    requires forall i: nat :: i < |s| ==> 'a' <= s[i] <= 'z'
    requires forall i: nat :: i < |t| ==> 'a' <= t[i] <= 'z'
    ensures |s| == 0 ==> result
    ensures |s| > |t| ==> !result
    // Test cases
    ensures s == "abc" && t == "ahbgdc" ==> result
    ensures s == "axc" && t == "ahbgdc" ==> !result
{
    result := false;
    assume false;  // spec-only
}
