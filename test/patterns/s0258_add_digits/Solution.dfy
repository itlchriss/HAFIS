// #Easy #Math #Simulation #Number_Theory #2022_07_05_Time_1_ms_(100.00%)_Space_39.3_MB_(98.44%)
// Dafny version of Solution

// requires(*The integer parameter `num` is greater than or equal to 0 and is less than or equal to 2^31 - 1.*);
// ensures(*The integer result is a single digit.*);
// ensures(*If the integer parameter `num` is equal to 38, the integer result is equal to 2.*);
// ensures(*If the integer parameter `num` is equal to 0, the integer result is equal to 0.*);
    method addDigits(num: int) returns (result: int)
    {
        if num == 0 {
            result := 0;
            return;
        }
        if num % 9 == 0 {
            result := 9;
            return;
        }
        result := num % 9;
        return;
    }
