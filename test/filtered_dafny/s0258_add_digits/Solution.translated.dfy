// #Easy #Math #Simulation #Number_Theory #2022_07_05_Time_1_ms_(100.00%)_Space_39.3_MB_(98.44%)
// Dafny version of Solution

requires (num >= 0) && (num <= 2147483647)
ensures \result >= 0 && \result <= 9
ensures (num == 38) ==> (\result == 2)
ensures (num == 0) ==> (\result == 0)
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
