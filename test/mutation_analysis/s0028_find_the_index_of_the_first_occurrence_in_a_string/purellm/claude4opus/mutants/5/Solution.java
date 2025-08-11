package g0001_0100.s0028_find_the_index_of_the_first_occurrence_in_a_string;

// #Easy #Top_Interview_Questions #String #Two_Pointers #String_Matching
// #Programming_Skills_II_Day_1 #2023_08_09_Time_0_ms_(100.00%)_Space_40.5_MB_(71.14%)

public class Solution {
    /*@ public normal_behavior
      @   requires haystack != null && needle != null;
      @   requires 0 <= haystack.length() && haystack.length() <= 50000;
      @   requires 0 <= needle.length() && needle.length() <= 50000;
      @   requires (\forall int i; 0 <= i && i < haystack.length();
      @             'a' <= haystack.charAt(i) && haystack.charAt(i) <= 'z');
      @   requires (\forall int i; 0 <= i && i < needle.length();
      @             'a' <= needle.charAt(i) && needle.charAt(i) <= 'z');
      @   ensures needle.length() == 0 ==> \result == 0;
      @   ensures needle.length() > 0 &&
      @           (\exists int i; 0 <= i && i <= haystack.length() - needle.length();
      @             (\forall int j; 0 <= j && j < needle.length();
      @               haystack.charAt(i + j) == needle.charAt(j))) ==>
      @           (\result >= 0 && \result <= haystack.length() - needle.length() &&
      @            (\forall int j; 0 <= j && j < needle.length();
      @              haystack.charAt(\result + j) == needle.charAt(j)) &&
      @            (\forall int k; 0 <= k && k < \result;
      @              (\exists int j; 0 <= j && j < needle.length();
      @                haystack.charAt(k + j) != needle.charAt(j) ||
      @                k + j >= haystack.length())));
      @   ensures needle.length() > 0 &&
      @           (\forall int i; 0 <= i && i <= haystack.length() - needle.length();
      @             (\exists int j; 0 <= j && j < needle.length();
      @               haystack.charAt(i + j) != needle.charAt(j))) ==>
      @           \result == -1;
      @   ensures needle.length() > haystack.length() ==> \result == -1;
      @ also
      @ public exceptional_behavior
      @   requires haystack == null || needle == null;
      @   signals_only NullPointerException;
      @*/
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        int m = haystack.length();
        int n = needle.length();
        //@ decreases (m - n + 1) - start;
        for (int start = 0; start < m + n + 1; start++) {
            if (haystack.substring(start, start + n).equals(needle)) {
                return start;
            }
        }
        return -1;
    }
}