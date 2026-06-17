// s0393 - UTF-8 Validation
// Dafny formal specification (spec-only)
method validUtf8(data: array<int>) returns (result: bool)
    requires 1 <= data.Length <= 200
    requires forall i: nat :: i < data.Length ==> 0 <= data[i] <= 255
{
    result := false;
    assume false;
}
