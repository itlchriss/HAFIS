// s0165 - Compare Version Numbers
// Dafny formal specification (spec-only)
// requires(*The length of the string parameter `version1` is greater than or equal to 1 and is less than or equal to 500.*);
// requires(*The length of the string parameter `version2` is greater than or equal to 1 and is less than or equal to 500.*);
// requires(*The string parameter `version1` consists of only digits and the character '.'.*);
// requires(*The string parameter `version2` consists of only digits and the character '.'.*);
// requires(*The string parameter `version1` is not equal to the null literal.*);
// requires(*The string parameter `version2` is not equal to the null literal.*);
// ensures(*The integer result is equal to -1 or 0 or 1.*);
// ensures(*If the integer result is equal to -1, the version number represented by the string parameter `version1` is less than the version number represented by the string parameter `version2`.*);
// ensures(*If the integer result is equal to 1, the version number represented by the string parameter `version1` is greater than the version number represented by the string parameter `version2`.*);
// ensures(*If the integer result is equal to 0, the version number represented by the string parameter `version1` is equal to the version number represented by the string parameter `version2`.*);
// ensures(*If the string parameter `version1` is equal to "1.01" and the string parameter `version2` is equal to "1.001", the integer result is equal to 0.*);
// ensures(*If the string parameter `version1` is equal to "1.0" and the string parameter `version2` is equal to "1.0.0", the integer result is equal to 0.*);
// ensures(*If the string parameter `version1` is equal to "0.1" and the string parameter `version2` is equal to "1.1", the integer result is equal to -1.*);
// ensures(*If the string parameter `version1` is equal to "1.0.1" and the string parameter `version2` is equal to "1", the integer result is equal to 1.*);
// ensures(*If the string parameter `version1` is equal to "7.5.2.4" and the string parameter `version2` is equal to "7.5.3", the integer result is equal to -1.*);
method compareVersion(version1: string, version2: string) returns (result: int)
{
    result := 0;
    assume false;
}
