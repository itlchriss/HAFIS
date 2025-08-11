package g0001_0100.s0028_find_the_index_of_the_first_occurrence_in_a_string;

// #Easy #Top_Interview_Questions #String #Two_Pointers #String_Matching
// #Programming_Skills_II_Day_1 #2023_08_09_Time_0_ms_(100.00%)_Space_40.5_MB_(71.14%)

import java.util.Arrays;

import java.util.Collections;

public class Solution {
//@ requires(\forall int i; 0 <= i < needle.length(); Character.isLowerCase(needle.charAt(i)));
//@ requires(\forall int i; 0 <= i < haystack.length(); Character.isLowerCase(haystack.charAt(i)));
//@ requires((haystack.length() >= 0) && (haystack.length() <= 50000));
//@ requires((needle.length() >= 0) && (needle.length() <= 50000));
//@ ensures((needle.length() == 0) ==> (\result == 0));
//@ ensures(((haystack.equals("hello")) && (needle.equals("ll"))) ==> (\result == 2));
//@ ensures(((haystack.length() == 0) && (needle.length() == 0)) ==> (\result == 0));
//@ ensures(((haystack.equals("aaaaa")) && (needle.equals("bba"))) ==> (\result == -1));
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
