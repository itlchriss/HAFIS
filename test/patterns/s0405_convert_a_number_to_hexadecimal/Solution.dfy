// #Easy #Math #Bit_Manipulation #2022_07_16_Time_1_ms_(71.02%)_Space_42.2_MB_(15.68%)
// Dafny version of Solution

// requires(*The integer parameter `num` is greater than or equal to -2147483648 and is less than or equal to 2147483647.*);
// ensures(*The string result represents the hexadecimal representation of the integer parameter `num`.*);
// ensures(*The string result consists of lowercase characters only.*);
// ensures(*The string result does not contain any leading zeros except for the zero itself.*);
// ensures(*If the integer parameter `num` is equal to 26, the string result is equal to "1a".*);
// ensures(*If the integer parameter `num` is equal to -1, the string result is equal to "ffffffff".*);
    method toHex(num: int) returns (result: string)
    {
        if num == 0 {
            result := "0";
            return;
        }
        var sb: StringBuilder := new StringBuilder();
        var x: int;
        while num != 0
            invariant true
        {
            x := num & 0xf;
            if x < 10 {
                // sb.append(x)
            } else {
                // sb.append((char) (x + 87))
            }
            num := num >>> 4;
        }
        result := sb.reverse().toString();
        return;
    }
