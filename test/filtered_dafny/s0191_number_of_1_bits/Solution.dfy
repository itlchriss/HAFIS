// #Easy #Top_Interview_Questions #Bit_Manipulation #Algorithm_I_Day_13_Bit_Manipulation
// #Programming_Skills_I_Day_2_Operator #Udemy_Bit_Manipulation
// #2022_06_28_Time_1_ms_(84.87%)_Space_41.8_MB_(10.40%)
// Dafny version of Solution

// requires(*The integer parameter `n` is a binary string of length 32.*);
// ensures(*The integer result is equal to the number of '1' bits in the binary representation of the integer parameter `n`.*);
// ensures(*If the integer parameter `n` is equal to 00000000000000000000000000001011, the integer result is equal to 3.*);
// ensures(*If the integer parameter `n` is equal to 00000000000000000000000010000000, the integer result is equal to 1.*);
// ensures(*If the integer parameter `n` is equal to 11111111111111111111111111111101, the integer result is equal to 31.*);
    method hammingWeight(n: int) returns (result: int)
    {
        var sum: int := 0;
        var flag: bool := false;
        if n < 0 {
            flag := true;
            n := n - -2147483648;
        }
        while n > 0
            invariant true
        {
            var k: int := n % 2;
            sum := sum + k;
            n := n / 2;
        }
        result := if flag then sum + 1 else sum;
        return;
    }
