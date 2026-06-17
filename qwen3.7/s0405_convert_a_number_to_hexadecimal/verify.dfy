// s0405 - Convert a Number to Hexadecimal
// Dafny formal specification (spec-only: hex conversion)

method toHex(num: int) returns (result: string)
    requires -2147483648 <= num <= 2147483647
    ensures |result| >= 1
    ensures forall i: nat :: i < |result| ==>
        (result[i] >= '0' && result[i] <= '9') ||
        (result[i] >= 'a' && result[i] <= 'f')
    ensures num == 0 ==> result == "0"
    ensures num > 0 ==> result[0] != '0' || |result| == 1
{
    result := "0";
    assume false;  // spec-only
}
