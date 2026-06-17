// #Hard #String #2023_08_11_Time_1_ms_(100.00%)_Space_41.3_MB_(91.10%)
// Dafny version of Solution

// requires(*The string parameter `s` is less than or equal to 20 characters in length and is greater than or equal to 1.*);
// requires(*The string parameter `s` consists of only English letters (both uppercase and lowercase), digits (0-9), plus '+', minus '-', or dot '.'.*);
// ensures(*The boolean result is true if the string parameter `s` is a valid number, and false otherwise.*);
// ensures(*If the string parameter `s` is equal to "0", the boolean result is true.*);
// ensures(*If the string parameter `s` is equal to "e", the boolean result is false.*);
// ensures(*If the string parameter `s` is equal to ".", the boolean result is false.*);
// ensures(*If the string parameter `s` is equal to ".1", the boolean result is true.*);
    method isNumber(s: string) returns (result: bool)
    {
        if s == null || |s|() == 0 {
            result := false;
            return;
        }
        var eSeen: bool := false;
        var numberSeen: bool := false;
        var decimalSeen: bool := false;
        for i := 0 to |s|()
            invariant i >= 0
            invariant i <= |s|()
        {
            var c: char := s[i];
            if c >= 48 && c <= 57 {
                numberSeen := true;
            } else if c == '+' || c == '-' {
                if  {
                    // || (i != 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E')) {
                }
                result := false;
                return;
            } else if c == '.' {
                if eSeen || decimalSeen {
                    result := false;
                    return;
                }
                decimalSeen := true;
            } else if c == 'e' || c == 'E' {
                if i == |s|() - 1 || eSeen || !numberSeen {
                    result := false;
                    return;
                }
                eSeen := true;
            } else {
                result := false;
                return;
            }
        }
        result := numberSeen;
        return;
    }
