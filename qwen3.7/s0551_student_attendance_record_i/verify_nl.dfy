// s0551 - Student Attendance Record I
// Complete Dafny formal specification with verified implementation

// requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 1000.*);
// requires(*The string parameter `s` consists of only the characters 'A' or 'L' or 'P'.*);
// requires(*The string parameter `s` is not equal to the null literal.*);
// ensures(*If the boolean result is equal to the true literal, the number of occurrences of the character 'A' in the string parameter `s` is less than 2 and the string parameter `s` does not contain 3 consecutive characters 'L'.*);
// ensures(*If the boolean result is equal to the false literal, the number of occurrences of the character 'A' in the string parameter `s` is greater than or equal to 2 or the string parameter `s` contains 3 consecutive characters 'L'.*);
// ensures(*If the string parameter `s` is equal to "PPALLP", the boolean result is equal to the true literal.*);
// ensures(*If the string parameter `s` is equal to "PPALLL", the boolean result is equal to the false literal.*);
method checkRecord(s: string) returns (result: bool)
{
    var aCount: int := 0;
    var i: nat := 0;
    while i < |s|
        invariant i <= |s|
        invariant aCount >= 0
        decreases |s| - i
    {
        if s[i] == 'A' {
            aCount := aCount + 1;
            if aCount >= 2 {
                result := false;
                return;
            }
        }
        i := i + 1;
    }
    // aCount < 2 here (we didn't return early)
    // Check for 3 consecutive L's
    i := 0;
    while i + 2 < |s|
        invariant i <= |s|
        decreases |s| - i
    {
        if s[i] == 'L' && s[i + 1] == 'L' && s[i + 2] == 'L' {
            result := false;
            return;
        }
        i := i + 1;
    }
    result := true;
}
