package g0501_0600.s0521_longest_uncommon_subsequence_i;

// #Easy #String #2022_07_28_Time_0_ms_(100.00%)_Space_40.2_MB_(87.89%)

public class Solution {
    /*
     * The gotcha point of this question is:
     * 1. if a and b are identical, then there will be no common subsequence, return -1
     * 2. else if a and b are of equal length, then any one of them will be a subsequence of the other string
     * 3. else if a and b are of different length, then the longer one is a required subsequence because
     * the longer string cannot be a subsequence of the shorter one
     * Or in other words, when a.length() != b.length(), no subsequence of b will be equal to a,
     * so return Math.max(a.length(), b.length())
     */
    /*@ public helper pure static boolean isLowerCaseLetter(char c) {
  @     return 'a' <= c && c <= 'z';
  @ } @*/

/*@  //  sub is a subsequence of str
  @ public model pure static boolean isSubsequence(String sub, String str) {
  @     if (sub == null || str == null)          return false;
  @     int j = 0;
  @     for (int i = 0; i < str.length() && j < sub.length(); i++) {
  @         if (sub.charAt(j) == str.charAt(i))  j++;
  @     }
  @     return j == sub.length();
  @ } @*/

/*@ public normal_behavior
  @   requires a != null && b != null;
  @   requires 1 <= a.length() && a.length() <= 100;
  @   requires 1 <= b.length() && b.length() <= 100;
  @   requires (\forall int i; 0 <= i < a.length(); isLowerCaseLetter(a.charAt(i)));
  @   requires (\forall int i; 0 <= i < b.length(); isLowerCaseLetter(b.charAt(i)));
  @
  @   assignable \nothing;         // the method has no observable side-effects
  @
  @   /* CASE 1  – no uncommon subsequence exists:                       * /
  @   ensures
  @       ((\forall String s; isSubsequence(s,a) ==> isSubsequence(s,b)) &&
  @        (\forall String s; isSubsequence(s,b) ==> isSubsequence(s,a)))
  @       ==> \result == -1;
  @
  @   /* CASE 2  – at least one uncommon subsequence exists:             * /
  @   ensures
  @       !((\forall String s; isSubsequence(s,a) ==> isSubsequence(s,b)) &&
  @         (\forall String s; isSubsequence(s,b) ==> isSubsequence(s,a)))
  @       ==>
  @       (
  @          /* some uncommon subsequence has exactly \result characters */
  @          (\exists String s;
  @                ((isSubsequence(s,a) && !isSubsequence(s,b)) ||
  @                 (isSubsequence(s,b) && !isSubsequence(s,a)))
  @                && s.length() == \result)
  @          &&
  @          /* none is longer                                            */
  @          (\forall String s;
  @                ((isSubsequence(s,a) && !isSubsequence(s,b)) ||
  @                 (isSubsequence(s,b) && !isSubsequence(s,a)))
  @                ==> s.length() <= \result)
  @       );
  @
  @   /* trivial bound on the result when it is not –1                  * /
  @   ensures (\result == -1) || (1 <= \result && \result <= 100);
  @*/
    public int findLUSlength(String a, String b) {
        if (a.equals(b)) {
            return -1;
        }
        return Math.max(a.length(), b.length());
    }
}