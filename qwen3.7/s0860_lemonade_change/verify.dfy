// s0860 - Lemonade Change
// Dafny formal specification (spec-only: greedy change-making)
method lemonadeChange(bills: array<int>) returns (result: bool)
    requires 1 <= bills.Length <= 100000
    requires forall i: nat :: i < bills.Length ==> bills[i] == 5 || bills[i] == 10 || bills[i] == 20
{
    result := false;
    assume false;
}
