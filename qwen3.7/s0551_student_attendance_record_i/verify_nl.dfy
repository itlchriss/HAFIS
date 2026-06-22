// s0551 - Student Attendance Record I
// Complete Dafny formal specification with verified implementation

method checkRecord(s: string) returns (result: bool)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 1000.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // requires(*All values in the string parameter `s` are the character 'A', the character 'L', or the character 'P'.*);
    // ensures(*The boolean result is equal to the true literal if and only if the count of the character 'A' in the string parameter `s` is less than 2 and the string parameter `s` does not contain 3 consecutive the character 'L'.*);
    // ensures(*If the string parameter `s` is equal to "PPALLP", the boolean result is equal to the true literal.*);
    // ensures(*If the string parameter `s` is equal to "PPALLL", the boolean result is equal to the false literal.*);
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
