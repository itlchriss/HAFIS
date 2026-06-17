// s0504 - Base 7
// Dafny formal specification (spec-only, no verified implementation)

// Convert an integer to its base 7 string representation.

method convertToBase7(num: int) returns (result: string)
    requires -10000000 <= num <= 10000000
    ensures |result| > 0
    ensures num == 100 ==> result == "202"
    ensures num == -7 ==> result == "-10"
    ensures num == 0 ==> result == "0"
    ensures num == 7 ==> result == "10"
    ensures num == -100 ==> result == "-202"
    ensures num == 49 ==> result == "100"
    // All characters in result (excluding optional leading '-') are base-7 digits
    ensures num >= 0 ==> forall i: nat :: i < |result| ==> '0' <= result[i] <= '6'
    ensures num < 0 ==> result[0] == '-'
    ensures num < 0 ==> forall i: nat :: 1 <= i < |result| ==> '0' <= result[i] <= '6'
    // No leading zeros (except for the value 0 itself)
    ensures |result| > 1 ==> result[0] != '0' && result[0] != '-'
{
    // Implementation stub - not verified
    assume {:axiom} false;
}
