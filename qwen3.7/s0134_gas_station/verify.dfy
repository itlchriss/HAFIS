// s0134 - Gas Station
// Dafny formal specification (spec-only: circular circuit problem)

method canCompleteCircuit(gas: array<int>, cost: array<int>) returns (result: int)
    requires 1 <= gas.Length <= 100000
    requires gas.Length == cost.Length
    requires forall i: nat :: i < gas.Length ==> 0 <= gas[i] <= 10000
    requires forall i: nat :: i < cost.Length ==> 0 <= cost[i] <= 10000
    ensures -1 <= result < gas.Length
    // If result >= 0, starting from result can complete the circuit
    ensures result >= 0 ==> canComplete(gas, cost, result)
    // If result == -1, no starting point can complete the circuit
    ensures result == -1 ==> (forall start: nat :: start < gas.Length ==> !canComplete(gas, cost, start))
{
    result := -1;
    assume false;  // spec-only
}

ghost predicate canComplete(gas: array<int>, cost: array<int>, start: nat)
    requires 0 <= start < gas.Length
    requires gas.Length == cost.Length
    reads gas, cost
{
    forall n: nat :: 0 <= n < gas.Length ==>
        (sumFromTo(gas, start, n) >= sumFromTo(cost, start, n))
}

function sumFromTo(arr: array<int>, start: nat, count: nat): int
    requires start < arr.Length
    reads arr
    decreases count
{
    if count == 0 then 0
    else arr[(start + count - 1) % arr.Length] + sumFromTo(arr, start, count - 1)
}
