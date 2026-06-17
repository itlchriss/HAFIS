// #Medium #String #2024_01_04_Time_2_ms_(99.60%)_Space_44.7_MB_(38.67%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 1000 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of English letters (lower-case and upper-case), ',' and '.'.*);
// requires(*The integer parameter `numRows` is greater than or equal to 1 and is less than or equal to 1000.*);
// ensures(*The string result is formed by reading the zigzag pattern line by line.*);
// ensures(*If the string parameter `s` is equal to "PAYPALISHIRING" and the integer parameter `numRows` is equal to 3, the string result is equal to "PAHNAPLSIIGYIR".*);
// ensures(*If the string parameter `s` is equal to "PAYPALISHIRING" and the integer parameter `numRows` is equal to 4, the string result is equal to "PINALSIGYAHRPI".*);
// ensures(*If the string parameter `s` is equal to "A" and the integer parameter `numRows` is equal to 1, the string result is equal to "A".*);
    method convert(s: string, numRows: int) returns (result: string)
    {
        var sLen: int := |s|();
        if numRows == 1 {
            result := s;
            return;
        }
        // assume Integer.MIN_VALUE + 3 <= k * 2 <= Integer.MAX_VALUE - 3;
        var maxDist: int := numRows * 2 - 2;
        var buf: StringBuilder := new StringBuilder();
        // invariant 0 <= i <= k;
        for i := 0 to numRows
            invariant i >= 0
            invariant i <= numRows
        {
            var index: int := i;
            if i == 0 || i == numRows - 1 {
                while index < sLen
                    invariant true
                {
                    // buf.append(s.charAt(index))
                    // assume Integer.MIN_VALUE + 1 <= index + maxDist <= Integer.MAX_VALUE - 1;
                    index := index + maxDist;
                }
            } else {
                while index < sLen
                    invariant true
                {
                    // buf.append(s.charAt(index))
                    // assume Integer.MIN_VALUE + 1 <= index + maxDist - i * 2 <= Integer.MAX_VALUE - 1;
                    index := index + maxDist - i * 2;
                    if index >= sLen {
                        break;
                    }
                    // buf.append(s.charAt(index))
                    // assume Integer.MIN_VALUE + 1 <= index + i * 2 <= Integer.MAX_VALUE - 1;
                    index := index + i * 2;
                }
            }
        }
        result := buf.toString();
        return;
    }
