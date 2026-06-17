// #Easy #Bit_Manipulation #Udemy_Bit_Manipulation
// #2022_07_19_Time_0_ms_(100.00%)_Space_40.9_MB_(60.77%)
// Dafny version of Solution

// requires(*The integer parameter `x` is greater than or equal to 0 and is less than or equal to 2147483647.*);
// requires(*The integer parameter `y` is greater than or equal to 0 and is less than or equal to 2147483647.*);
// ensures(*The integer result is greater than or equal to 0 and is less than or equal to 32.*);
// ensures(*If the integer parameter `x` is equal to 1 and the integer parameter `y` is equal to 4, the integer result is equal to 2.*);
// ensures(*If the integer parameter `x` is equal to 3 and the integer parameter `y` is equal to 1, the integer result is equal to 1.*);
    method hammingDistance(x: int, y: int) returns (result: int)
    {
        result := Integer.bitCount(x ^ y);
        return;
    }
