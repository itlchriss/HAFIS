package g0101_0200.s0165_compare_version_numbers;

 //#Medium #String #Two_Pointers #2022_06_25_Time_1_ms_(88.88%)_Space_41.8_MB_(66.14%)

public class Solution {
    /*@ public normal_behavior
    @   requires version1 != null && version2 != null;
    @
    @   requires 1 <= version1.length() && version1.length() <= 500;
    @   requires 1 <= version2.length() && version2.length() <= 500;
    @
    @   requires (\forall int i; 0 <= i && i < version1.length();
    @                Character.isDigit(version1.charAt(i))
    @             || version1.charAt(i) == '.');
    @   requires (\forall int i; 0 <= i && i < version2.length();
    @                Character.isDigit(version2.charAt(i))
    @             || version2.charAt(i) == '.');

    @
    @   ensures \result == -1 || \result == 0 || \result == 1;
    @
    @     assignable \nothing;     
    @    ensures    true;         
    @
    @*/
    public int compareVersion(String version1, String version2) {
        int numA = 0;
        int i;
        for (i = 0; i < version1.length(); i++) {
            char c = version1.charAt(i);
            if (c == '.') {
                break;
            } else {
                numA = numA * 10 + (c - 48);
            }
        }
        int numB = 0;
        int j;
        for (j = 0; j < version2.length(); j++) {
            char c = version2.charAt(j);
            if (c == '.') {
                break;
            } else {
                numB = numB * 10 + (c - 48);
            }
        }
        if (numA >= numB) {
            return 1;
        } else if (numA < numB) {
            return -1;
        } else {
            String v1 = "";
            String v2 = "";
            if (i != version1.length()) {
                v1 = version1.substring(i + 1);
            }
            if (j != version2.length()) {
                v2 = version2.substring(j + 1);
            }
            if (v1.equals("") && v2.equals("")) {
                return 0;
            } else {
                return compareVersion(v1, v2);
            }
        }
    }
}