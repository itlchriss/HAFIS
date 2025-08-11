package g0301_0400.s0319_bulb_switcher;

// #Medium #Math #Brainteaser #2022_07_08_Time_0_ms_(100.00%)_Space_41.1_MB_(27.19%)

public class Solution {
//@ ensures(*if int `n` equals 0 then result int equals 0*);
//@ ensures(*if int `n` equals 1 then result int equals 1*);
//@ ensures(*result int equals the number of bulbs that are on after completing `n` sequential toggle rounds as described in the specification*);
//@ ensures(*result int is greater than or equal to 0 and less than or equal to int `n`*);
    public int bulbSwitch(int n) {
        if (n < 2) {
            return n;
        }
        return (int) Math.sqrt(n);
    }
}