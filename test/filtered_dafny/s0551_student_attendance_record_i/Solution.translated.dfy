// #Easy #String #2022_08_02_Time_0_ms_(100.00%)_Space_40.2_MB_(96.36%)
// Dafny version of Solution

requires (s.length <= 1000) && (s.length >= 1)
requires (s.length <= 1000) && (s.length >= 1)
requires (s.size() <= 1000) && (s.size() >= 1)
requires (s.length() <= 1000) && (s.length() >= 1)
requires (s.size() <= 1000) && (s.size() >= 1)
// requires(*The string parameter `s` only contains the characters 'A', 'L', or 'P'.*);
// ensures(*If the boolean result is true, the total number of 'A' characters in the string parameter `s` is less than 2 and there are no sequences of 3 or more consecutive 'L' characters.*);
// ensures(*If the boolean result is false, the total number of 'A' characters in the string parameter `s` is greater than or equal to 2 or there is at least one sequence of 3 or more consecutive 'L' characters.*);
    method checkRecord(s: string) returns (result: bool)
    {
        var aCount: int := 0;
        var i: int := 0;
        while i < |s|()
            invariant true
        {
            if s[i] == 'A' {
                aCount := aCount + 1;
                if aCount > 1 {
                    result := false;
                    return;
                }
            } else if s[i] == 'L' {
                var continuousLCount: int := 0;
                while i < |s|() && s[i] == 'L'
                    invariant true
                {
                    i := i + 1;
                    continuousLCount := continuousLCount + 1;
                    if continuousLCount > 2 {
                        result := false;
                        return;
                    }
                }
                i := i - 1;
            }
            i := i + 1;
        }
        result := true;
        return;
    }
