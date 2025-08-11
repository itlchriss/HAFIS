package g0501_0600.s0551_student_attendance_record_i;

// #Easy #String #2022_08_02_Time_0_ms_(100.00%)_Space_40.2_MB_(96.36%)

public class Solution {
//@ requires(*All characters in the string parameter `s` are either the character literal 'A' or the character literal 'L' or the character literal 'P'.*);
//@ ensures(*If boolean result is equal to the true literal, the count of the character literal 'A' in the string parameter `s` is less than 2 and the string parameter `s` does not contain the substring literal "LLL".*);
//@ ensures(*If boolean result is equal to the false literal, the count of the character literal 'A' in the string parameter `s` is greater than or equal to 2 or the string parameter `s` contains the substring literal "LLL".*);
//@ ensures(*If the string parameter `s` is equal to "PPALLP", the boolean result is equal to the true literal.*);
//@ ensures(*If the string parameter `s` is equal to "PPALLL", the boolean result is equal to the false literal.*);
    public boolean checkRecord(String s) {
        int aCount = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == 'A') {
                aCount++;
                if (aCount > 1) {
                    return false;
                }
            } else if (s.charAt(i) == 'L') {
                int continuousLCount = 0;
                while (i < s.length() && s.charAt(i) == 'L') {
                    i++;
                    continuousLCount++;
                    if (continuousLCount > 2) {
                        return false;
                    }
                }
                i--;
            }
            i++;
        }
        return true;
    }
}