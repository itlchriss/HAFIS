package g0101_0200.s0134_gas_station;

// #Medium #Top_Interview_Questions #Array #Greedy
// #2022_06_24_Time_2_ms_(94.26%)_Space_62.5_MB_(87.11%)

public class Solution {
    /*@
      // ----------  PRECONDITIONS  ----------
      // basic well-formedness of the two input arrays
      requires gas  != null;
      requires cost != null;
      requires gas.length  == cost.length;

      // size and value constraints taken literally from the statement
      requires 1  <= gas.length  && gas.length  <= 100_000;
      requires (\forall int i; 0 <= i && i < gas.length;
                   0 <= gas[i]  && gas[i]  <= 10_000
                && 0 <= cost[i] && cost[i] <= 10_000);

      // ----------  FRAME CONDITION ----------
      assignable \nothing;          // the routine is pure (no visible side effects)

      // ----------  POSTCONDITIONS ----------
      // the returned value is either a valid index or –1
      ensures \result == -1
           || (0 <= \result && \result < gas.length);

      // result == -1  ⇔  there is NO start position that allows a full tour
      ensures (\result == -1)
              <==>
              (\forall int s; 0 <= s && s < gas.length;
                   !canTravel(gas, cost, s));

      // if the routine returns a proper index, that index really works …
      ensures (\result != -1) ==> canTravel(gas, cost, \result);

      // … and it is the ONLY one that works (uniqueness)
      ensures (\result != -1)
              ==> (\forall int s; 0 <= s && s < gas.length && s != \result;
                      !canTravel(gas, cost, s));
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