// s0020 - Valid Parentheses
// Dafny formal specification (spec-only: stack-based matching)

method isValid(s: string) returns (result: bool)
    requires 1 <= |s| <= 10000
    requires forall i: nat :: i < |s| ==> s[i] in ['(', ')', '{', '}', '[', ']']
    ensures |s| % 2 != 0 ==> !result
    ensures result ==> |s| % 2 == 0
    // Test cases
    ensures s == "()" ==> result
    ensures s == "()[]{}" ==> result
    ensures s == "(]" ==> !result
    ensures s == "{[]}" ==> result
{
    result := false;
    assume false;  // spec-only
}
