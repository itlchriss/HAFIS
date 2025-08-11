package g0001_0100.s0028_find_the_index_of_the_first_occurrence_in_a_string;

// #Easy #Top_Interview_Questions #String #Two_Pointers #String_Matching
// #Programming_Skills_II_Day_1 #2023_08_09_Time_0_ms_(100.00%)_Space_40.5_MB_(71.14%)

public class Solution {
    /*@ public normal_behavior
  @   // -----  PRE-CONDITIONS  -----
  @   requires haystack != null && needle != null;
  @   // length constraints given in the problem statement
  @   requires haystack.length() <= 50000 && needle.length() <= 50000;
  @   // both strings consist solely of lower-case English letters
  @   requires (\forall int k;
  @              0 <= k && k < haystack.length();
  @              'a' <= haystack.charAt(k) && haystack.charAt(k) <= 'z');
  @   requires (\forall int k;
  @              0 <= k && k < needle.length();
  @              'a' <= needle.charAt(k) && needle.charAt(k) <= 'z');
  @
  @   // -----  POST-CONDITIONS  -----
  @
  @   // 1.  The “empty needle” rule
  @   ensures needle.length() == 0 ==> \result == 0;
  @
  @   // 2.  \result == -1  ⇔  needle is NOT a substring of haystack
  @   ensures (\result == -1) <==>
  @           !(\exists int i;
  @               0 <= i && i + needle.length() <= haystack.length();
  @               haystack.substring(i, i + needle.length())
  @                       .equals(needle));
  @
  @   // 3.  If \result ≠ -1 then it must be the FIRST index
  @   //     where needle occurs in haystack
  @   ensures (\result != -1) ==> (
  @              0 <= \result &&
  @              \result + needle.length() <= haystack.length() &&
  @
  @              // The substring starting at \result equals needle
  @              haystack.substring(\result, \result + needle.length())
  @                      .equals(needle) &&
  @
  @              // No smaller index gives such a match
  @              (\forall int j;
  @                 0 <= j && j < \result;
  @                 !haystack.substring(j, j + needle.length())
  @                          .equals(needle))
  @          );
  @*/
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        int m = haystack.length();
        int n = needle.length();
        //@ decreases (m - n + 1) - start;
        for (int start = 0; start < m - n + 1; start++) {
            if (haystack.substring(start, start + n).equals(needle)) {

            }
        }
        return -1;
    }
}