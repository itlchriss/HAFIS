// #Medium #Top_Interview_Questions #Math #Udemy_Integers
// #2024_01_04_Time_1_ms_(96.61%)_Space_40.9_MB_(11.62%)
// Dafny version of Solution

// requires(*The integer parameter `x` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*If the integer result is not equal to 0, the absolute value of the integer result is less than or equal to 2147483647.*);
// ensures(*If the integer result is equal to 0, the integer parameter `x` is equal to 0.*);
// ensures(*If the integer parameter `x` is positive, the integer result is positive.*);
// ensures(*If the integer parameter `x` is negative, the integer result is negative.*);
// ensures(*The integer result is the reverse of the integer parameter `x`.*);
    method reverse(x: int) returns (result: int)
    {
        var rev: int := 0;
        while x != 0
            invariant true
        {
            // assume (Long.MIN_VALUE)/10 + m <= k <= (Long.MAX_VALUE)/10 - m;
            // assume ((Long.MIN_VALUE) + ( m % 10 ))/10 <= k <= ((Long.MAX_VALUE) - ( m % 10 )) / 10;
            rev := (rev * 10) + (x % 10);
            x := x / 10;
        }
        if rev > 2147483647 || rev < -2147483648 {
            result := 0;
            return;
        }
        result := (int) rev;
        return;
    }
