// s0942 - DI String Match
// Dafny formal specification (spec-only: permutation construction)

method diStringMatch(s: string) returns (result: array<int>)
{
    // requires(*The length of the string parameter `s` is greater than or equal to 1 and is less than or equal to 100000.*);
    // requires(*The string parameter `s` is not equal to the null literal.*);
    // requires(*All values in the string parameter `s` are the character 'I' or the character 'D'.*);
    // ensures(*The integer array result is not equal to the null literal.*);
    // ensures(*The length of the integer array result is equal to the sum of the length of the string parameter `s` and 1.*);
    // ensures(*All values in the integer array result are greater than or equal to 0 and are less than or equal to the length of the string parameter `s`.*);
    // ensures(*All values in the integer array result are unique.*);
    // ensures(*If the string parameter `s` is equal to "IDID", the integer array result is equal to [0,4,1,3,2].*);
    // ensures(*If the string parameter `s` is equal to "III", the integer array result is equal to [0,1,2,3].*);
    // ensures(*If the string parameter `s` is equal to "DDI", the integer array result is equal to [3,2,0,1].*);
    result := new int[|s| + 1];
    assume false;  // spec-only
}
