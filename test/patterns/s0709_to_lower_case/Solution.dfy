// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)
// Dafny version of Solution

// requires(*The length of the string parameter `s` is less than or equal to 100 and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of printable ASCII characters.*);
// ensures(*If the string parameter `s` is equal to "Hello", the string result is equal to "hello".*);
// ensures(*If the string parameter `s` is equal to "here", the string result is equal to "here".*);
// ensures(*If the string parameter `s` is equal to "LOVELY", the string result is equal to "lovely".*);
    method toLowerCase(s: string) returns (result: string)
    {
        var c: array<char> := s.toCharArray();
        for i := 0 to |s|()
            invariant i >= 0
            invariant i <= |s|()
        {
            if c[i] <= 'Z' && c[i] >= 'A' {
                c[i] := (char) (c[i] - 'A' + 'a');
            }
        }
        result := new String(c);
        return;
    }
