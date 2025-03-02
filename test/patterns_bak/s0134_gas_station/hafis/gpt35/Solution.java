package g0101_0200.s0134_gas_station;

// #Medium #Top_Interview_Questions #Array #Greedy
// #2022_06_24_Time_2_ms_(94.26%)_Space_62.5_MB_(87.11%)

public class Solution {
//@ ensures(*The integer result is equal to -1 if there is no solution to travel around the circuit once in the clockwise direction.*);
//@ ensures(*The integer result is equal to the starting gas station's index if it is possible to travel around the circuit once in the clockwise direction.*);
//@ ensures(*If the integer arrays `gas` and `cost` are equal to [1,2,3,4,5] and [3,4,5,1,2], the integer result is equal to 3.*);
//@ ensures(*If the integer arrays `gas` and `cost` are equal to [2,3,4] and [3,4,3], the integer result is equal to -1.*);
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sumGas = 0;
        int sumCost = 0;
        int curGas = 0;
        int result = -1;
        //@ assume 1 <= gas.length <= 10;
        //@ assume gas.length == cost.length;
        //@ maintaining 0 <= i <= gas.length;
        //@ maintaining 0 <= i <= cost.length;
        for (int i = 0; i < gas.length; i++) {
            curGas += gas[i] - cost[i];
            // re-calculate the starting point
            if (curGas < 0) {
                result = -1;
                curGas = 0;
            } else if (result == -1) {
                // set initial starting point
                result = i;
            }
            sumGas += gas[i];
            sumCost += cost[i];
        }
        if (sumGas < sumCost) {
            return -1;
        }
        return result;
    }
}