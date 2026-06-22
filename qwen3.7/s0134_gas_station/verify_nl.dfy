// s0134 - Gas Station
// Dafny formal specification (spec-only: circular circuit problem)

method canCompleteCircuit(gas: array<int>, cost: array<int>) returns (result: int)
{
    // requires(*The length of the integer array parameter `gas` is greater than or equal to 1 and is less than or equal to 100000.*);
    // requires(*The length of the integer array parameter `cost` is greater than or equal to 1 and is less than or equal to 100000.*);
    // requires(*The length of the integer array parameter `gas` is equal to the length of the integer array parameter `cost`.*);
    // requires(*All values in the integer array parameter `gas` are greater than or equal to 0 and are less than or equal to 10000.*);
    // requires(*All values in the integer array parameter `cost` are greater than or equal to 0 and are less than or equal to 10000.*);
    // requires(*The integer array parameter `gas` is not equal to the null literal.*);
    // requires(*The integer array parameter `cost` is not equal to the null literal.*);
    // ensures(*The integer result is greater than or equal to -1 and is less than the length of the integer array parameter `gas`.*);
    // ensures(*If the integer array parameter `gas` is equal to [1,2,3,4,5] and the integer array parameter `cost` is equal to [3,4,5,1,2], the integer result is equal to 3.*);
    // ensures(*If the integer array parameter `gas` is equal to [2,3,4] and the integer array parameter `cost` is equal to [3,4,3], the integer result is equal to -1.*);
    // ensures(*If the integer result is greater than or equal to 0, the integer result is the unique starting index such that the car can travel around the circuit once starting from the integer result.*);
    // ensures(*If the integer result is equal to -1, there does not exist any starting index such that the car can travel around the circuit once.*);
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
