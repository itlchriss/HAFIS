// s0091 - Decode Ways
// Dafny formal specification (spec-only: DP string partitioning)

method numDecodings(s: string) returns (result: int)
    requires 1 <= |s| <= 100
    requires forall i: nat :: i < |s| ==> s[i] >= '0' && s[i] <= '9'
    ensures result >= 0
{
    result := 0;
    assume false;
}
