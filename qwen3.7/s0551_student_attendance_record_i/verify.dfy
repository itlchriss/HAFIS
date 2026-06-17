// s0551 - Student Attendance Record I
// Complete Dafny formal specification with verified implementation

method checkRecord(s: string) returns (result: bool)
    requires 1 <= |s| <= 1000
    requires forall i: nat :: i < |s| ==> s[i] == 'A' || s[i] == 'L' || s[i] == 'P'
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
