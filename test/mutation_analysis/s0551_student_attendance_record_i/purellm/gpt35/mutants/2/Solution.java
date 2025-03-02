package g0501_0600.s0551_student_attendance_record_i;

// #Easy #String #2022_08_02_Time_0_ms_(100.00%)_Space_40.2_MB_(96.36%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
// requires s != null && s.length() >= 1 && s.length() <= 1000
// requires (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == 'A' || s.charAt(i) == 'L' || s.charAt(i) == 'P')
// ensures \result == true || \result == false
// ensures \result == true ==> (countAbsences(s) < 2 && !hasConsecutiveLate(s))
// ensures \result == false ==> (countAbsences(s) >= 2 || hasConsecutiveLate(s))
// requires s != null && s.length() >= 1 && s.length() <= 1000
// requires (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == 'A' || s.charAt(i) == 'L' || s.charAt(i) == 'P')
// ensures \result >= 0
// ensures \result == countAbsencesHelper(s, 0, 0)
// requires s != null && s.length() >= 1 && s.length() <= 1000
// requires (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == 'A' || s.charAt(i) == 'L' || s.charAt(i) == 'P')
// requires 0 <= index && index <= s.length()
// ensures \result >= 0
// ensures \result == count + countAbsencesHelper(s, index + 1, count) if index < s.length()
// ensures \result == count if index == s.length()
// requires s != null && s.length() >= 1 && s.length() <= 1000
// requires (\forall int i; 0 <= i && i < s.length(); s.charAt(i) == 'A' || s.charAt(i) == 'L' || s.charAt(i) == 'P')
//@ ensures \result == true || \result == false
// ensures \result == true ==> (\exists int i; 0 <= i && i < s.length() - 2; s.charAt(i) == 'L' && s.charAt(i + 1) == 'L' && s.charAt(i + 2) == 'L')
// ensures \result == false ==> (\forall int i; 0 <= i && i < s.length() - 2; s.charAt(i) != 'L' || s.charAt(i + 1) != 'L' || s.charAt(i + 2) != 'L')
    public boolean checkRecord(String s) {
        int aCount = 0;
        int i = 0;
        while (false) {
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
