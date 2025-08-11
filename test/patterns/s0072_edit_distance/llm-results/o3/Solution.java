package g0001_0100.s0072_edit_distance;

// #Hard #Top_100_Liked_Questions #String #Dynamic_Programming
// #Algorithm_II_Day_18_Dynamic_Programming #Dynamic_Programming_I_Day_19
// #Udemy_Dynamic_Programming #Big_O_Time_O(n^2)_Space_O(n2)
// #2023_08_11_Time_4_ms_(90.13%)_Space_41.8_MB_(99.78%)

@SuppressWarnings("java:S2234")
public class Solution {
    /*@ public model pure boolean isLowerCaseString(String s) {
      @   return (\forall int i; 0 <= i && i < s.length();
      @                    Character.isLowerCase(s.charAt(i)));
      @ }
      @*/

    /*@ public model pure boolean canTransform(String s1, String s2, int k) {
      @     /*  A recursive mathematical definition of
      @         “there exists a sequence of at most k elementary
      @         insert / delete / replace operations that converts
      @         s1 into s2”.                                 * /
      @
      @     if (k < 0)                return false;
      @     if (s1.equals(s2))        return k == 0;
      @     if (k == 0)               return false;
      @
      @     /*  delete one character                                        * /
      @     boolean del = (\exists int i; 0 <= i && i < s1.length();
      @                      canTransform(
      @                          s1.substring(0,i) + s1.substring(i+1),
      @                          s2, k-1));
      @
      @     /*  insert one lower-case character                             * /
      @     boolean ins = (\exists int i; 0 <= i && i <= s1.length();
      @                        \exists char c; 'a' <= c && c <= 'z';
      @                      canTransform(
      @                          s1.substring(0,i) + c + s1.substring(i),
      @                          s2, k-1));
      @
      @     /*  replace one character                                       * /
      @     boolean rep = (\exists int i; 0 <= i && i < s1.length();
      @                        \exists char c; 'a' <= c && c <= 'z'
      @                                        && c != s1.charAt(i);
      @                      canTransform(
      @                          s1.substring(0,i) + c + s1.substring(i+1),
      @                          s2, k-1));
      @
      @     return del || ins || rep;
      @ }
      @*/


    /*------------------------------------------------------------------
     *  The required method
     *----------------------------------------------------------------*/

    /*@ requires word1 != null && word2 != null;
      @ requires word1.length() <= 500 && word2.length() <= 500;
      @ requires isLowerCaseString(word1) && isLowerCaseString(word2);
      @
      @ ensures  canTransform(word1, word2, \result)
      @      && (\forall int k; 0 <= k && k < \result;
      @                 !canTransform(word1, word2, k));
      @          //  i.e.  \result is the minimum number
      @          //        of allowed operations to convert word1 to word2
      @
      @ assignable \nothing;          //  pure function: no side effects
      @*/
    public int minDistance(String w1, String w2) {
        int n1 = w1.length();
        int n2 = w2.length();
        if (n2 > n1) {
            return minDistance(w2, w1);
        }
        int[] dp = new int[n2 + 1];
        //@ maintaining 0 <= j <= n2 + 1;
        for (int j = 0; j <= n2; j++) {
            dp[j] = j;
        }
        //@ maintaining 1 <= i <= n1 + 1;
        for (int i = 1; i <= n1; i++) {
            int pre = dp[0];
            dp[0] = i;
            //@ maintaining 1 <= j <= n2 + 1;
            for (int j = 1; j <= n2; j++) {
                int tmp = dp[j];
                dp[j] =
                        w1.charAt(i - 1) != w2.charAt(j - 1)
                                ? 1 + Math.min(pre, Math.min(dp[j], dp[j - 1]))
                                : pre;
                pre = tmp;
            }
        }
        return dp[n2];
    }
}