// s0165 - Compare Version Numbers
// Dafny formal specification (spec-only)
method compareVersion(version1: string, version2: string) returns (result: int)
    requires 1 <= |version1| <= 500
    requires 1 <= |version2| <= 500
    ensures -1 <= result <= 1
    ensures result == 0 || result == -1 || result == 1
{
    result := 0;
    assume false;
}
