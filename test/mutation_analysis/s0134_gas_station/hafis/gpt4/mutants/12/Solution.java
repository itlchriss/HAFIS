package g0101_0200.s0134_gas_station;

// #Medium #Top_Interview_Questions #Array #Greedy
// #2022_06_24_Time_2_ms_(94.26%)_Space_62.5_MB_(87.11%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires((\forall int i; 0 <= i < cost.length; cost[i] <= 10000) && (\forall int i; 0 <= i < cost.length; cost[i] >= 0));
//@ requires((\forall int i; 0 <= i < gas.length; gas[i] <= 10000) && (\forall int i; 0 <= i < gas.length; gas[i] >= 0));
//@ requires((cost.length <= 100000) && (cost.length >= 1));
//@ requires(gas.length == cost.length);
//@ requires((gas.length <= 100000) && (gas.length >= 1));
//@ ensures(((gas[0] == 1 && gas[1] == 2 && gas[2] == 3 && gas[3] == 4 && gas[4] == 5 && gas.length == 5) && (cost[0] == 3 && cost[1] == 4 && cost[2] == 5 && cost[3] == 1 && cost[4] == 2 && cost.length == 5)) ==> (\result == 3));
//@ ensures(((gas[0] == 2 && gas[1] == 3 && gas[2] == 4 && gas.length == 3) && (cost[0] == 3 && cost[1] == 4 && cost[2] == 3 && cost.length == 3)) ==> (\result == -1));
//@ ensures((\result >= -1) && (\result < gas.length));
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
