// #Medium #Math #Brainteaser #2022_07_08_Time_0_ms_(100.00%)_Space_41.1_MB_(27.19%)
// Dafny version of Solution

// requires(*The integer parameter `n` is greater than or equal to 0 and is less than or equal to 10^9.*);
// ensures(*The integer result is equal to the number of bulbs that are on after `n` rounds.*);
// ensures(*If the integer parameter `n` is equal to 3, the integer result is equal to 1.*);
// ensures(*If the integer parameter `n` is equal to 0, the integer result is equal to 0.*);
// ensures(*If the integer parameter `n` is equal to 1, the integer result is equal to 1.*);
    method bulbSwitch(n: int) returns (result: int)
    {
        if n < 2 {
            result := n;
            return;
        }
        result := (int) Math.sqrt(n);
        return;
    }
