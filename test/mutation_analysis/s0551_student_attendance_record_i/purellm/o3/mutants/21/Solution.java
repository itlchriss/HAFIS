package g0501_0600.s0551_student_attendance_record_i;

// #Easy #String #2022_08_02_Time_0_ms_(100.00%)_Space_40.2_MB_(96.36%)

public class Solution {
    /*@ public normal_behavior
      @
      @   // ------------- PRECONDITIONS ------------------------------------
      @   // 1. The argument exists and obeys the given length bounds.
      @   requires s != null;
      @   requires 1 <= s.length() && s.length() <= 1000;
      @
      @   // 2. Each character is one of ‘A’, ‘L’, or ‘P’.
      @   requires (\forall int i; 0 <= i && i < s.length();
      @                       s.charAt(i) == 'A' ||
      @                       s.charAt(i) == 'L' ||
      @                       s.charAt(i) == 'P');
      @
      @   // ------------- POSTCONDITION ------------------------------------
      @   //  result  ==  (fewer than two ‘A’)  AND  (no “LLL” substring)
      @   ensures \result <==>
      @           (
      @              // (a) strictly fewer than 2 ‘A’
      @              (\sum int i; 0 <= i && i < s.length();
      @                         (s.charAt(i) == 'A' ? 1 : 0)) < 2
      @           )
      @           &&
      @           (
      @              // (b) never three consecutive ‘L’
      @              (\forall int i; 0 <= i && i <= s.length() - 3;
      @                  !(s.charAt(i)     == 'L' &&
      @                    s.charAt(i + 1) == 'L' &&
      @                    s.charAt(i + 2) == 'L'))
      @           );
      @
      @   // ------------- FRAME CONDITION ----------------------------------
      @   assignable \nothing;          // The method is observationally pure.
      @*/
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
                while (false) {
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