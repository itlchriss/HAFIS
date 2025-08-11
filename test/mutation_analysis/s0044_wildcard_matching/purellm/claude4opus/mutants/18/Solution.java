package g0001_0100.s0044_wildcard_matching;

// #Hard #Top_Interview_Questions #String #Dynamic_Programming #Greedy #Recursion
// #Udemy_Dynamic_Programming #2023_08_11_Time_2_ms_(99.87%)_Space_43.2_MB_(99.49%)

public class Solution {
    /*@ public model pure boolean matches(String s, String p) {
      @   return matchesFrom(s, 0, p, 0);
      @ }
      @
      @ public model pure boolean matchesFrom(String s, int si, String p, int pi) {
      @   if (pi == p.length()) {
      @     return si == s.length();
      @   }
      @
      @   if (p.charAt(pi) == '*') {
      @     // '*' can match empty sequence
      @     if (matchesFrom(s, si, p, pi + 1)) return true;
      @     // '*' can match one or more characters
      @     for (int i = si; i < s.length(); i++) {
      @       if (matchesFrom(s, i + 1, p, pi + 1)) return true;
      @     }
      @     return false;
      @   }
      @
      @   if (si == s.length()) {
      @     return false;
      @   }
      @
      @   if (p.charAt(pi) == '?' || p.charAt(pi) == s.charAt(si)) {
      @     return matchesFrom(s, si + 1, p, pi + 1);
      @   }
      @
      @   return false;
      @ }
      @*/

    /*@ public normal_behavior
      @ // // requires s != null && p != null;
      @ // // requires s.length() >= 0 && s.length() <= 2000;
      @ // // requires p.length() >= 0 && p.length() <= 2000;
      @ // requires (\forall int i; 0 <= i && i < s.length();
      @ // //           'a' <= s.charAt(i) && s.charAt(i) <= 'z');
      @ // requires (\forall int i; 0 <= i && i < p.length();
      @ // //           ('a' <= p.charAt(i) && p.charAt(i) <= 'z') ||
      @ // //           p.charAt(i) == '?' || p.charAt(i) == '*');
      @ // // ensures \result == matches(s, p);
      @ pure
      @*/
    public boolean isMatch(String inputString, String pattern) {
        int i = 0;
        int j = 0;
        int starIdx = -1;
        int lastMatch = -1;
        while (i < inputString.length()) {
            if (j < pattern.length()
                    && (inputString.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '?')) {
                i++;
                j++;
            } else if (j < pattern.length() && pattern.charAt(j) == '*') {
                starIdx = j;
                lastMatch = i;
                j++;
            } else if (starIdx != -1) {
                // there is a no match and there was a previous star, we will reset the j to indx
                // after star_index
                // lastMatch will tell from which index we start comparing the string if we
                // encounter * in pattern
                j = starIdx + 1;
                // we are saying we included more characters in * so we incremented the
                lastMatch++;
                // index
                i = lastMatch;
            } else {
                return false;
            }
        }
        boolean isMatch = true;
        while (j < pattern.length() && pattern.charAt(j) == '*') {
            j++;
        }
        if (i != inputString.length() || j != pattern.length()) {
            isMatch = false;
        }
        return isMatch;
    }
}