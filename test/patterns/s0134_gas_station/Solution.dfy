// #Medium #Top_Interview_Questions #Array #Greedy
// #2022_06_24_Time_2_ms_(94.26%)_Space_62.5_MB_(87.11%)
// Dafny version of Solution

// requires(*The length of the integer arrays `gas` and `cost` is less than or equal to 100000 and is greater than or equal to 1.*);
// requires(*All values in the integer arrays `gas` and `cost` are less than or equal to 10000 and are greater than or equal to 0.*);
// ensures(*The integer result is equal to the starting gas station's index if it is possible to travel around the circuit once in the clockwise direction, otherwise, the integer result is equal to -1.*);
// ensures(*If the integer result is equal to 3, the starting gas station's index is 3.*);
// ensures(*If the integer result is equal to -1, it means it is not possible to travel around the circuit once no matter where you start.*);
    method canCompleteCircuit(gas: array<int>, cost: array<int>) returns (result: int)
    {
        var sumGas: int := 0;
        var sumCost: int := 0;
        var curGas: int := 0;
        var result: int := -1;
        // invariant //@ maintaining 0 <= i <= gas.length;
        // invariant //@ maintaining 0 <= i <= cost.length;
        for i := 0 to |gas|
            invariant i >= 0
            invariant i <= |gas|
        {
            curGas := curGas + gas[i] - cost[i];
            // re-calculate the starting point
            if curGas < 0 {
                result := -1;
                curGas := 0;
            } else if result == -1 {
                // set initial starting point
                result := i;
            }
            sumGas := sumGas + gas[i];
            sumCost := sumCost + cost[i];
        }
        if sumGas < sumCost {
            result := -1;
            return;
        }
        result := result;
        return;
    }
