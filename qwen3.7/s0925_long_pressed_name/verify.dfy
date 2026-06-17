// s0925 - Long Pressed Name
// Dafny formal specification (spec-only: nested loop two-pointer)

method isLongPressedName(name: string, typed: string) returns (result: bool)
    requires 1 <= |name| <= 1000
    requires 1 <= |typed| <= 1000
    requires forall i: nat :: i < |name| ==> 'a' <= name[i] <= 'z'
    requires forall i: nat :: i < |typed| ==> 'a' <= typed[i] <= 'z'
    ensures |typed| < |name| ==> !result
    // Test cases
    ensures name == "alex" && typed == "aaleex" ==> result
    ensures name == "saeed" && typed == "ssaaedd" ==> !result
{
    result := false;
    assume false;  // spec-only
}
