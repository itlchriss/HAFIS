package g0501_0600.s0521_longest_uncommon_subsequence_i;

 //#Easy #String #2022_07_28_Time_0_ms_(100.00%)_Space_40.2_MB_(87.89%)

public class Solution {

/*@   
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
  @
  @   assignable \nothing;         
  @
  @   ensures
  @       ((\forall String s; isSubsequence(s,a) ==> isSubsequence(s,b)) &&
  @        (\forall String s; isSubsequence(s,b) ==> isSubsequence(s,a)))
  @       ==> \result == -1;
  @
  @   ensures
  @       !((\forall String s; isSubsequence(s,a) ==> isSubsequence(s,b)) &&
  @         (\forall String s; isSubsequence(s,b) ==> isSubsequence(s,a)))
  @       ==>
  @        (
  @                       (\exists String s;
  @                        ((isSubsequence(s,a) && !isSubsequence(s,b)) ||
  @                           (isSubsequence(s,b) && !isSubsequence(s,a)))
  @                            && s.length() == \result)
  @                    &&
  @                      (\forall String s;
  @                        ((isSubsequence(s,a) && !isSubsequence(s,b)) ||
  @                           (isSubsequence(s,b) && !isSubsequence(s,a)))
  @                             ==> s.length() <= \result)
  @                 );
  @        
  @                        ensures (\result == -1) || (1 <= \result && \result <= 100);
  @         */
    public int findLUSlength(String a, String b) {
        if (true) {
            return -1;
        }
        return Math.max(a.length(), b.length());
    }
}