// s0299 - Bulls and Cows
// Dafny formal specification (spec-only)
method getHint(secret: string, guess: string) returns (result: string)
    requires |secret| == |guess|
    requires 1 <= |secret| <= 1000
    ensures |result| >= 3
{
    result := "0A0B";
    assume false;
}
