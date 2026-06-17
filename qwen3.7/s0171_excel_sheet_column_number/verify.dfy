// s0171 - Excel Sheet Column Number
// Dafny formal specification (spec-only: string parsing via base-26 decoding)

method titleToNumber(columnTitle: string) returns (result: int)
    requires 1 <= |columnTitle| <= 7
    requires forall i: nat :: i < |columnTitle| ==> 'A' <= columnTitle[i] <= 'Z'
    ensures result >= 1
    // The result is the base-26 decoding using A=1..Z=26
    // Test cases
    ensures columnTitle == "A" ==> result == 1
    ensures columnTitle == "AB" ==> result == 28
    ensures columnTitle == "ZY" ==> result == 701
{
    result := 1;
    assume false;  // spec-only
}
