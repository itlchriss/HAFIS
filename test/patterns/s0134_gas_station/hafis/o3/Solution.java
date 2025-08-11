package g0101_0200.s0134_gas_station;

// #Medium #Top_Interview_Questions #Array #Greedy
// #2022_06_24_Time_2_ms_(94.26%)_Space_62.5_MB_(87.11%)

public class Solution {
//@ requires(*The length of the integer array parameter `cost` is equal to the length of the integer array parameter `gas`.*);
//@ requires(*All values in the integer array parameter `gas` are greater than or equal to 0 and are less than or equal to 10000.*);
//@ requires(*All values in the integer array parameter `cost` are greater than or equal to 0 and are less than or equal to 10000.*);
//@ ensures(*The integer result is greater than or equal to -1 and is less than the length of the integer array parameter `gas`.*);
//@ ensures(*If the integer result is equal to -1, the sum of all values in the integer array parameter `gas` is less than the sum of all values in the integer array parameter `cost`.*);
//@ ensures(*If the integer result is greater than or equal to 0, starting from the index equal to the integer result and moving clockwise, the running total of the difference between each value in the integer array parameter `gas` and the corresponding value in the integer array parameter `cost` is never negative and the traversal returns to the starting index.*);
//@ ensures(*If the integer result is greater than or equal to 0, no other index in the integer array parameter `gas` satisfies the previous specification.*);
//@ ensures(*If the integer array parameter `gas` is equal to [1,2,3,4,5] and the integer array parameter `cost` is equal to [3,4,5,1,2], the integer result is equal to 3.*);
//@ ensures(*If the integer array parameter `gas` is equal to [2,3,4] and the integer array parameter `cost` is equal to [3,4,3], the integer result is equal to -1.*);
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