// #Easy #Math #2022_07_24_Time_0_ms_(100.00%)_Space_39.4_MB_(98.67%)
// Dafny version of Solution

requires (num <= 10000000) && (num >= -10000000)
// ensures(*The string result is the base 7 representation of the integer parameter `num`.*);
// ensures(*If the integer parameter `num` is equal to 100, the string result is equal to "202".*);
// ensures(*If the integer parameter `num` is equal to -7, the string result is equal to "-10".*);
    method convertToBase7(num: int) returns (result: string)
    {
        result := "" + num, 7;
        return;
    }
