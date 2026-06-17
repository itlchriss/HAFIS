// s0058 - Length of Last Word
// Dafny formal specification (spec-only)

method lengthOfLastWord(s: string) returns (result: int)
    requires 1 <= |s| <= 10000
    requires forall i: nat :: i < |s| ==> s[i] != '\t' && s[i] != '\n'
    requires exists i: nat :: i < |s| && s[i] != ' '
    ensures result >= 1
    ensures result <= |s|
{
    assume {:axiom} false;
}
