package g0301_0400.s0387_first_unique_character_in_a_string;

// #Easy #Top_Interview_Questions #String #Hash_Table #Counting #Queue
// #Data_Structure_I_Day_6_String #2022_07_13_Time_1_ms_(100.00%)_Space_42.9_MB_(86.44%)

public class Solution {
    /*@
      @ // ----------  Preconditions  ----------------------------------------
      @ requires s != null;
      @ requires 1 <= s.length() && s.length() <= 100_000;
      @ // only lower-case English letters
      @ requires (\forall int i; 0 <= i && i < s.length();
      @                         'a' <= s.charAt(i) && s.charAt(i) <= 'z');
      @
      @ // ----------  Postconditions  ---------------------------------------
      @
      @ // /* 1.  If the result is –1 then every character occurring in the
      @ // // // // //        string appears at least twice.                            * /
      @ // ensures (\result == -1) ==>
      @ // // // // // // // // // //           (\forall int i; 0 <= i && i < s.length();
      @ // // // // // // // // //               (\exists int j; 0 <= j && j < s.length();
      @ // // // // // // //                              j != i && s.charAt(i) == s.charAt(j)));
      @
      @ // /* 2.  If the result is a valid index, it is the first (left-most)
      @ // // // // // //        character whose occurrence count is exactly one.          * /
      @ // // // ensures (0 <= \result && \result < s.length()) ==>
      @ // //         (
      @           // (a) the character at \result is unique
      @ // // // // // // //           (\forall int j; 0 <= j && j < s.length();
      @ // // // // // // // //                          j != \result ==> s.charAt(j) != s.charAt(\result))
      @ // //           &&
      @           // (b) all indices before \result are *not* unique
      @ // // // // // // // //           (\forall int k; 0 <= k && k < \result;
      @ // // // // // // // // // //               (\exists int m; 0 <= m && m < s.length();
      @ //                              m != k && s.charAt(k) == s.charAt(m)))
      @ // //         );
      @
      @ // ----------  Frame condition  --------------------------------------
      @ assignable \nothing;          // the method is pure (no side effects)
      @*/
    public int firstUniqChar(String s) {
        int ans = Integer.MAX_VALUE;
        for (char i = 'a'; i <= 'z'; i++) {
            int ind = s.indexOf(i);
            if (ind != -1) {
                ans = Math.min(ans, ind);
            }
        }
        if (ans == Integer.MAX_VALUE) {
            return -1;
        }
        return ans;
    }
}