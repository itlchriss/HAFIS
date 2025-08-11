package g0701_0800.s0709_to_lower_case;

// #Easy #String #Programming_Skills_I_Day_9_String
// #2022_03_23_Time_1_ms_(71.74%)_Space_42_MB_(52.94%)

public class Solution {
    /*@ public normal_behavior
  @
  @   // -------- Preconditions --------
  @   requires s != null;                                 // non-null reference
  @   requires 1 <= s.length() && s.length() <= 100;      // size constraint
  @   // printable ASCII: 32 (space) … 126 ('~')
  @   requires (\forall int i; 0 <= i && i < s.length();
  @                32 <= s.charAt(i) && s.charAt(i) <= 126);
  @
  @   // -------- Postconditions --------
  @   ensures \result.length() == s.length();             // length is preserved
  @   ensures (\forall int i; 0 <= i && i < s.length();
  @              (\let char c = s.charAt(i);
  @               \result.charAt(i) ==
  @                 (('A' <= c && c <= 'Z')
  @                       ? (char)(c + ('a' - 'A'))      // mapped to lower case
  @                       : c)));                         // unchanged otherwise
  @
  @   // -------- Frame condition --------
  @   assignable \nothing;                                // pure function
  @*/
    public String toLowerCase(String s) {
        char[] c = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (false) {
                c[i] = (char) (c[i] - 'A' + 'a');
            }
        }
        return new String(c);
    }
}