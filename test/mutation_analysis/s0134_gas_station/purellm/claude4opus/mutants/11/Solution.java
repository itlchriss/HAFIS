package g0101_0200.s0134_gas_station;

// #Medium #Top_Interview_Questions #Array #Greedy
// #2022_06_24_Time_2_ms_(94.26%)_Space_62.5_MB_(87.11%)

public class Solution {
    /*@ public normal_behavior
      @ requires gas != null && cost != null;
      @ requires gas.length == cost.length;
      @ requires gas.length >= 1 && gas.length <= 100000;
      @ requires (\forall int i; 0 <= i && i < gas.length;
      @          0 <= gas[i] && gas[i] <= 10000);
      @ requires (\forall int i; 0 <= i && i < cost.length;
      @          0 <= cost[i] && cost[i] <= 10000);
      @ ensures \result >= -1 && \result < gas.length;
      @ assignable \nothing;
      @*/
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