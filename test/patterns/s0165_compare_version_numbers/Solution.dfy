// #Medium #String #Two_Pointers #2022_06_25_Time_1_ms_(88.88%)_Space_41.8_MB_(66.14%)
// Dafny version of Solution

// requires(*The length of the string parameter `version1` is less than or equal to 500 and is greater than or equal to 1.*);
// requires(*The length of the string parameter `version2` is less than or equal to 500 and is greater than or equal to 1.*);
// requires(*The string parameters `version1` and `version2` only contain digits and '.'.*);
// requires(*The string parameters `version1` and `version2` are valid version numbers.*);
// requires(*All the given revisions in `version1` and `version2` can be stored in a 32-bit integer.*);
// ensures(*If the integer result is equal to -1, `version1` is less than `version2`.*);
// ensures(*If the integer result is equal to 1, `version1` is greater than `version2`.*);
// ensures(*If the integer result is equal to 0, `version1` is equal to `version2`.*);
// ensures(*If the string parameter `version1` is equal to "1.01" and the string parameter `version2` is equal to "1.001", the integer result is equal to 0.*);
// ensures(*If the string parameter `version1` is equal to "1.0" and the string parameter `version2` is equal to "1.0.0", the integer result is equal to 0.*);
// ensures(*If the string parameter `version1` is equal to "0.1" and the string parameter `version2` is equal to "1.1", the integer result is equal to -1.*);
// ensures(*If the string parameter `version1` is equal to "1.0.1" and the string parameter `version2` is equal to "1", the integer result is equal to 1.*);
// ensures(*If the string parameter `version1` is equal to "7.5.2.4" and the string parameter `version2` is equal to "7.5.3", the integer result is equal to -1.*);
    method compareVersion(version1: string, version2: string) returns (result: int)
    {
        // acquire first number
        var numA: int := 0;
        var i: int;
        for i := 0 to |version1|()
            invariant i >= 0
            invariant i <= |version1|()
        {
            var c: char := version1[i];
            if c == '.' {
                break;
            } else {
                numA := numA * 10 + (c - 48);
            }
        }
        // acquire second number
        var numB: int := 0;
        var j: int;
        for j := 0 to |version2|()
            invariant j >= 0
            invariant j <= |version2|()
        {
            var c: char := version2[j];
            if c == '.' {
                break;
            } else {
                numB := numB * 10 + (c - 48);
            }
        }
        // compare
        if numA > numB {
            result := 1;
            return;
        } else if numA < numB {
            result := -1;
            return;
        } else {
            // equal
            var v1: string := "";
            var v2: string := "";
            if i != |version1|() {
                v1 := version1.substring(i + 1);
            }
            if j != |version2|() {
                v2 := version2.substring(j + 1);
            }
            // if both versions end here, they are equal
            if v1 == "" && v2 == "" {
                result := 0;
                return;
            } else {
                result := compareVersion(v1, v2);
                return;
            }
        }
    }
