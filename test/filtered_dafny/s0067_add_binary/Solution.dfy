// #Easy #String #Math #Bit_Manipulation #Simulation #Programming_Skills_II_Day_5
// #2023_08_11_Time_1_ms_(100.00%)_Space_41.6_MB_(36.86%)
// Dafny version of Solution

// requires(*The length of the string parameter `a` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The length of the string parameter `b` is less than or equal to 10000 and is greater than or equal to 1.*);
// requires(*The string parameter `a` consists only of '0' or '1' characters.*);
// requires(*The string parameter `b` consists only of '0' or '1' characters.*);
// ensures(*The string result is a binary string.*);
// ensures(*If the string parameter `a` is equal to "11" and the string parameter `b` is equal to "1", the string result is equal to "100".*);
// ensures(*If the string parameter `a` is equal to "1010" and the string parameter `b` is equal to "1011", the string result is equal to "10101".*);
    method addBinary(a: string, b: string) returns (result: string)
    {
        var aArray: array<char> := a.toCharArray();
        var bArray: array<char> := b.toCharArray();
        var sb: StringBuilder := new StringBuilder();
        var i: int := |aArray| - 1;
        var j: int := |bArray| - 1;
        var carry: int := 0;
        // invariant //@ maintaining -1 <= i <= aArray.length - 1 && bArray.length - 1 >= j >= -1;
        while i >= 0 || j >= 0
            invariant true
        {
            var sum: int := if (i >= 0 then aArray[i] - '0' else if 0) + (j >= 0 then bArray[j] - '0' else 0) + carry;
            // sb.append(sum % 2)
            carry := sum / 2;
            if i >= 0 {
                i := i - 1;
            }
            if j >= 0 {
                j := j - 1;
            }
        }
        if carry != 0 {
            // sb.append(carry)
        }
        result := sb.reverse().toString();
        return;
    }
